package com.zis.purchase.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;
import com.zis.bookinfo.bean.ShopItemInfoShopName;
import com.zis.bookinfo.bean.ShopItemInfoStatus;
import com.zis.bookinfo.dto.ShopItemInfoDTO;
import com.zis.bookinfo.service.BookService;
import com.zis.purchase.bean.TempImportDetailStatus;
import com.zis.purchase.bean.TempImportTask;
import com.zis.purchase.bean.TempImportTaskBizTypeEnum;
import com.zis.purchase.biz.DoPurchaseService;
import com.zis.purchase.dto.StockDTO;
import com.zis.purchase.dto.TempImportDetailView;

/**
 * 临时记录转换为其他类型记录Action<br/>
 * 
 * @author yz
 * 
 */
public class TempImportDetailTransferAction extends ActionSupport {

	private static final long serialVersionUID = 2684805789435711703L;
	private Integer taskId;
	
	private Logger logger = Logger.getLogger(TempImportDetailTransferAction.class);

	private BookService bookService;
	private DoPurchaseService doPurchaseService;
	
	/**
	 * 转换为其他类型记录
	 * @return
	 */
	public String transfer() {
		TempImportTask task = doPurchaseService.findTempImportTaskByTaskId(taskId);
		if(task == null) {
			logger.error("临时导入记录转换成其他类型时发成错误，无此记录，taskId=" + taskId);
			this.addActionError("系统错误，无此记录，taskId=" + taskId);
			return INPUT;
		}
		try {
			switch(TempImportTaskBizTypeEnum.parseEnum(task.getBizType())) {
				case STOCK:
					transferToBookStock();
					break;
				case SHOP_STATUS:
					transferToShopItem();
					break;
				case SHOP_TITLE:
					updateShopItemInfo(true, false, false);
					break;
				case SHOP_CATEGORY_ID:
					updateShopItemInfo(false, true, false);
					break;
				case TAOBAO_FORBIDDEN:
					updateShopItemInfo(false, false, true);
					break;
				default:
					throw new RuntimeException("不支持的业务类型：" + task.getBizType());
			}
			return SUCCESS;
		} catch (Exception e) {
			logger.error("临时导入记录转换成其他类型时发成错误:" + e.getMessage(), e);
			this.addActionError("临时导入记录转换成其他类型时发生错误:" + e.getMessage());
			return INPUT;
		}
	}

	/**
	 * 更新库存
	 */
	private void transferToBookStock() {
		List<TempImportDetailView> list = this.doPurchaseService.findTempImportDetail(taskId, TempImportDetailStatus.MATCHED);
		List<StockDTO> stockList = new ArrayList<StockDTO>();
		for (TempImportDetailView view : list) {
			StockDTO stock = new StockDTO();
			stock.setBookId(view.getBookId());
			stock.setStockBalance(Integer.valueOf(view.getData()));
			stockList.add(stock);
		}
		this.doPurchaseService.addBookStock(stockList);
	}

	/**
	 * 批量转换为网店商品
	 */
	private void transferToShopItem() {
		List<TempImportDetailView> list = this.doPurchaseService.findTempImportDetail(taskId, TempImportDetailStatus.MATCHED);
		List<ShopItemInfoDTO> itemList = new ArrayList<ShopItemInfoDTO>();
		for (TempImportDetailView view : list) {
			if(!ShopItemInfoStatus.ON_SALES.equals(view.getAdditionalInfo()) && !ShopItemInfoStatus.SOLD_OUT.equals(view.getAdditionalInfo())) {
				throw new RuntimeException("商品状态必须是"+ShopItemInfoStatus.ON_SALES+"或者" + ShopItemInfoStatus.SOLD_OUT);
			}
			ShopItemInfoDTO item = new ShopItemInfoDTO();
			item.setBookId(view.getBookId());
			item.setShopName(ShopItemInfoShopName.TB_ZAIJIAN);
			item.setShopStatus(view.getAdditionalInfo());
			itemList.add(item);
		}
		this.bookService.saveShopItemForBatch(itemList);
	}
	
	/**
	 * 更新网店标题\类目ID，或设置禁止发布
	 * @param updateTitle 是否更新标题
	 */
	private void updateShopItemInfo(boolean updateTitle, boolean updateCategoryId, boolean updateForbidden) {
		List<TempImportDetailView> list = this.doPurchaseService.findTempImportDetail(taskId, TempImportDetailStatus.MATCHED);
		List<ShopItemInfoDTO> detailList = new ArrayList<ShopItemInfoDTO>();
		for (TempImportDetailView view : list) {
			ShopItemInfoDTO detail = new ShopItemInfoDTO();
			detail.setBookId(view.getBookId());
			if(updateTitle) {
				detail.setTaobaoTitle(view.getData());
			}
			if(updateCategoryId){
				detail.setTaobaoCatagoryId(Integer.valueOf(view.getData()));
			}
			if(updateForbidden) {
				detail.setTaobaoForbidden("是".equals(view.getData()));
			}
			detailList.add(detail);
		}
		this.bookService.updateTitleAndCategoryForBatch(detailList);
	}
	
	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}
	
	public void setDoPurchaseService(DoPurchaseService doPurchaseService) {
		this.doPurchaseService = doPurchaseService;
	}
}
