package com.zis.toolkit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zis.toolkit.service.BookInfoToolkit;

/**
 * 图书版次修复工具
 * 
 * @author yz
 * 
 */
@Controller
@RequestMapping(value = "/toolkit")
public class BookEditionFixController extends BaseBookFixController {

	@Autowired
	private BookInfoToolkit bookInfoToolkit;

	/**
	 * 批量修复版次：删除书名中“修订版”、“修订本”，并追加到版次最后
	 * 
	 * @return
	 */
	@RequestMapping(value = "/batchFixEditionByBookName")
	public String batchFixEditionByBookName(ModelMap context) {
		List<String> list = bookInfoToolkit.updateFixEditionByBookName("修订版");
		List<String> list2 = bookInfoToolkit.updateFixEditionByBookName("修订本");
		list.addAll(list2);
		showResult(list, context);
		return "toolkit/toolkit";
	}

	/**
	 * 批量修复版次：使用书名中的“第X版”代替版次的值
	 * 
	 * @return
	 */
	@RequestMapping(value = "/batchReplaceEditionByBookName")
	public String batchReplaceEditionByBookName(ModelMap context) {
		List<String> list = bookInfoToolkit.updateReplaceEditionByBookName();
		showResult(list, context);
		return "toolkit/toolkit";
	}
}
