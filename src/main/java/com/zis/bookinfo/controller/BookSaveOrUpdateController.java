package com.zis.bookinfo.controller;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.bean.BookinfoDetail;
import com.zis.bookinfo.bean.BookinfoStatus;
import com.zis.bookinfo.dto.BookInfoDTO;
import com.zis.bookinfo.service.BookService;
import com.zis.bookinfo.util.BookMetadataSource;
import com.zis.bookinfo.util.ConstantString;
import com.zis.common.util.ZisUtils;

@Controller
@RequestMapping(value = "/bookInfo")
public class BookSaveOrUpdateController {

	private static Logger logger = Logger
			.getLogger(BookSaveOrUpdateController.class);

	@Autowired
	private BookService bookService;

	// TODO 缺少springMVC验证框架jar包，及其配置
	// @Validations(
	// /* �ǿ��ַ� */
	// requiredStrings = {
	// @RequiredStringValidator(fieldName = "isbn", trim = true, key =
	// "isbn不能为空"),
	// @RequiredStringValidator(fieldName = "bookName", trim = true, key =
	// "书名不能为空"),
	// @RequiredStringValidator(fieldName = "bookAuthor", trim = true, key =
	// "作者不能为空"),
	// @RequiredStringValidator(fieldName = "bookEdition", trim = true, key =
	// "版次不能为空"),
	// @RequiredStringValidator(fieldName = "bookPublisher", trim = true, key =
	// "出版社不能为空") },
	// /* �������� */
	// stringLengthFields = {
	// @StringLengthFieldValidator(fieldName = "bookName", maxLength = "128",
	// key = "书名不能超过128个字符"),
	// @StringLengthFieldValidator(fieldName = "isbn", maxLength = "30", key =
	// "isbn不能超过30个字符"),
	// @StringLengthFieldValidator(fieldName = "bookAuthor", maxLength = "50",
	// key = "作者不能超过50个字符"),
	// @StringLengthFieldValidator(fieldName = "bookPublisher", maxLength =
	// "30", key = "出版社不能超过30个字符"),
	// @StringLengthFieldValidator(fieldName = "groupId", maxLength = "20", key
	// = "ͬgroupId不能超过20个字符"),
	// @StringLengthFieldValidator(fieldName = "relateId", maxLength = "20", key
	// = "relateId不能超过20个字符"),
	//
	// },
	// /* �ǿ����� */
	// requiredFields = {
	// @RequiredFieldValidator(fieldName = "bookPrice", key = "价格不能为空"),
	// @RequiredFieldValidator(fieldName = "publishDate", key = "出版日期不能为空"),
	// },
	// /* ��ֵ */
	// intRangeFields = { @IntRangeFieldValidator(fieldName = "outId", min =
	// "1", key = "外部ID必须大于0") })
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

	@RequestMapping(value = "/saveOrUpdate")
	public String saveOrUpdate(BookInfoDTO bookInfoDTO) {
		 if (bookInfoDTO.getBookPrice() < 0) {
//		 addFieldError("bookPrice", "价格必须大于0");
		 return "error";
		 }
//		System.out.println(bookInfoDTO.getPublishDate());
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
				else if (ConstantString.NOTUSE.equals(bookInfoDTO
						.getOperateType())) {
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
				DetachedCriteria dc = DetachedCriteria.forClass(Bookinfo.class);
				dc.add(Restrictions.like("bookAuthor",
						"%" + book.getBookAuthor() + "%"));
				dc.add(Restrictions.eq("bookPublisher", book.getBookPublisher()));
				dc.add(Restrictions.ne("bookStatus", BookinfoStatus.DISCARD));
				List<Bookinfo> samebookList = bookService
						.findBookByCriteria(dc);
				if (samebookList != null && samebookList.size() > 1) {
					// 如果存在相似记录，则跳转到图书列表页由用户操作
					// TODO 缺少springMVC验证框架jar包，及其配置
					// this.addActionMessage("新添加的图书在系统中有相似记录，请检查是否有重复记录，并将不同版本的图书设置到同一分组。");
					return "forward:/bookInfo/list/1.html";
				}
			}
			// TODO 缺少springMVC验证框架jar包，及其配置
			// this.addActionMessage(bookInfoDTO.getBookName() + "已保存");
			return "bookinfo/addBook";
		} catch (Exception e) {
			// TODO 缺少springMVC验证框架jar包，及其配置
			// this.addActionError("操作失败:" + e.getMessage());
			logger.error("操作失败:" + e.getMessage(), e);
			return "error";
		}
	}

	private BookinfoDetail buildBookInfoDetail(BookInfoDTO bookInfoDTO) {
		// 如果页面中未填写任何信息，返回null（业务层不会对detail做任何处理）
		if (isBlank(bookInfoDTO.getImageUrl())
				&& isBlank(bookInfoDTO.getTaobaoTitle())
				&& isBlank(bookInfoDTO.getSummary())
				&& isBlank(bookInfoDTO.getCatalog())
				&& bookInfoDTO.getTaobaoCatagoryId() == null) {
			return null;
		}
		BookinfoDetail detail = null;
		if (bookInfoDTO.getId() != null) {
			detail = bookService
					.findBookInfoDetailByBookId(bookInfoDTO.getId());
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
			detail.setVersion(0);
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
