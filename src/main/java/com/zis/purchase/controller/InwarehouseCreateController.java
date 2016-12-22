package com.zis.purchase.controller;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zis.purchase.bean.InwarehouseBizType;
import com.zis.purchase.biz.DoPurchaseService;
import com.zis.purchase.dto.CreateBatchDTO;
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

	@Autowired
	private DoPurchaseService doPurchaseService;
	private Logger logger = Logger.getLogger(InwarehouseCreateController.class);

	/**
	 * 创建采购入库单
	 * 
	 * @return
	 */
	@RequiresPermissions(value = {"purchase:inWarehouse"})
	@RequestMapping(value = "/inWarehouse")
	public String createBatch(@Valid @ModelAttribute("createBatchDTO") CreateBatchDTO createBatchDTO, BindingResult br, ModelMap context) {
		
		if(br.hasErrors()){
			return "purchase/inwarehouse";
		}
		
		String bizType = createBatchDTO.getBizType(); // 业务类型
		String purchaseOperator = createBatchDTO.getPurchaseOperator(); // 采购员
		String inwarehouseOperator = createBatchDTO.getInwarehouseOperator(); // 入库操作员
		String[] stockPosLabel = createBatchDTO.getStockPosLabel(); // 库位名称
		Integer[] stockPosCapacity = createBatchDTO.getStockPosCapacity(); // 库位容量
		String memo = createBatchDTO.getMemo(); // 备注

		if (InwarehouseBizType.PURCHASE.equals(bizType)) {
			if (StringUtils.isBlank(purchaseOperator)) {
				context.put("actionError", "如果入库类型是采购，采购员必须输入");
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
			InwarehouseCreateResult result = doPurchaseService.createInwarehouse(inwarehouse);
			// 参数传递到下一个页面，展示用
			context.put("inwarehouseId", result.getInwarehouseId());
			context.put("stockPosLabel", stockPosLabel);
			context.put("bizTypeDisplay", InwarehouseBizType.getDisplay(bizType));
			context.put("bizType", bizType);
			context.put("purchaseOperator", purchaseOperator);
			context.put("inwarehouseOperator", inwarehouseOperator);
			context.put("curPosition", stockPosLabel[0]);
			context.put("memo", memo);
			return "purchase/inwarehouseScanner";
		} catch (Exception e) {
			context.put("actionError", e.getMessage());
			logger.error("创建采购入库单失败", e);
			return "purchase/inwarehouse";
		}
	}
}
