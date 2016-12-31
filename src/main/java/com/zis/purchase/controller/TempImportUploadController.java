package com.zis.purchase.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.zis.common.controllertemplate.CommonImportController;
import com.zis.purchase.bean.TempImportTaskBizTypeEnum;
import com.zis.purchase.biz.DoPurchaseService;
import com.zis.purchase.dto.TempImportDTO;

/**
 * 临时数据导入Action
 * 
 * @author yz
 * 
 */
@Controller
@RequestMapping(value = "/purchase")
public class TempImportUploadController extends CommonImportController<TempImportDTO> {

	@Autowired
	private DoPurchaseService doPurchaseService;
	ThreadLocal<TempImportTaskBizTypeEnum> bizTypeLocal = new ThreadLocal<TempImportTaskBizTypeEnum>();

	private static final String HEADER_ISBN = "条形码";
	private static final String HEADER_STOCK = "库存量";
	private static final String HEADER_SHOP_STATUS = "店铺状态";
	private static final String HEADER_SHOP_TITLE = "标题";
	private static final String HEADER_SHOP_CATEGORY_ID = "类目ID";
	private static final String HEADER_SHOP_FORBIDDEN = "禁止发布";
	// 支持的业务类型描述
	private static String supportedBizTypes = HEADER_STOCK + "," + HEADER_SHOP_STATUS + "," + HEADER_SHOP_TITLE + ","
			+ HEADER_SHOP_CATEGORY_ID + "," + HEADER_SHOP_FORBIDDEN;

	@RequiresPermissions(value = { "data:dataInfo" })
	@RequestMapping(value = "/uploadTempRecord")
	public String upload(@RequestParam MultipartFile excelFile, String memo, ModelMap map) {
		try {
			InputStream fileInputStream = excelFile.getInputStream();
			return super.upload(fileInputStream, memo, map);
		} catch (IOException e) {
			e.printStackTrace();
			map.put("actionError", "传入文件为空");
			return getFailPage();
		}
	}

	@Override
	protected String initTemplatePath() {
		return null;
	}

	@Override
	protected String subCheckFileFormat(List<String> factHeader) {
		if (!factHeader.get(0).equals(HEADER_ISBN)) {
			return "格式错误，第一列必须是" + HEADER_ISBN;
		}
		String secondCol = factHeader.get(1);
		if (secondCol.equals(HEADER_STOCK)) {
			bizTypeLocal.set(TempImportTaskBizTypeEnum.STOCK);
			return null;
		}
		if (secondCol.equals(HEADER_SHOP_STATUS)) {
			bizTypeLocal.set(TempImportTaskBizTypeEnum.SHOP_STATUS);
			return null;
		}
		if (secondCol.equals(HEADER_SHOP_TITLE)) {
			bizTypeLocal.set(TempImportTaskBizTypeEnum.SHOP_TITLE);
			return null;
		}
		if (secondCol.equals(HEADER_SHOP_CATEGORY_ID)) {
			bizTypeLocal.set(TempImportTaskBizTypeEnum.SHOP_CATEGORY_ID);
			return null;
		}
		if (secondCol.equals(HEADER_SHOP_FORBIDDEN)) {
			bizTypeLocal.set(TempImportTaskBizTypeEnum.TAOBAO_FORBIDDEN);
			return null;
		}
		return "格式错误，无法识别的业务类型，第二列必须是{" + supportedBizTypes + "}之一";
	}

	@Override
	protected Logger initLogger() {
		return Logger.getLogger(TempImportUploadController.class);
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
	protected void saveImportRecord(List<TempImportDTO> list, String memo) {
		doPurchaseService.addTempImportTask(list, bizTypeLocal.get(), memo);
	}

	@Override
	protected Map<String, Integer> initPropMapping() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("isbn", 0);
		map.put("data", 1);
		map.put("additionalInfo", 2);// 附加信息，不做检查
		return map;
	}

	public void setDoPurchaseService(DoPurchaseService doPurchaseService) {
		this.doPurchaseService = doPurchaseService;
	}

	@Override
	protected String getSuccessPage() {
		return "redirect:/purchase/viewTempImportTask";
	}

	@Override
	protected String getFailPage() {
		return "error";
	}
}
