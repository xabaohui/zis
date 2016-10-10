package com.zis.toolkit.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.service.BookService;
import com.zis.toolkit.service.BookInfoToolkit;

/**
 * 图书作者修复工具
 * 
 * @author yz
 * 
 */
@Controller
@RequestMapping(value = "/toolkit")
public class BookAuthorFixController extends BaseBookFixController {

	@Autowired
	private BookInfoToolkit bookInfoToolkit;

	@Autowired
	private BookService bookService;

	/**
	 * 修复图书作者
	 * 
	 * @return
	 */
	@RequestMapping(value = "/fixBookAuthor")
	public String fixBookAuthor(ModelMap context) {
		Pageable page = new PageRequest(0, 500);
		List<String> list = new ArrayList<String>();
		Page<Bookinfo> pageList = this.bookService.findAll(page);
		while (pageList.hasNext()) {
			List<Bookinfo> bookList = this.bookService.findAll(page).getContent();
			List<String> result = bookInfoToolkit.updateFixBookAuthor(bookList);
			list.addAll(result);
			page = new PageRequest(page.next().getPageNumber(), 500);
		}
		showResult(list, context);
		return "toolkit/toolkit";
	}
}
