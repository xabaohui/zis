package com.zis.toolkit.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zis.toolkit.dto.BatchReplaceBookNameDTO;
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
	@RequiresPermissions(value = { "toolkit:batchReplaceBookName" })
	@RequestMapping(value = "/batchReplaceBookName")
	public String batchReplaceBookName(@Valid @ModelAttribute("dto") BatchReplaceBookNameDTO dto, BindingResult br,
			ModelMap context) {
		if (br.hasErrors()) {
			return "toolkit/toolkit";
		}
		List<String> list = bookInfoToolkit.updateBatchReplaceBookName(dto.getOrig(), dto.getReplace());
		showResult(list, context);
		return "toolkit/toolkit";
	}
}
