package com.zis.purchase.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.zis.common.actiontemplate.CommonImportAction;
import com.zis.purchase.bean.TempImportTaskBizTypeEnum;
import com.zis.purchase.biz.DoPurchaseService;
import com.zis.purchase.dto.TempImportDTO;

/**
 * 临时数据导入Action
 * @author yz
 *
 */
public class TempImportUploadAction extends CommonImportAction<TempImportDTO>{
	
	private static final long serialVersionUID = 8176579244582350282L;
	private DoPurchaseService doPurchaseService;
	private TempImportTaskBizTypeEnum bizType;
	private String memo;
	
	private static final String HEADER_ISBN = "条形码";
	private static final String HEADER_STOCK = "库存量";
	private static final String HEADER_SHOP_STATUS = "店铺状态";
	private static final String HEADER_SHOP_TITLE = "标题";
	private static final String HEADER_SHOP_CATEGORY_ID = "类目ID";
	private static final String HEADER_SHOP_FORBIDDEN = "禁止发布";
	// 支持的业务类型描述
	private static String supportedBizTypes = HEADER_STOCK + "," + HEADER_SHOP_STATUS + "," 
											+ HEADER_SHOP_TITLE + "," + HEADER_SHOP_CATEGORY_ID + "," 
											+ HEADER_SHOP_FORBIDDEN;

	@Validations(
	requiredFields = { 
			@RequiredFieldValidator(fieldName = "excelFile", key = "文件必须输入"), 
	})
	@Override
	protected String initTemplatePath() {
		return null;
	}
	
	@Override
	protected String subCheckFileFormat(List<String> factHeader) {
		if(!factHeader.get(0).equals(HEADER_ISBN)) {
			return "格式错误，第一列必须是" + HEADER_ISBN;
		}
		String secondCol = factHeader.get(1);
		if(secondCol.equals(HEADER_STOCK)) {
			bizType = TempImportTaskBizTypeEnum.STOCK;
			return null;
		}
		if(secondCol.equals(HEADER_SHOP_STATUS)) {
			bizType = TempImportTaskBizTypeEnum.SHOP_STATUS;
			return null;
		}
		if(secondCol.equals(HEADER_SHOP_TITLE)) {
			bizType = TempImportTaskBizTypeEnum.SHOP_TITLE;
			return null;
		}
		if(secondCol.equals(HEADER_SHOP_CATEGORY_ID)) {
			bizType = TempImportTaskBizTypeEnum.SHOP_CATEGORY_ID;
			return null;
		}
		if(secondCol.equals(HEADER_SHOP_FORBIDDEN)) {
			bizType = TempImportTaskBizTypeEnum.TAOBAO_FORBIDDEN;
			return null;
		}
		return "格式错误，无法识别的业务类型，第二列必须是{"+supportedBizTypes+"}之一";
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
		map.put("data", 1);
		map.put("additionalInfo", 2);//附加信息，不做检查
		return map;
	}
	
	public void setDoPurchaseService(DoPurchaseService doPurchaseService) {
		this.doPurchaseService = doPurchaseService;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getMemo() {
		return memo;
	}
}
