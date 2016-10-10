package com.zis.toolkit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zis.toolkit.service.BookInfoToolkit;

/**
 * 图书名称修复工具
 * 
 * @author yz
 * 
 */
@Controller
@RequestMapping(value = "/toolkit")
public class BookNameFixController extends BaseBookFixController {

	@Autowired
	private BookInfoToolkit bookInfoToolkit;

	/**
	 * 修复图书名称
	 * 
	 * @return
	 */
	// TODO 验证框架
	// @Validations(requiredStrings = {
	// @RequiredStringValidator(fieldName = "startLabel", trim = true, key =
	// "起始字符不能为空"),
	// @RequiredStringValidator(fieldName = "keyword", trim = true, key =
	// "关键字不能为空"), })
	@RequestMapping(value = "/batchFixBookName")
	public String fixBookName(ModelMap context, String startLabel, String keyword) {
		List<String> list = bookInfoToolkit.updateFixBookName(startLabel, keyword);
		showResult(list, context);
		return "toolkit/toolkit";
	}
}
