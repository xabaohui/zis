package com.zis.trade.dto;

import java.util.Date;
import java.util.List;

import com.zis.trade.entity.Order;
import com.zis.trade.entity.OrderDetail;
import com.zis.trade.processor.OrderHelper;

/**
 * 主订单ViewObject
 * 
 * @author yz
 * 
 */
public class OrderVO extends Order {

	// --- 状态中文描述 ---
	private String payStatusDisplay;
	private String storageStatusDisplay;
	private String expressStatusDisplay;
	// --- 外部订单号 ---
	private List<String> outOrderNumbers;
	// --- 订单详情 ---
	private List<OrderDetailVO> orderDetailVOs;

	/**
	 * 能否取消订单
	 * 
	 * @return
	 */
	public boolean canCancelOrder() {
		return OrderHelper.canCancelOrder(this);
	}

	/**
	 * 能否支付
	 * 
	 * @return
	 */
	public boolean canPay() {
		return OrderHelper.canPay(this);
	}

	/**
	 * 能否申请退款
	 * 
	 * @return
	 */
	public boolean canApplyRefund() {
		return OrderHelper.canApplyRefund(this);
	}

	/**
	 * 能否同意退款
	 * 
	 * @return
	 */
	public boolean canAgreeRefund() {
		return OrderHelper.canAgreeRefund(this);
	}

	/**
	 * 能否取消退款
	 * 
	 * @return
	 */
	public boolean canCancelRefund() {
		return OrderHelper.canCancelRefund(this);
	}

	/**
	 * 能否修改地址
	 * 
	 * @return
	 */
	public boolean canChangeOrderAddress() {
		return OrderHelper.canChangeOrderAddress(this);
	}

	/**
	 * 能否改商品
	 * 
	 * @return
	 */
	public boolean canChangeItems() {
		return OrderHelper.canChangeItems(this);
	}

	/**
	 * 能否合并订单
	 * 
	 * @param newOrderPayStatus
	 *            新订单资金状态
	 * @return
	 */
	public boolean canCombined(String newOrderPayStatus) {
		return OrderHelper.canCombined(this, newOrderPayStatus);
	}

	/**
	 * 能否拆分订单
	 * 
	 * @return
	 */
	public boolean canSplit() {
		return OrderHelper.canSplit(this);
	}

	/**
	 * 能否分配仓库
	 * 
	 * @return
	 */
	public boolean canArrangeOrderToRepo() {
		return OrderHelper.canArrangeOrderToRepo(this);
	}

	/**
	 * 能否开始配货
	 * 
	 * @return
	 */
	public boolean canArrangeOrderToPos() {
		return OrderHelper.canArrangeOrderToPos(this);
	}

	/**
	 * 能否取消配货
	 * 
	 * @return
	 */
	public boolean canCancelArrangeOrder() {
		return OrderHelper.canCancelArrangeOrder(this);
	}

	/**
	 * 能否完成配货
	 * 
	 * @return
	 */
	public boolean canFinishSend() {
		return OrderHelper.canFinishSend(this);
	}

	/**
	 * 能否标记缺货
	 * 
	 * @return
	 */
	public boolean canLackness() {
		return OrderHelper.canLackness(this);
	}

	/**
	 * 能否打印快递单
	 * 
	 * @return
	 */
	public boolean canPrint() {
		return OrderHelper.canPrint(this);
	}

	/**
	 * 能否回填快递单号
	 * 
	 * @return
	 */
	public boolean canFillExpressNumber() {
		return OrderHelper.canFillExpressNumber(this);
	}

	/**
	 * 能否出库
	 * 
	 * @return
	 */
	public boolean canSendOut() {
		return OrderHelper.canSendOut(this);
	}

	/**
	 * 能否出库
	 * 
	 * @return 能出库返回null，不能出库返回原因
	 */
	public String canSendOutWithMessage() {
		return OrderHelper.canSendOutWithMessage(this);
	}

	/**
	 * 能否拦截
	 * 
	 * @return
	 */
	public boolean canBlock() {
		return OrderHelper.canBlock(this);
	}

	/**
	 * 返回状态展示（所有tab页统一调用）
	 * 
	 * @param tabType
	 * @return
	 */
	// TODO 没有店铺视角全查询，和仓库视角全查询
	public String getUniqueStatusDisplay(String tabType) {
		// 未支付、退款中，显示资金状态
		if (PayStatus.UNPAID.getValue().equals(tabType) || PayStatus.REFUNDING.getValue().equals(tabType)) {
			return this.getPayStatusDisplay();
		}
		// 等待分配仓库，显示资金状态+配货状态
		if (StorageStatus.isWaitForArrange(tabType)) {
			return generateStatus(getPayStatusDisplay(), getStorageStatusDisplay());
		}
		// 等待配货、配货中、等待打印、已打印，显示配货状态+物流状态
		if (StorageStatus.ARRANGED.getValue().equals(tabType) || StorageStatus.PICKUP.getValue().equals(tabType)
				|| ExpressStatus.WAIT_FOR_PRINT.getValue().equals(tabType)
				|| ExpressStatus.PRINTED.getValue().equals(tabType)) {
			return generateStatus(getStorageStatusDisplay(), getExpressStatusDisplay());
		}
		return generateStatus(getPayStatusDisplay(), getStorageStatusDisplay(), getExpressStatusDisplay());
	}

	private String generateStatus(String... args) {
		StringBuilder builder = new StringBuilder();
		for (String str : args) {
			builder.append(str).append("<br/>");
		}
		return builder.substring(0, builder.length() - 5).toString();
	}

	/**
	 * 返回用于展示的店铺名称，例如"[淘宝]小龙女书屋"
	 * 
	 * @return
	 */
	public String getShopNameDisplay() {
		return String.format("[%s]%s", this.getpName(), this.getShopName());
	}

	public String getPayStatusDisplay() {
		return payStatusDisplay;
	}

	public void setPayStatusDisplay(String payStatusDisplay) {
		this.payStatusDisplay = payStatusDisplay;
	}

	public String getStorageStatusDisplay() {
		return storageStatusDisplay;
	}

	public void setStorageStatusDisplay(String storageStatusDisplay) {
		this.storageStatusDisplay = storageStatusDisplay;
	}

	public String getExpressStatusDisplay() {
		return expressStatusDisplay;
	}

	public void setExpressStatusDisplay(String expressStatusDisplay) {
		this.expressStatusDisplay = expressStatusDisplay;
	}

	public List<String> getOutOrderNumbers() {
		return outOrderNumbers;
	}

	public void setOutOrderNumbers(List<String> outOrderNumbers) {
		this.outOrderNumbers = outOrderNumbers;
	}

	public List<OrderDetailVO> getOrderDetailVOs() {
		return orderDetailVOs;
	}

	public void setOrderDetailVOs(List<OrderDetailVO> orderDetailVOs) {
		this.orderDetailVOs = orderDetailVOs;
	}

	/**
	 * 订单明细ViewObject
	 * 
	 * @author yz
	 * 
	 */
	public static class OrderDetailVO extends OrderDetail {

		private Integer bookId;
		private String isbn;
		private String bookName;
		private String bookAuthor;
		private String bookPublisher;
		private Date publishDate;
		private Double bookPrice;
		private String bookEdition;
		private Boolean isNewEdition;

		public Integer getBookId() {
			return bookId;
		}

		public void setBookId(Integer bookId) {
			this.bookId = bookId;
		}

		public String getIsbn() {
			return isbn;
		}

		public void setIsbn(String isbn) {
			this.isbn = isbn;
		}

		public String getBookName() {
			return bookName;
		}

		public void setBookName(String bookName) {
			this.bookName = bookName;
		}

		public String getBookAuthor() {
			return bookAuthor;
		}

		public void setBookAuthor(String bookAuthor) {
			this.bookAuthor = bookAuthor;
		}

		public String getBookPublisher() {
			return bookPublisher;
		}

		public void setBookPublisher(String bookPublisher) {
			this.bookPublisher = bookPublisher;
		}

		public Date getPublishDate() {
			return publishDate;
		}

		public void setPublishDate(Date publishDate) {
			this.publishDate = publishDate;
		}

		public Double getBookPrice() {
			return bookPrice;
		}

		public void setBookPrice(Double bookPrice) {
			this.bookPrice = bookPrice;
		}

		public String getBookEdition() {
			return bookEdition;
		}

		public void setBookEdition(String bookEdition) {
			this.bookEdition = bookEdition;
		}

		public Boolean getIsNewEdition() {
			return isNewEdition;
		}

		public void setIsNewEdition(Boolean isNewEdition) {
			this.isNewEdition = isNewEdition;
		}
	}

}
