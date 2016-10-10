package com.zis.toolkit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zis.toolkit.service.BookInfoToolkit;

/**
 * 图书名称修复工具-批量替换
 * 
 * @author yz
 * 
 */
@Controller
@RequestMapping(value = "/toolkit")
public class BookNameReplaceController extends BaseBookFixController {

	@Autowired
	private BookInfoToolkit bookInfoToolkit;

	/**
	 * 修复图书名称
	 * 
	 * @return
	 */
	//TODO 验证框架
//	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "orig", trim = true, key = "原始内容不能为空") })
	@RequestMapping(value="/batchReplaceBookName")
	public String batchReplaceBookName(ModelMap context, String orig, String replace) {
		List<String> list = bookInfoToolkit.updateBatchReplaceBookName(orig, replace);
		showResult(list, context);
		return "toolkit/toolkit";
	}
}
