package com.zis.purchase.action;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.zis.purchase.bean.InwarehouseBizType;
import com.zis.purchase.biz.DoPurchaseService;
import com.zis.purchase.dto.InwarehouseCreateDTO;
import com.zis.purchase.dto.InwarehouseCreateResult;

/**
 * 创建入库单
 * 
 * @author yz
 * 
 */
public class InwarehouseCreateAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private String bizType; // 业务类型
	private String purchaseOperator; // 采购员
	private String inwarehouseOperator; // 入库操作员
	private String[] stockPosLabel; // 库位名称
	private Integer[] stockPosCapacity; // 库位容量
	private String memo; // 备注

	private DoPurchaseService doPurchaseService;
	private Logger logger = Logger.getLogger(InwarehouseCreateAction.class);

	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "bizType", trim = true, key = "入库类型必须选择"),
			@RequiredStringValidator(fieldName = "inwarehouseOperator", trim = true, key = "入库操作员必须输入")}, 
		requiredFields = {
			@RequiredFieldValidator(fieldName = "stockPosLabel", key = "库位名称必须输入"),
			@RequiredFieldValidator(fieldName = "stockPosCapacity", key = "库位容量必须输入")}
	)
	/**
	 * 创建采购入库单
	 * 
	 * @return
	 */
	public String createBatch() {
		if(InwarehouseBizType.PURCHASE.equals(bizType)) {
			if(StringUtils.isBlank(purchaseOperator)) {
				this.addActionError("如果入库类型是采购，采购员必须输入");
				return INPUT;
			}
		}
		InwarehouseCreateDTO inwarehouse = new InwarehouseCreateDTO();
		inwarehouse.setBizType(bizType);
		inwarehouse.setInwarehouseOperator(inwarehouseOperator);
		inwarehouse.setMemo(memo);
		inwarehouse.setPurchaseOperator(purchaseOperator);
		inwarehouse.setStockPosLabel(stockPosLabel);
		inwarehouse.setStockPosCapacity(stockPosCapacity);
		try {
			// 新建入库单
			InwarehouseCreateResult result = doPurchaseService.createInwarehouse(inwarehouse);
			// 参数传递到下一个页面，展示用
			ActionContext context = ActionContext.getContext();
			context.put("inwarehouseId", result.getInwarehouseId());
			context.put("stockPosLabel", stockPosLabel);
			context.put("bizTypeDisplay", InwarehouseBizType.getDisplay(bizType));
			context.put("bizType", bizType);
			context.put("purchaseOperator", purchaseOperator);
			context.put("inwarehouseOperator", inwarehouseOperator);
			context.put("curPosition", stockPosLabel[0]);
			context.put("memo", memo);
			return SUCCESS;
		} catch (Exception e) {
			this.addActionError(e.getMessage());
			logger.error("创建采购入库单失败", e);
			return INPUT;
		}
	}

	public String getPurchaseOperator() {
		return purchaseOperator;
	}

	public void setPurchaseOperator(String purchaseOperator) {
		this.purchaseOperator = purchaseOperator;
	}

	public String[] getStockPosLabel() {
		return stockPosLabel;
	}

	public void setStockPosLabel(String[] stockPosLabel) {
		this.stockPosLabel = stockPosLabel;
	}

	public Integer[] getStockPosCapacity() {
		return stockPosCapacity;
	}

	public void setStockPosCapacity(Integer[] stockPosCapacity) {
		this.stockPosCapacity = stockPosCapacity;
	}

	public void setInwarehouseOperator(String inwarehouseOperator) {
		this.inwarehouseOperator = inwarehouseOperator;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getInwarehouseOperator() {
		return inwarehouseOperator;
	}

	public String getMemo() {
		return memo;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public void setDoPurchaseService(DoPurchaseService doPurchaseService) {
		this.doPurchaseService = doPurchaseService;
	}
}
