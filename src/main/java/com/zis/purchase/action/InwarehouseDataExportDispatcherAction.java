package com.zis.purchase.action;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 采购入库数据导出
 * <p/>
 * 通过此Action，将导出请求转发到负责导出各种数据的具体Action中
 * 
 * @author yz
 * 
 */
public class InwarehouseDataExportDispatcherAction extends ActionSupport {

	private Integer[] batchSelectedItem;

	private String operateType;
	
	public String export() {
		// 根据struts文件的配置，将导出请求转发到各种数据的具体Action中
		return operateType;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public Integer[] getBatchSelectedItem() {
		return batchSelectedItem;
	}

	public void setBatchSelectedItem(Integer[] batchSelectedItem) {
		this.batchSelectedItem = batchSelectedItem;
	}
}
