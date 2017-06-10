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
 * OrderLog entity. @author MyEclipse Persistence Tools
 */

@Entity
@Table(name = "order_log")
public class OrderLog  {

	// Fields
	
	@Id
	@GeneratedValue
	@Column(name = "order_log_id", nullable = false)
	private Integer orderLogId;
	
	@Column(name = "order_id")
	private Integer orderId;
	
	@Column(name = "order_group_number")
	private String orderGroupNumber;
	
	@Column(name = "operater_id")
	private Integer operaterId;
	
	@Column(name = "operate_detail")
	private String operateDetail;
	
	@Column(name = "operate_type")
	private String operateType;
	
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
	public OrderLog() {
	}

	/** full constructor */
	public OrderLog(Integer orderId, String orderGroupNumber, Integer operaterId, String operateDetail,
			String operateType, Date createTime, Date updateTime, Integer version) {
		this.orderId = orderId;
		this.orderGroupNumber = orderGroupNumber;
		this.operaterId = operaterId;
		this.operateDetail = operateDetail;
		this.operateType = operateType;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.version = version;
	}

	// Property accessors

	public Integer getOrderLogId() {
		return this.orderLogId;
	}

	public void setOrderLogId(Integer orderLogId) {
		this.orderLogId = orderLogId;
	}

	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getOrderGroupNumber() {
		return this.orderGroupNumber;
	}

	public void setOrderGroupNumber(String orderGroupNumber) {
		this.orderGroupNumber = orderGroupNumber;
	}

	public Integer getOperaterId() {
		return this.operaterId;
	}

	public void setOperaterId(Integer operaterId) {
		this.operaterId = operaterId;
	}

	public String getOperateDetail() {
		return this.operateDetail;
	}

	public void setOperateDetail(String operateDetail) {
		this.operateDetail = operateDetail;
	}

	public String getOperateType() {
		return this.operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
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
	 * 订单操作类型
	 * @author yz
	 *
	 */
	public static enum OperateType {
		//
		CREATE("create", "创建订单"),
		//
		PAY("pay", "支付"),
		//
		CANCEL("cancel", "取消"),
		//
		APPLY_REFUND("apply_refund", "申请退款"),
		//
		AGREE_REFUND("agree_refund", "同意退款"),
		//
		CANCEL_REFUND("cancel_refund", "取消退款"),
		//
		CHANGE_ADDR("change_addr", "修改地址"),
		//
		CHANGE_ITEMS("change_items", "修改 商品"),
		//
		COMBINE("combine", "合并订单"),
		//
		SPLIT("split", "拆分"),
		//
		ARRANGE_TO_REPO("arrange_to_repo", "分配仓库"),
		//
		ARRANGE_TO_POS("arrange_to_pos", "开始配货"),
		//
		CANCEL_ARRANGE("cancel_arrange", "取消分配仓库"),
		//
		PICKUP_FINISH("pickup_finish", "完成配货"),
		//
		LACKNESS("lackness", "标记缺货"),
		//
		PRINT("print", "打印快递单"),
		//
		FILL_EX_NUM("fill_express_number", "填写快递单号"),
		//
		SEND_OUT("send_out", "出库"),
		//
		BLOCK("block", "拦截"),
		//
		UNBLOCK("unblock", "取消拦截");

		private String value;
		private String display;

		private OperateType(String value, String display) {
			this.value = value;
			this.display = display;
		}

		/**
		 * 根据value获得枚举值
		 * 
		 * @param value
		 * @return
		 */
		public static OperateType getEnum(String value) {
			for (OperateType record : values()) {
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