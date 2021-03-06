package com.zis.bookinfo.controller;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.bean.BookinfoDetail;
import com.zis.bookinfo.bean.BookinfoStatus;
import com.zis.bookinfo.dto.BookInfoDTO;
import com.zis.bookinfo.service.BookService;
import com.zis.bookinfo.util.BookMetadataSource;
import com.zis.bookinfo.util.ConstantString;
import com.zis.common.mvc.ext.QueryUtil;
import com.zis.common.util.ZisUtils;

@Controller
@RequestMapping(value = "/bookInfo")
public class BookSaveOrUpdateController {

	private static Logger logger = Logger.getLogger(BookSaveOrUpdateController.class);
	
	//跳转地址标示 修改跳入ALTER_BOOK，新增跳入ADD_BOOK
	private final String ALTER_BOOK = "alterBook";
	private final String ADD_BOOK = "addBook";

	@Autowired
	private BookService bookService;
	
	@RequiresPermissions(value="bookInfo:saveOrUpdate")
	@RequestMapping(value = "/saveOrUpdate")
	public String saveOrUpdate(@Valid @ModelAttribute("bookInfoDTO") BookInfoDTO bookInfoDTO, BindingResult br,
			ModelMap map) {
		map.put("book", bookInfoDTO);
		if (br.hasErrors()) {
			return getUrl(bookInfoDTO.getUrlType());
		}
		if (bookInfoDTO.getBookPrice() < 0) {
			map.put("actionError", "bookPrice 价格必须大于0");
			return "error";
		}
		try {
			Bookinfo book;
			BookinfoDetail detail;
			if (bookInfoDTO.getId() != null) {
				Bookinfo bi = bookService.findBookById(bookInfoDTO.getId());
				book = buildBook(bi, bookInfoDTO);
				// 审核
				if (ConstantString.CHECKOk.equals(bookInfoDTO.getOperateType())) {
					this.bookService.updateBookForCheckOK(book);
				}
				// 废弃
				else if (ConstantString.NOTUSE.equals(bookInfoDTO.getOperateType())) {
					this.bookService.updateBookForDisable(bookInfoDTO.getId());
				}
				// 修改
				else {
					detail = buildBookInfoDetail(bookInfoDTO);
					book.setBookStatus(bi.getBookStatus());
					bookService.updateBook(book, detail);
				}
			}
			// 新增
			else {
				book = buildBook(new Bookinfo(), bookInfoDTO);
				detail = buildBookInfoDetail(bookInfoDTO);
				book.setOutId(bookInfoDTO.getOutId());
				book.setBookStatus(ConstantString.USEFUL);
				bookService.addBook(book, detail);// XXX 暂时不处理图书详情
				// 检查系统中是否存在相似记录
				QueryUtil<Bookinfo> query = new QueryUtil<Bookinfo>();
				query.like("bookAuthor", "%" + book.getBookAuthor() + "%");
				query.eq("bookPublisher", book.getBookPublisher());
				query.ne("bookStatus", BookinfoStatus.DISCARD);
				Specification<Bookinfo> spec = query.getSpecification();
				List<Bookinfo> samebookList = bookService.findBySpecificationAll(spec);
				if (samebookList != null && samebookList.size() > 1) {
					// 如果存在相似记录，则跳转到图书列表页由用户操作
					map.put("actionMessage", "新添加的图书在系统中有相似记录，请检查是否有重复记录，并将不同版本的图书设置到同一分组。");
					return "forward:/bookInfo/getAllBooks";
				}
			}
			map.put("actionMessage", bookInfoDTO.getBookName() + "已保存");
			return getUrl(bookInfoDTO.getUrlType());
		} catch (Exception e) {
			map.put("actionError", "操作失败:" + e.getMessage());
			e.printStackTrace();
			logger.error("操作失败:" + e.getMessage(), e);
			return "error";
		}
	}

	private Bookinfo buildBook(Bookinfo book, BookInfoDTO bookInfoDTO) {
		book.setIsbn(bookInfoDTO.getIsbn());
		book.setBookName(bookInfoDTO.getBookName());
		book.setBookAuthor(bookInfoDTO.getBookAuthor());
		book.setBookPublisher(bookInfoDTO.getBookPublisher());
		book.setPublishDate(bookInfoDTO.getPublishDate());
		book.setBookPrice(bookInfoDTO.getBookPrice());
		book.setBookEdition(bookInfoDTO.getBookEdition());
		book.setIsNewEdition(bookInfoDTO.getIsNewEdition());
		book.setRepeatIsbn(bookInfoDTO.getRepeatIsbn());
		return book;
	}
	
	private String getUrl(String type){
		if(ADD_BOOK.equals(type)){
			return "bookinfo/addBook";
		} 
		if(ALTER_BOOK.equals(type)){
			return "bookinfo/alterBook";
		}
		return "error";
	}
	
	private BookinfoDetail buildBookInfoDetail(BookInfoDTO bookInfoDTO) {
		// 如果页面中未填写任何信息，返回null（业务层不会对detail做任何处理）
		if (isBlank(bookInfoDTO.getImageUrl()) && isBlank(bookInfoDTO.getTaobaoTitle())
				&& isBlank(bookInfoDTO.getSummary()) && isBlank(bookInfoDTO.getCatalog())
				&& bookInfoDTO.getTaobaoCatagoryId() == null) {
			return null;
		}
		BookinfoDetail detail = null;
		if (bookInfoDTO.getId() != null) {
			detail = bookService.findBookInfoDetailByBookId(bookInfoDTO.getId());
		}
		// DB中无记录，并且页面中未填写任何信息，返回null
		if (detail == null) {
			detail = new BookinfoDetail();
			detail.setBookid(bookInfoDTO.getId());
			detail.setImageUrl(bookInfoDTO.getImageUrl());
			detail.setCatalog(bookInfoDTO.getCatalog());
			detail.setSummary(bookInfoDTO.getSummary());
			detail.setTaobaoForbidden(false);
			detail.setTaobaoTitle(bookInfoDTO.getTaobaoTitle());
			detail.setTaobaoCatagoryId(bookInfoDTO.getTaobaoCatagoryId());
			detail.setSource(BookMetadataSource.USER);
			detail.setGmtCreate(ZisUtils.getTS());
			detail.setGmtModify(ZisUtils.getTS());
			return detail;
		} else {
			if (!isBlank(bookInfoDTO.getImageUrl())) {
				detail.setImageUrl(bookInfoDTO.getImageUrl());
			}
			if (!isBlank(bookInfoDTO.getTaobaoTitle())) {
				detail.setTaobaoTitle(bookInfoDTO.getTaobaoTitle());
			}
			if (!isBlank(bookInfoDTO.getSummary())) {
				detail.setSummary(bookInfoDTO.getSummary());
			}
			if (!isBlank(bookInfoDTO.getCatalog())) {
				detail.setCatalog(bookInfoDTO.getCatalog());
			}
			if (bookInfoDTO.getTaobaoCatagoryId() != null) {
				detail.setTaobaoCatagoryId(bookInfoDTO.getTaobaoCatagoryId());
			}
			return detail;
		}
	}
}
