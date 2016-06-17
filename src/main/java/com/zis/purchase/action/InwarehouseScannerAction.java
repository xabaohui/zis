package com.zis.purchase.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.zis.purchase.biz.DoPurchaseService;
import com.zis.purchase.dto.InwarehouseView;

/**
 * 入库扫描
 * <p/>
 * 负责<br/>
 * 1.结束入库单；<br/>
 * 2.继续扫描之前未完成的入库单
 * 
 * @author yz
 * 
 */
public class InwarehouseScannerAction extends ActionSupport {

	private Integer inwarehouseId;

	private DoPurchaseService doPurchaseService;

	/**
	 * 继续扫描之前未完成的入库单
	 * 
	 * @return
	 */
	public String recoverScan() {
		try {
			InwarehouseView view = this.doPurchaseService
					.findInwarehouseViewById(inwarehouseId);
			String[] positionLabels = view.getPositionLabel();
			if (positionLabels == null || positionLabels.length == 0) {
				// 如果没有可用库位，进行如下设置，方便页面展示
				positionLabels = new String[]{"无可用库位"};
			}
			// 参数传递到下一个页面，展示用
			ActionContext context = ActionContext.getContext();
			context.put("inwarehouse", view);
			context.put("inwarehouseId", view.getId());
			context.put("stockPosLabel", positionLabels);
			context.put("bizTypeDisplay", view.getBizTypeDisplay());
			context.put("bizType", view.getBizType());
			context.put("purchaseOperator", view.getSource());
			context.put("inwarehouseOperator", view.getInwarehouseOperator());
			context.put("curPosition", positionLabels[0]);
			context.put("memo", view.getMemo());
			return SUCCESS;
		} catch (Exception e) {
			this.addActionError(e.getMessage());
			return ERROR;
		}
	}

	/**
	 * 完成入库
	 * 
	 * @return
	 */
	public String terminate() {
		try {
			this.doPurchaseService.applyTerminateInwarehouse(inwarehouseId);
			return SUCCESS;
		} catch (Exception e) {
			this.addActionError(e.getMessage());
			return ERROR;
		}
	}

	public Integer getInwarehouseId() {
		return inwarehouseId;
	}

	public void setInwarehouseId(Integer inwarehouseId) {
		this.inwarehouseId = inwarehouseId;
	}

	public void setDoPurchaseService(DoPurchaseService doPurchaseService) {
		this.doPurchaseService = doPurchaseService;
	}
}
