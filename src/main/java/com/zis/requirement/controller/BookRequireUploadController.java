package com.zis.requirement.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.zis.common.controllertemplate.CommonImportController;
import com.zis.requirement.dto.BookRequireUploadDTO;

/**
 * 导入书单的Action
 * 
 * @author yz
 * 
 */
@Controller
@RequestMapping(value = "/requirement")
public class BookRequireUploadController extends CommonImportController<BookRequireUploadDTO> {

	// TODO 验证框架
	// @Validations(requiredFields = {
	// @RequiredFieldValidator(fieldName = "excelFile", key = "文件必须输入"),
	// @RequiredFieldValidator(fieldName = "college", key = "操作员必须输入"),
	// @RequiredFieldValidator(fieldName = "operator", key = "操作员必须输入"),
	// @RequiredFieldValidator(fieldName = "memo", key = "操作备注必须输入"), })
	@RequestMapping(value = "/uploadBookRequirement")
	public String upload(@RequestParam MultipartFile excelFile, String memo) {
		try {
			InputStream fileInputStream = excelFile.getInputStream();
			return super.upload(fileInputStream, memo);
		} catch (IOException e) {
			e.printStackTrace();
			return getFailPage();
		}

	}

	@Override
	protected String initTemplatePath() {
		return "书单模板.xls";
	}

	@Override
	protected Logger initLogger() {
		return Logger.getLogger(BookRequireUploadController.class);
	}

	@Override
	protected BookRequireUploadDTO getInstance() {
		return new BookRequireUploadDTO();
	}

	@Override
	protected String getFaildRecordMessage(BookRequireUploadDTO failRecord) {
		return null;
	}

	@Override
	protected void saveImportRecord(List<BookRequireUploadDTO> list, String memo) {
		// this.bookAmountService.saveTempBookRequireImportDetails(list,
		// college, operator,
		// memo);
	}

	@Override
	protected Map<String, Integer> initPropMapping() {
		Map<String, Integer> mapping = new HashMap<String, Integer>();
		mapping.put("isbn", 0);
		mapping.put("bookName", 1);
		mapping.put("bookAuthor", 2);
		mapping.put("bookEdition", 3);
		mapping.put("bookPublisher", 4);
		mapping.put("college", 5);
		mapping.put("institute", 6);
		mapping.put("partName", 7);
		mapping.put("grade", 8);
		mapping.put("term", 9);
		mapping.put("classNum", 10);
		mapping.put("amount", 11);
		return mapping;
	}

	@Override
	protected String getSuccessPage() {
		return "redirect:/requirement/viewBookRequireImportTask";
	}

	@Override
	protected String getFailPage() {
		return "error";
	}

}
