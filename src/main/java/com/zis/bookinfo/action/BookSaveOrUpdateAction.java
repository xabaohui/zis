package com.zis.bookinfo.action;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.bean.BookinfoStatus;
import com.zis.bookinfo.service.BookService;
import com.zis.bookinfo.util.ConstantString;

public class BookSaveOrUpdateAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger
			.getLogger(BookSaveOrUpdateAction.class);
	private Integer id;
	private Integer outId;
	private String isbn;
	private String bookName;
	private String bookAuthor;
	private String bookPublisher;
	private Date publishDate;
	private Float bookPrice;
	private String bookEdition;
	private Boolean isNewEdition;
	private Boolean repeatIsbn;
	private String bookStatus;
	private String operateType;

	private BookService bookService;

	@Validations(
	/* �ǿ��ַ� */
	requiredStrings = {
			@RequiredStringValidator(fieldName = "isbn", trim = true, key = "isbn不能为空"),
			@RequiredStringValidator(fieldName = "bookName", trim = true, key = "书名不能为空"),
			@RequiredStringValidator(fieldName = "bookAuthor", trim = true, key = "作者不能为空"),
			@RequiredStringValidator(fieldName = "bookEdition", trim = true, key = "版次不能为空"),
			@RequiredStringValidator(fieldName = "bookPublisher", trim = true, key = "出版社不能为空") },
	/* �������� */
	stringLengthFields = {
			@StringLengthFieldValidator(fieldName = "bookName", maxLength = "128", key = "书名不能超过128个字符"),
			@StringLengthFieldValidator(fieldName = "isbn", maxLength = "30", key = "isbn不能超过30个字符"),
			@StringLengthFieldValidator(fieldName = "bookAuthor", maxLength = "50", key = "作者不能超过50个字符"),
			@StringLengthFieldValidator(fieldName = "bookPublisher", maxLength = "30", key = "出版社不能超过30个字符"),
			@StringLengthFieldValidator(fieldName = "groupId", maxLength = "20", key = "ͬgroupId不能超过20个字符"),
			@StringLengthFieldValidator(fieldName = "relateId", maxLength = "20", key = "relateId不能超过20个字符"),

	},
	/* �ǿ����� */
	requiredFields = {
			@RequiredFieldValidator(fieldName = "bookPrice", key = "价格不能为空"),
			@RequiredFieldValidator(fieldName = "publishDate", key = "出版日期不能为空"),
	},
	/* ��ֵ */
	intRangeFields = { @IntRangeFieldValidator(fieldName = "outId", min = "1", key = "外部ID必须大于0") })
	private Bookinfo buildBook(Bookinfo book) {
		book.setIsbn(isbn);
		book.setBookName(bookName);
		book.setBookAuthor(bookAuthor);
		book.setBookPublisher(bookPublisher);
		book.setPublishDate(publishDate);
		book.setBookPrice(bookPrice);
		book.setBookEdition(bookEdition);
		book.setIsNewEdition(isNewEdition);
		book.setRepeatIsbn(repeatIsbn);
		return book;
	}

	public String saveOrUpdate() {
		if (bookPrice < 0) {
			addFieldError("bookPrice", "价格必须大于0");
			return INPUT;
		}
		try {
			Bookinfo book;
			if (id != null) {
				Bookinfo bi = bookService.findBookById(id);
				book = buildBook(bi);
				// 审核
				if (ConstantString.CHECKOk.equals(operateType)) {
					this.bookService.updateBookForCheckOK(book);
				}
				// 废弃
				else if (ConstantString.NOTUSE.equals(operateType)) {
					this.bookService.updateBookForDisable(id);
				}
				// 修改
				else {
					book.setBookStatus(bi.getBookStatus());
					bookService.updateBook(book);
				}
			} 
			// 新增
			else {
				book = buildBook(new Bookinfo());
				book.setOutId(outId);
				book.setBookStatus(ConstantString.USEFUL);
				bookService.addBook(book);
				// 检查系统中是否存在相似记录
				DetachedCriteria dc = DetachedCriteria.forClass(Bookinfo.class);
				dc.add(Restrictions.like("bookAuthor", "%" + book.getBookAuthor() + "%"));
				dc.add(Restrictions.eq("bookPublisher", book.getBookPublisher()));
				dc.add(Restrictions.ne("bookStatus", BookinfoStatus.DISCARD));
				List<Bookinfo> samebookList = bookService.findBookByCriteria(dc);
				if(samebookList != null && samebookList.size() > 1) {
					// 如果存在相似记录，则跳转到图书列表页由用户操作
					this.addActionMessage("新添加的图书在系统中有相似记录，请检查是否有重复记录，并将不同版本的图书设置到同一分组。");
					return "showRelated";
				}
			}
			return SUCCESS;
		} catch (Exception e) {
			this.addActionError("操作失败:" + e.getMessage());
			logger.error("操作失败:" + e.getMessage(), e);
			return ERROR;
		}
	}

//	// 检查图书是否已经和院校信息关联
//	private boolean checkBookinfoInUse(Integer bookId) {
//		List<Bookamount> list = this.bookAmountService
//				.findBookAmountByBookId(bookId);
//		return list != null && !list.isEmpty();
//	}

	public BookService getBookService() {
		return bookService;
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}

	public Integer getOutId() {
		return outId;
	}

	public void setOutId(Integer outId) {
		this.outId = outId;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	public String getBookPublisher() {
		return bookPublisher;
	}

	public void setBookPublisher(String bookPublisher) {
		this.bookPublisher = bookPublisher;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Float getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(Float bookPrice) {
		this.bookPrice = bookPrice;
	}

	public String getBookEdition() {
		return bookEdition;
	}

	public void setBookEdition(String bookEdition) {
		this.bookEdition = bookEdition;
	}

	public Boolean getIsNewEdition() {
		return isNewEdition;
	}

	public void setIsNewEdition(Boolean isNewEdition) {
		this.isNewEdition = isNewEdition;
	}

	public String getBookStatus() {
		return bookStatus;
	}

	public void setBookStatus(String bookStatus) {
		this.bookStatus = bookStatus;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public Boolean getRepeatIsbn() {
		return repeatIsbn;
	}

	public void setRepeatIsbn(Boolean repeatIsbn) {
		this.repeatIsbn = repeatIsbn;
	}
}
