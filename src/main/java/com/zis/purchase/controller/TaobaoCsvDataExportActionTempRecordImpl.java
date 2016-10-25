package com.zis.purchase.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.bean.BookinfoDetail;
import com.zis.bookinfo.dto.BookInfoAndDetailDTO;
import com.zis.purchase.bean.PurchasePlan;
import com.zis.purchase.bean.TempImportDetailStatus;
import com.zis.purchase.biz.DoPurchaseService;
import com.zis.purchase.dto.TempImportDetailView;

/**
 * 导出淘宝数据包-从临时表格中导出
 * 
 * @author yz
 * 
 */
@Controller
@RequestMapping(value = "/purchase")
public class TaobaoCsvDataExportActionTempRecordImpl extends TaobaoCsvDataExportController {

	@Autowired
	private DoPurchaseService doPurchaseService;

	private static final Logger logger = LoggerFactory.getLogger(TaobaoCsvDataExportActionTempRecordImpl.class);

	@RequestMapping(value = "/exportTaobaoItemDataByTempImport")
	public String export(HttpServletRequest request, ModelMap map) {
		return super.export(request, map);
	}

	@Override
	protected List<BookInfoAndDetailDTO> queryBookInfoAndDetails(HttpServletRequest request) {
		String taskIdStr = request.getParameter("taskId");
		if (StringUtils.isNumeric(taskIdStr)) {
			Integer taskId = Integer.parseInt(taskIdStr);
			// 查询相关数据
			List<TempImportDetailView> inList = this.doPurchaseService.findTempImportDetail(taskId,
					TempImportDetailStatus.MATCHED);
			System.out.println(taskId);
			// 转换成BookInfoAndDetailDTO
			List<BookInfoAndDetailDTO> resultList = new ArrayList<BookInfoAndDetailDTO>();
			for (TempImportDetailView tmp : inList) {
				Bookinfo book = tmp.getAssociateBook();
				BookinfoDetail detail = bookService.findBookInfoDetailByBookId(book.getId());
				// 如果没有图书详情，则从网上采集
				if (detail == null) {
					try {
						detail = bookService.captureBookInfoDetailFromNet(book.getId());
					} catch (Exception e) {
						// 单条错误不能影响全部记录
						logger.error("[数据采集] 采集过程中发生错误，bookId=", book.getId(), e);
					}
				}
				// 如果没有采集到图书详情，则跳过此条记录
				if (detail == null) {
					continue;
				}
				// 过滤淘宝黑名单记录
				if (detail.getTaobaoForbidden() != null && detail.getTaobaoForbidden() == true) {
					continue;
				}
				BookInfoAndDetailDTO infoAndDetail = new BookInfoAndDetailDTO();
				BeanUtils.copyProperties(book, infoAndDetail);
				BeanUtils.copyProperties(detail, infoAndDetail);
				// 查询库存量
				PurchasePlan plan = this.doPurchaseService.findPurchasePlanByBookId(book.getId());
				if (plan == null || plan.getStockAmount() == null || plan.getStockAmount() <= 0) {
					continue; // 跳过没有库存的记录
				}
				if (plan != null) {
					infoAndDetail.setStockBalance(plan.getStockAmount());
				}
				resultList.add(infoAndDetail);
			}
			return resultList;
		} else {
			throw new IllegalArgumentException("taskId 异常");
		}
	}

	@Override
	protected String getSuccessPage() {
		return "success";
	}

}
