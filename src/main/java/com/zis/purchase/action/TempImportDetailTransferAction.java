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
	private String operateType;
	
	/** 更新商品快照 */
	public static final String OPERATE_TYPE_SHOP_ITEM = "shopItem";
	/** 更新商品库存 */
	public static final String OPERATE_TYPE_BOOK_STOCK = "bookStock";
	private Logger logger = Logger.getLogger(TempImportDetailTransferAction.class);

	private BookService bookService;
	private DoPurchaseService doPurchaseService;
	
	/**
	 * 转换为其他类型记录
	 * @return
	 */
	public String transfer() {
		try {
			if(OPERATE_TYPE_SHOP_ITEM.equals(operateType)) {
				transferToShopItem();
			} else if(OPERATE_TYPE_BOOK_STOCK.equals(operateType)) {
				transferToBookStock();
			}
			return SUCCESS;
		} catch (Exception e) {
			logger.error("临时导入记录转换成其他类型时发成错误:" + e.getMessage(), e);
			this.addActionError("临时导入记录转换成其他类型时发生错误:" + e.getMessage());
			return INPUT;
		}
	}

	private void transferToBookStock() {
		List<TempImportDetailView> list = this.doPurchaseService.findTempImportDetail(taskId, TempImportDetailStatus.MATCHED);
		List<StockDTO> stockList = new ArrayList<StockDTO>();
		for (TempImportDetailView view : list) {
			StockDTO stock = new StockDTO();
			stock.setBookId(view.getBookId());
			stock.setStockBalance(view.getAmount());
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

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}
	
	public void setDoPurchaseService(DoPurchaseService doPurchaseService) {
		this.doPurchaseService = doPurchaseService;
	}
}
