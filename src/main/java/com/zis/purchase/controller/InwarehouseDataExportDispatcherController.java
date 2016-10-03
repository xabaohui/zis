package com.zis.purchase.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 采购入库数据导出
 * <p/>
 * 通过此Action，将导出请求转发到负责导出各种数据的具体Action中
 * 
 * @author yz
 * 
 */
@Controller
@RequestMapping(value = "/purchase")
public class InwarehouseDataExportDispatcherController {

	@RequestMapping(value = "/exportInwarehouseData",method=RequestMethod.POST)
	public String export(Integer[] batchSelectedItem, String operateType,
			ModelMap map) {
		map.put("batchSelectedItem", batchSelectedItem);
		if ("taobao_item_data".equals(operateType)) {
			return "forward:/purchase/exportTaobaoItemDataByInwarehouse";
		} else if ("wangqubao_item_data".equals(operateType)) {
			return "forward:/purchase/exportWangqubaoItemByInwarehouse";
		} else if ("wangqubao_inwarehouse".equals(operateType)) {
			return "forward:/purchase/exportWangqubaoInwarehouse";
		} else if ("taobao_item_sold_out".equals(operateType)) {
			return "forward:/purchase/exportSoldOutItem";
		} else {
			return "error";
		}
	}
}
