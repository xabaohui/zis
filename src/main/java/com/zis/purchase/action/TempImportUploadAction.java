package com.zis.purchase.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.zis.common.actiontemplate.CommonImportAction;
import com.zis.purchase.biz.DoPurchaseService;
import com.zis.purchase.dto.TempImportDTO;

/**
 * 临时数据导入Action
 * @author yz
 *
 */
public class TempImportUploadAction extends CommonImportAction<TempImportDTO>{
	
	private DoPurchaseService doPurchaseService;
	private String bizType;
	private String memo;

	@Validations(
	requiredFields = { 
			@RequiredFieldValidator(fieldName = "excelFile", key = "文件必须输入"), 
			@RequiredFieldValidator(fieldName = "bizType", key = "业务类型必须输入"), 
	})
	@Override
	protected String initTemplatePath() {
		return "tempImport.xls";
	}

	@Override
	protected Logger initLogger() {
		return Logger.getLogger(TempImportUploadAction.class);
	}

	@Override
	protected TempImportDTO getInstance() {
		return new TempImportDTO();
	}

	@Override
	protected String getFaildRecordMessage(TempImportDTO failRecord) {
		return failRecord.toString();
	}

	@Override
	protected void saveImportRecord(List<TempImportDTO> list) {
		doPurchaseService.addTempImportTask(list, bizType, memo);
	}

	@Override
	protected Map<String, Integer> initPropMapping() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("isbn", 0);
		map.put("amount", 1);
		map.put("additionalInfo", 2);//附加信息，不做检查
		return map;
	}
	
	public void setDoPurchaseService(DoPurchaseService doPurchaseService) {
		this.doPurchaseService = doPurchaseService;
	}
	
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getBizType() {
		return bizType;
	}
	
	public String getMemo() {
		return memo;
	}
}
