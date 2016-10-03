package com.zis.purchase.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
@Controller
@RequestMapping(value = "/purchase")
public class InwarehouseCreateController {

	// private String bizType; // 业务类型
	// private String purchaseOperator; // 采购员
	// private String inwarehouseOperator; // 入库操作员
	// private String[] stockPosLabel; // 库位名称
	// private Integer[] stockPosCapacity; // 库位容量
	// private String memo; // 备注
	@Autowired
	private DoPurchaseService doPurchaseService;
	private Logger logger = Logger.getLogger(InwarehouseCreateController.class);
//TODO 验证框架
//	@Validations(requiredStrings = {
//			@RequiredStringValidator(fieldName = "bizType", trim = true, key = "入库类型必须选择"),
//			@RequiredStringValidator(fieldName = "inwarehouseOperator", trim = true, key = "入库操作员必须输入") }, requiredFields = {
//			@RequiredFieldValidator(fieldName = "stockPosLabel", key = "库位名称必须输入"),
//			@RequiredFieldValidator(fieldName = "stockPosCapacity", key = "库位容量必须输入") })
	/**
	 * 创建采购入库单
	 * 
	 * @return
	 */
	@RequestMapping(value = "/inWarehouse", method = RequestMethod.POST)
	public String createBatch(String bizType, String purchaseOperator,
			String inwarehouseOperator, String[] stockPosLabel,
			Integer[] stockPosCapacity, String memo, ModelMap context) {
		if (InwarehouseBizType.PURCHASE.equals(bizType)) {
			if (StringUtils.isBlank(purchaseOperator)) {
				// TODO 验证框架
				// this.addActionError("如果入库类型是采购，采购员必须输入");
				return "purchase/inwarehouse";
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
			InwarehouseCreateResult result = doPurchaseService
					.createInwarehouse(inwarehouse);
			// 参数传递到下一个页面，展示用
			context.put("inwarehouseId", result.getInwarehouseId());
			context.put("stockPosLabel", stockPosLabel);
			context.put("bizTypeDisplay",
					InwarehouseBizType.getDisplay(bizType));
			context.put("bizType", bizType);
			context.put("purchaseOperator", purchaseOperator);
			context.put("inwarehouseOperator", inwarehouseOperator);
			context.put("curPosition", stockPosLabel[0]);
			context.put("memo", memo);
			return "purchase/inwarehouseScanner";
		} catch (Exception e) {
			//TODO 验证框架
//			this.addActionError(e.getMessage());
			logger.error("创建采购入库单失败", e);
			return "purchase/inwarehouse";
		}
	}
}
