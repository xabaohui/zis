package com.zis.purchase.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.bean.BookinfoDetail;
import com.zis.bookinfo.bean.ShopItemInfo;
import com.zis.bookinfo.bean.ShopItemInfoShopName;
import com.zis.bookinfo.dto.BookInfoAndDetailDTO;
import com.zis.purchase.bean.InwarehouseDetail;
import com.zis.purchase.bean.PurchasePlan;
import com.zis.purchase.biz.DoPurchaseService;

/**
 * 导出淘宝数据包-从入库记录中导出
 * 
 * @author yz
 * 
 */
@Controller
@RequestMapping(value = "/purchase")
public class TaobaoCsvDataExportControllerInwarehouseImpl extends TaobaoCsvDataExportController {
	// 已处理的记录，导出的时候判断重复项用
	@Autowired
	private DoPurchaseService doPurchaseService;

	private static final Logger logger = LoggerFactory.getLogger(TaobaoCsvDataExportControllerInwarehouseImpl.class);
	
	@RequiresPermissions(value = { "stock:output" })
	@RequestMapping(value = "/exportTaobaoItemDataByInwarehouse")
	public String export(HttpServletRequest request, ModelMap map) {
		return super.export(request, map);
	}

	@Override
	protected List<BookInfoAndDetailDTO> queryBookInfoAndDetails(HttpServletRequest request) {
		// 查询相关数据
		String[] batchSelectedItemStr = request.getParameterValues("batchSelectedItem");
		if (batchSelectedItemStr == null) {
			throw new IllegalArgumentException("batchSelectedItem 数组为空");
		}
		Integer[] batchSelectedItem = new Integer[batchSelectedItemStr.length];
		for (int i = 0; i < batchSelectedItemStr.length; i++) {
			batchSelectedItem[i] = Integer.parseInt(batchSelectedItemStr[i]);
		}
		List<InwarehouseDetail> inList = doPurchaseService.findInwarehouseDetailByInwarehouseIds(batchSelectedItem);

		// 转换成BookInfoAndDetailDTO
		List<BookInfoAndDetailDTO> resultList = new ArrayList<BookInfoAndDetailDTO>();
		List<String> uniqueIsbnDealt = new ArrayList<String>();
		for (InwarehouseDetail record : inList) {
			Bookinfo book = this.bookService.findNormalBookById(record.getBookId());
			if (book == null) {
				throw new RuntimeException("没有找到对应图书，bookId=" + record.getBookId());
			}

			// 如果已经处理过，则跳过
			String uniqueIsbn = getUniqueIsbn(book);
			if (uniqueIsbnDealt.contains(uniqueIsbn)) {
				continue;
			} else {
				uniqueIsbnDealt.add(uniqueIsbn);
			}

			// 如果淘宝网已上架，则跳过
			ShopItemInfo item = this.bookService.findShopItemByBookIdAndShopName(ShopItemInfoShopName.TB_ZAIJIAN,
					book.getId());
			if (item != null) {
				continue;
			}

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
	}

	@Override
	protected String getSuccessPage() {
		return "success";
	}

	/**
	 * 生成唯一标识
	 * 
	 * @param book
	 * @return
	 */
	private String getUniqueIsbn(Bookinfo book) {
		// 一码多书的，采用"条形码+bookId"作为唯一标识，正常的图书直接使用条形码
		return book.getRepeatIsbn() ? book.getIsbn() + "-" + book.getId() : book.getIsbn();
	}

}
