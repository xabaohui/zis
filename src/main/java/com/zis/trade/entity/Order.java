package com.zis.trade.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.apache.commons.lang3.StringUtils;

/**
 * Order entity. @author MyEclipse Persistence Tools
 */

@Entity
@Table(name = "order")
public class Order {

	// Fields

	@Id
	@GeneratedValue
	@Column(name = "order_id", nullable = false)
	private Integer orderId;

	@Column(name = "shop_id")
	private Integer shopId;

	@Column(name = "shop_name")
	private String shopName;

	@Column(name = "p_name")
	private String pName;

	@Column(name = "company_id")
	private Integer companyId;

	@Column(name = "repo_id")
	private Integer repoId;

	@Column(name = "receiver_name")
	private String receiverName;

	@Column(name = "receiver_phone")
	private String receiverPhone;

	@Column(name = "receiver_addr")
	private String receiverAddr;

	@Column(name = "order_group_number")
	private String orderGroupNumber;

	@Column(name = "express_company")
	private String expressCompany;

	@Column(name = "express_number")
	private String expressNumber;

	@Column(name = "order_money")
	private Double orderMoney;

	@Column(name = "order_type")
	private String orderType;

	@Column(name = "storage_status")
	private String storageStatus;

	@Column(name = "express_status")
	private String expressStatus;

	@Column(name = "pay_status")
	private String payStatus;

	@Column(name = "block_flag")
	private boolean blockFlag;

	@Column(name = "block_reason")
	private String blockReason;

	@Column(name = "refund_apply_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date refundApplyTime;

	@Column(name = "refund_finish_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date refundFinishTime;

	@Column(name = "pay_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date payTime;

	@Column(name = "arrange_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date arrangeTime;

	@Column(name = "express_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expressTime;

	@Column(name = "saler_remark")
	private String salerRemark;

	@Column(name = "buyer_message")
	private String buyerMessage;

	@Column(name = "create_time", updatable = false, length = 32)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	@Column(name = "update_time", length = 32)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

	@Version
	@Column(name = "version")
	private Integer version;

	// Constructors

	/** default constructor */
	public Order() {
	}

	// Property accessors

	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public Integer getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getRepoId() {
		return this.repoId;
	}

	public void setRepoId(Integer repoId) {
		this.repoId = repoId;
	}

	public String getReceiverName() {
		return this.receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverPhone() {
		return this.receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getReceiverAddr() {
		return this.receiverAddr;
	}

	public void setReceiverAddr(String receiverAddr) {
		this.receiverAddr = receiverAddr;
	}

	public String getOrderGroupNumber() {
		return this.orderGroupNumber;
	}

	public void setOrderGroupNumber(String orderGroupNumber) {
		this.orderGroupNumber = orderGroupNumber;
	}

	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	public String getExpressNumber() {
		return expressNumber;
	}

	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber;
	}

	public String getExpressStatus() {
		return expressStatus;
	}

	public void setExpressStatus(String expressStatus) {
		this.expressStatus = expressStatus;
	}

	public Date getExpressTime() {
		return expressTime;
	}

	public void setExpressTime(Date expressTime) {
		this.expressTime = expressTime;
	}

	public Double getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(Double orderMoney) {
		this.orderMoney = orderMoney;
	}

	public String getOrderType() {
		return this.orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getStorageStatus() {
		return this.storageStatus;
	}

	public void setStorageStatus(String storageStatus) {
		this.storageStatus = storageStatus;
	}

	public String getPayStatus() {
		return this.payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public boolean getBlockFlag() {
		return blockFlag;
	}

	public void setBlockFlag(boolean blockFlag) {
		this.blockFlag = blockFlag;
	}

	public String getBlockReason() {
		return blockReason;
	}

	public void setBlockReason(String blockReason) {
		this.blockReason = blockReason;
	}

	public Date getRefundApplyTime() {
		return refundApplyTime;
	}

	public void setRefundApplyTime(Date refundApplyTime) {
		this.refundApplyTime = refundApplyTime;
	}

	public Date getRefundFinishTime() {
		return refundFinishTime;
	}

	public void setRefundFinishTime(Date refundFinishTime) {
		this.refundFinishTime = refundFinishTime;
	}

	public Date getPayTime() {
		return this.payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Date getArrangeTime() {
		return this.arrangeTime;
	}

	public void setArrangeTime(Date arrangeTime) {
		this.arrangeTime = arrangeTime;
	}

	public String getSalerRemark() {
		return this.salerRemark;
	}

	public void setSalerRemark(String salerRemark) {
		this.salerRemark = salerRemark;
	}

	public String getBuyerMessage() {
		return this.buyerMessage;
	}

	public void setBuyerMessage(String buyerMessage) {
		this.buyerMessage = buyerMessage;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * 订单-配货状态
	 * 
	 * @author yz
	 * 
	 */
	public static enum StorageStatus {
		//
		WAIT_ARRANGE("wait_arrange", "等待分配仓库"),
		//
		WAIT_ARRANGE_BY_MANUAL("wait_arrange_by_manual", "等待分配仓库(手动取消)"),
		//
		WAIT_ARRANGE_BY_LACKNESS("wait_arrange_by_lackness", "等待分配仓库(缺货)"),
		//
		ARRANGED("arranged", "已分配仓库"),
		//
		PICKUP("pickup", "配货中"),
		//
		PICKUP_FINISH("pickup_finish", "配货完成");

		private String value;
		private String display;

		private StorageStatus(String value, String display) {
			this.value = value;
			this.display = display;
		}

		/**
		 * 根据value获得枚举值
		 * 
		 * @param value
		 * @return
		 */
		public static StorageStatus getEnum(String value) {
			for (StorageStatus record : values()) {
				if (record.getValue().equals(value)) {
					return record;
				}
			}
			return null;
		}

		/**
		 * 判断value是否是预定义的
		 * 
		 * @param value
		 * @return
		 */
		public static boolean isDefined(String value) {
			if (StringUtils.isBlank(value)) {
				return false;
			}
			return getEnum(value) != null;
		}

		/**
		 * 是否处于等待分配仓库状态
		 * 
		 * @param value
		 * @return
		 */
		public static boolean isWaitForArrange(String value) {
			return WAIT_ARRANGE.getValue().equals(value) || WAIT_ARRANGE_BY_LACKNESS.getValue().equals(value)
					|| WAIT_ARRANGE_BY_MANUAL.getValue().equals(value);
		}

		public String getValue() {
			return value;
		}

		public String getDisplay() {
			return display;
		}
	}

	/**
	 * 订单-物流状态
	 * 
	 * @author yz
	 * 
	 */
	public static enum ExpressStatus {
		//
		WAIT_FOR_PRINT("wait_for_print", "待打印"),
		//
		PRINTED("printed", "已打单"),
		//
		FILLED_EX_NUM("filled_express_number", "已填单"),
		//
		SEND_OUT("send_out", "已出库");

		private String value;
		private String display;

		private ExpressStatus(String value, String display) {
			this.value = value;
			this.display = display;
		}

		/**
		 * 根据value获得枚举值
		 * 
		 * @param value
		 * @return
		 */
		public static ExpressStatus getEnum(String value) {
			for (ExpressStatus record : values()) {
				if (record.getValue().equals(value)) {
					return record;
				}
			}
			return null;
		}

		/**
		 * 判断value是否是预定义的
		 * 
		 * @param value
		 * @return
		 */
		public static boolean isDefined(String value) {
			if (StringUtils.isBlank(value)) {
				return false;
			}
			return getEnum(value) != null;
		}

		public String getValue() {
			return value;
		}

		public String getDisplay() {
			return display;
		}
	}

	/**
	 * 订单-配货状态
	 * 
	 * @author yz
	 * 
	 */
	public static enum PayStatus {
		//
		UNPAID("unpaid", "未支付"),
		//
		PAID("paid", "已支付"),
		//
		REFUNDING("refunding", "退款中"),
		//
		REFUND_FINISH("refund_finish", "订单关闭(已退款)"),
		//
		CANCELLED("cancelled", "订单关闭(未支付)");

		private String value;
		private String display;

		private PayStatus(String value, String display) {
			this.value = value;
			this.display = display;
		}

		/**
		 * 根据value获得枚举值
		 * 
		 * @param value
		 * @return
		 */
		public static PayStatus getEnum(String value) {
			for (PayStatus record : values()) {
				if (record.getValue().equals(value)) {
					return record;
				}
			}
			return null;
		}

		/**
		 * 判断value是否是预定义的
		 * 
		 * @param value
		 * @return
		 */
		public static boolean isDefined(String value) {
			if (StringUtils.isBlank(value)) {
				return false;
			}
			return getEnum(value) != null;
		}

		public String getValue() {
			return value;
		}

		public String getDisplay() {
			return display;
		}
	}

	/**
	 * 订单类型
	 * 
	 * @author yz
	 * 
	 */
	public static enum OrderType {
		//
		SELF("self", "自发"),
		//
		FOR_OTHER("for_other", "代发"),
		//
		TO_OTHER("to_other", "委派");

		private String value;
		private String display;

		private OrderType(String value, String display) {
			this.value = value;
			this.display = display;
		}

		/**
		 * 根据value获得枚举值
		 * 
		 * @param value
		 * @return
		 */
		public static OrderType getEnum(String value) {
			for (OrderType record : values()) {
				if (record.getValue().equals(value)) {
					return record;
				}
			}
			return null;
		}

		/**
		 * 判断value是否是预定义的
		 * 
		 * @param value
		 * @return
		 */
		public static boolean isDefined(String value) {
			if (StringUtils.isBlank(value)) {
				return false;
			}
			return getEnum(value) != null;
		}

		public String getValue() {
			return value;
		}

		public String getDisplay() {
			return display;
		}
	}
}