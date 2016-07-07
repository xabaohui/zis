package com.zis.purchase.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.bean.BookinfoDetail;
import com.zis.bookinfo.dto.BookInfoAndDetailDTO;
import com.zis.purchase.bean.TempImportDetailStatus;
import com.zis.purchase.biz.DoPurchaseService;
import com.zis.purchase.dto.TempImportDetailView;

/**
 * 导出淘宝数据包-从临时表格中导出
 * @author yz
 *
 */
public class TaobaoCsvDataExportActionTempRecordImpl extends TaobaoCsvDataExportAction {
	
	private static final long serialVersionUID = 1L;
	private Integer taskId;
	private DoPurchaseService doPurchaseService;

	@Override
	protected List<BookInfoAndDetailDTO> queryBookInfoAndDetails() {
		// 查询相关数据
		List<TempImportDetailView> inList = this.doPurchaseService.findTempImportDetail(taskId,TempImportDetailStatus.MATCHED);
		// 转换成BookInfoAndDetailDTO
		List<BookInfoAndDetailDTO> resultList = new ArrayList<BookInfoAndDetailDTO>();
		for (TempImportDetailView tmp : inList) {
			Bookinfo book = tmp.getAssociateBook();
			BookinfoDetail detail = bookService.findBookInfoDetailByBookId(book.getId());
			// 如果没有图书详情，则从网上采集
			if(detail == null) {
				detail = bookService.captureBookInfoDetailFromNet(book.getId());
			}
			// 如果没有采集到图书详情，则跳过此条记录
			if(detail == null) {
				continue;
			}
			BookInfoAndDetailDTO infoAndDetail = new BookInfoAndDetailDTO();
			BeanUtils.copyProperties(book, infoAndDetail);
			BeanUtils.copyProperties(detail, infoAndDetail);
			resultList.add(infoAndDetail);
		}
		return resultList;
	}
	
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public void setDoPurchaseService(DoPurchaseService doPurchaseService) {
		this.doPurchaseService = doPurchaseService;
	}
}
