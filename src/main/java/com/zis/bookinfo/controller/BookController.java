package com.zis.bookinfo.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.opensymphony.xwork2.ActionContext;
import com.zis.bookinfo.action.BookAction;
import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.bean.BookinfoDetail;
import com.zis.bookinfo.bean.BookinfoStatus;
import com.zis.bookinfo.dto.BookInfoAndDetailDTO;
import com.zis.bookinfo.service.BookService;
import com.zis.bookinfo.util.ConstantString;
import com.zis.common.util.Page;
import com.zis.common.util.PaginationQueryUtil;
import com.zis.purchase.biz.DoPurchaseService;

@Controller
@RequestMapping(value = "book")
public class BookController {
	
	/** 待审核记录数 */
	private static final String BOOKINFO_WAIT_COUNT = "bookAction.waitingCount";
	/** 最近一次查询待审核记录数的时间 */
	private static final String BOOKINFO_WAIT_COUNT_TIME = "bookAction.waitingCountTime";
	private static final int BOOKINFO_WAIT_COUNT_EXPIRE_TIME = 30 * 60 * 1000;// 临时提取待审核数量的过期时间

	private Logger logger = Logger.getLogger(BookController.class);
	
	@Autowired
	private BookService bookService;
	
	/**
	 * 查询所有图书
	 * 
	 * @return
	 */
	@RequestMapping(value = "list/{pageNow}.html")
	public String getAllBooks(@PathVariable Integer pageNow, ModelMap model) {
		// 处理GET请求中的中文字符
		// preProcessGetRequestCHN();
		logger.debug("start:" + System.currentTimeMillis());
		// 待审核数
		int waitCheckCount = 10;
		//int waitCheckCount = getWaitingCount();
		// 创建查询条件
		logger.debug("after count waiting records:"
				+ System.currentTimeMillis());
		DetachedCriteria criteria = buildDetachedCriteria();

		// 分页查询
		if (pageNow == null || pageNow < 1) {
			pageNow = 1;
		}
		Integer totalCount = PaginationQueryUtil.getTotalCount(criteria);
		Page page = Page
				.createPage(pageNow, Page.DEFAULT_PAGE_SIZE, totalCount);
		logger.debug("after page:" + System.currentTimeMillis());
		@SuppressWarnings("unchecked")
		List<Bookinfo> list = PaginationQueryUtil.paginationQuery(criteria,
				page);
		logger.debug("final:" + System.currentTimeMillis());

		// 将返回结果存入ActionContext中
		model.put("waitCheckCount", waitCheckCount + "");
		model.put("list", list);
//		model.put("bookISBN", bookISBN);
//		model.put("bookName", bookName);
//		model.put("bookAuthor", bookAuthor);
//		model.put("bookPublisher", bookPublisher);
//		setQueryConditionToPage(context);
		model.put("pageNow", pageNow);
		if (page.isHasPre()) {
			model.put("prePage", pageNow - 1);
		}
		if (page.isHasNext()) {
			model.put("nextPage", pageNow + 1);
		}
		return "bookinfo/list";
	}

	/*
	private void setQueryConditionToPage(ActionContext context) {
		StringBuilder condition = new StringBuilder();
		if (StringUtils.isNotBlank(bookISBN)) {
			condition.append("bookISBN=" + bookISBN + "&");
		}
		if (StringUtils.isNotBlank(bookName)) {
			condition.append("bookName=" + bookName + "&");
		}
		if(strictBookName != null && strictBookName == true) {
			condition.append("strictBookName=true&");
		}
		if (StringUtils.isNotBlank(bookAuthor)) {
			condition.append("bookAuthor=" + bookAuthor + "&");
		}
		if (StringUtils.isNotBlank(bookPublisher)) {
			condition.append("bookPublisher=" + bookPublisher + "&");
		}
		context.put("queryCondition", condition.toString());
	}

	// 从application中拿数据，如果无数据或者数据过期，则重新获取
	private int getWaitingCount() {
		Map<String, Object> application = ActionContext.getContext()
				.getApplication();
		Date lastCountTime = (Date) application.get(BOOKINFO_WAIT_COUNT_TIME);
		if (lastCountTime == null) {
			int waitingCount = bookService.getWaitCheckList().size();
			application.put(BOOKINFO_WAIT_COUNT, waitingCount);
			application.put(BOOKINFO_WAIT_COUNT_TIME, new Date());
			return waitingCount;
		}
		Date currentTime = new Date();
		long diff = currentTime.getTime() - lastCountTime.getTime();
		if (diff > BOOKINFO_WAIT_COUNT_EXPIRE_TIME) {
			int waitingCount = bookService.getWaitCheckList().size();
			application.put(BOOKINFO_WAIT_COUNT, waitingCount);
			application.put(BOOKINFO_WAIT_COUNT_TIME, currentTime);
			return waitingCount;
		} else {
			return (Integer) application.get(BOOKINFO_WAIT_COUNT);
		}
	}

	// 参数转换，重点处理中文get请求
	private void preProcessGetRequestCHN() {
		// 仅仅对带条件的分页查询使用转化
		if (!"pagination".equals(pageSource)) {
			return;
		}
		if (StringUtils.isNotBlank(bookName)) {
			bookName = ZisUtils.convertGetRequestCHN(bookName);
		}
		if (StringUtils.isNotBlank(bookAuthor)) {
			bookAuthor = ZisUtils.convertGetRequestCHN(bookAuthor);
		}
		if (StringUtils.isNotBlank(bookPublisher)) {
			bookPublisher = ZisUtils.convertGetRequestCHN(bookPublisher);
		}
	}
	*/
	
	private String bookName;
	private Boolean strictBookName;
	private String bookISBN;
	private String bookAuthor;
	private String bookPublisher;
	private String bookId;
	private Integer batchSelectedItem[];
	private String operateType;
	private String relateId;
	private String groupId;
	private String id;
	private String pageType; // 分组类型：同书分组和关联分组
	private String pageSource; // 页面来源，点击下一页的，需要经过ISO-8859-1->UTF-8转码

	// 疑似相同图书的ID
	private String sameBookIds;
	private String similarityCheckLevel;

	private DetachedCriteria buildDetachedCriteria() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Bookinfo.class);
		if (!StringUtils.isBlank(bookName)) {
			if(strictBookName != null && strictBookName == true) {
				criteria.add(Restrictions.eq("bookName", bookName));
			} else {
				criteria.add(Restrictions.like("bookName", "%" + bookName + "%"));
			}
		}
		// ISBN不为空则添加条件
		if (!StringUtils.isBlank(bookISBN)) {
			String[] isbns = bookISBN.split(",");
			if (isbns.length == 1) {
				criteria.add(Restrictions.eq("isbn", bookISBN));
			} else {
				criteria.add(Restrictions.in("isbn", isbns));
			}
		}
		// 模糊查询作者
		if (!StringUtils.isBlank(bookAuthor))
			criteria.add(Restrictions
					.like("bookAuthor", "%" + bookAuthor + "%"));
		if (!StringUtils.isBlank(bookPublisher)) {
			criteria.add(Restrictions.eq("bookPublisher", bookPublisher));
		}
		// 状态为废弃的不查询
		criteria.add(Restrictions.ne("bookStatus", ConstantString.ABANDON));
		return criteria;
	}
	
	/**
	 * 通过id查询放入容器中
	 * 
	 * @return
	 */
	@RequestMapping(value="update/{bookId}.html")
	public String findBookById(@PathVariable(value="bookId") Integer bookId, ModelMap model) {
		int id = bookId;//
		Bookinfo book = bookService.findBookById(id);
		if (book == null) {
			// TODO
			//this.addActionError("无此图书, bookId=" + bookId);
			//return ERROR;
		}
		BookinfoDetail detail = bookService.findBookInfoDetailByBookId(id);
		BookInfoAndDetailDTO infoAndDetail = new BookInfoAndDetailDTO();
		BeanUtils.copyProperties(book, infoAndDetail);
		if(detail != null) {
			BeanUtils.copyProperties(detail, infoAndDetail);
		}
		model.put("book", infoAndDetail);
		model.put("bookId", book.getId());
		model.put("isNewEdition", book.getIsNewEdition());
		model.put("repeatIsbn", book.getRepeatIsbn());
		return "bookinfo/alterBook";
	}
}
