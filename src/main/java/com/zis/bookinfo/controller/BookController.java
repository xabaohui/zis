package com.zis.bookinfo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.bean.BookinfoAid;
import com.zis.bookinfo.bean.BookinfoDetail;
import com.zis.bookinfo.dto.BookInfoAndDetailDTO;
import com.zis.bookinfo.service.BookService;
import com.zis.bookinfo.util.ConstantString;
import com.zis.common.mvc.ext.WebHelper;
import com.zis.common.util.PaginationQueryUtil;
import com.zis.common.util.ZisUtils;
import com.zis.purchase.biz.DoPurchaseService;

@Controller
@RequestMapping(value = "/bookInfo")
public class BookController {

	/** 待审核记录数 */
	private static final String BOOKINFO_WAIT_COUNT = "bookAction.waitingCount";
	/** 最近一次查询待审核记录数的时间 */
	private static final String BOOKINFO_WAIT_COUNT_TIME = "bookAction.waitingCountTime";
	private static final int BOOKINFO_WAIT_COUNT_EXPIRE_TIME = 30 * 60 * 1000;// 临时提取待审核数量的过期时间

	private Logger logger = Logger.getLogger(BookController.class);
	@Autowired
	private BookService bookService;
	@Autowired
	private DoPurchaseService doPurchaseService;

	/**
	 * 查询所有图书
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getAllBooks")
	public String getAllBooks(ModelMap model, String bookName, Boolean strictBookName, String bookISBN,
			String bookAuthor, String bookPublisher, HttpServletRequest request) {
		logger.debug("start:" + System.currentTimeMillis());
		// TODO 创建查询条件

		// 分页查询
		Pageable page = WebHelper.buildPageRequest(request);
		Page<Bookinfo> list = this.bookService.findAll(page);

		// 待审核数
		int waitCheckCount = getWaitingCount(request);

		// 将返回结果存入ActionContext中
		model.put("waitCheckCount", waitCheckCount + "");
		model.put("list", list.getContent());
		setQueryConditionToPage(model, bookISBN, bookName, strictBookName, bookAuthor, bookPublisher);
		// model.put("page", page.getPageNumber());
		if (page.hasPrevious()) {
			model.put("prePage", page.previousOrFirst().getPageNumber());
		}
		if (page.next() != null) {
			model.put("nextPage", page.next().getPageNumber());
		}
		return "bookinfo/list";
	}

	/**
	 * 通过id查询放入容器中
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findBookById")
	public String findBookById(Integer bookId, ModelMap ctx) {
		Bookinfo book = bookService.findBookById(bookId);
		if (book == null) {
			// TODO 验证框架
			// this.addActionError("无此图书, bookId=" + bookId);
			return "error";
		}
		BookinfoDetail detail = bookService.findBookInfoDetailByBookId(bookId);
		BookInfoAndDetailDTO infoAndDetail = new BookInfoAndDetailDTO();
		BeanUtils.copyProperties(book, infoAndDetail);
		if (detail != null) {
			BeanUtils.copyProperties(detail, infoAndDetail);
		}
		ctx.put("book", infoAndDetail);
		ctx.put("bookId", book.getId());
		ctx.put("isNewEdition", book.getIsNewEdition());
		ctx.put("repeatIsbn", book.getRepeatIsbn());
		return "/bookinfo/alterBook";
	}

	/**
	 * 批量操作图书
	 * 
	 * @return
	 */
	@RequestMapping(value = "/batchOperateBooks")
	public String batchOperate(Integer[] batchSelectedItem, String operateType) {
		for (Integer i : batchSelectedItem) {
			System.out.println(i);
		}
		System.out.println(operateType);
		try {
			// 设置成不同版本
			if (BookBatchOperateType.SET_TO_GROUP.equals(operateType)) {
				bookService.updateSameBooksToOneGroup(batchSelectedItem);
				return "success";
			}
			// 设置成相关图书
			else if (BookBatchOperateType.SET_TO_RELATED.equals(operateType)) {
				bookService.updateRelatedBooksToOneGroup(batchSelectedItem);
				return "success";
			}
			// 批量删除
			else if (BookBatchOperateType.BATCH_DELETE.equals(operateType)) {
				bookService.updateBookForBatchDelete(batchSelectedItem);
				return "success";
			}
			// 批量拉黑
			else if (BookBatchOperateType.BATCH_ADD_TO_BLACK_LIST.equals(operateType)) {
				for (Integer bookId : batchSelectedItem) {
					doPurchaseService.addBlackList(bookId);
				}
				return "success";
			} else {
				// TODO 验证框架
				// this.addActionError("系统错误，不支持的操作类型" + operateType);
				return "error";
			}
		} catch (Exception e) {
			logger.error("操作失败，" + e.getMessage(), e);
			// TODO 验证框架
			// this.addActionError("操作失败，" + e.getMessage());
			return "error";
		}
	}

	/**
	 * 展示同书不同版的一组书
	 * 
	 * @return
	 */
	@RequestMapping(value = "/showGroupList")
	public String showGroupList(String relateId, String groupId, HttpSession session, ModelMap map) {
		// 通过组的id将查询到的集合放入容器
		if (groupId != null) {
			List<Bookinfo> groupList = bookService.getBooksByGroupId(groupId);
			map.put("relateList", groupList);
			session.setAttribute("relateList", groupList);
			map.put("pageType", ConstantString.PAGEGROUP);
			// 关联id不为setAttribute空则将关联的信息房屋容器中
		} else if (relateId != null) {
			List<Bookinfo> relateList = bookService.getBooksByRelateId(relateId);
			map.put("relateList", relateList);
			session.setAttribute("relateList", relateList);
			map.put("pageType", ConstantString.PAGEGRELATE);
		}
		return "/bookinfo/RelateList";
	}

	/**
	 * 移除关联id XXX 移除成功后，回到原页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/removeRelateId")
	public String removeRelateId(Integer id, String pageType) {
		pageType = "pageRelate";
		bookService.removeRelateId(id, pageType);
		return "success";
	}

	/**
	 * 移除所有信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/removeAll")
	public String removeAll(HttpSession session, String pageType) {
		@SuppressWarnings("unchecked")
		// List<Bookinfo>list1=bookService.getBooksByRelateId("555");
		// pageType="pageRelate";
		List<Bookinfo> list = (List<Bookinfo>) session.getAttribute("relateList");
		this.bookService.removeAllRelation(list, pageType);
		return "success";
	}

	@RequestMapping(value = "/getWaitCheckList")
	public String getWaitCheckList(ModelMap context) {
		// TODO 分页
		Pageable page = new PageRequest(0, 20);
		org.springframework.data.domain.Page<Bookinfo> list = bookService.getWaitCheckList(page);
		context.put("ALLBOOKS", list);
		return "bookinfo/dealWaitCheck";
	}

	/**
	 * 调整图书信息<br/>
	 * 1.处理一码多书<br/>
	 * 2.把相同图书关联到一起
	 * 
	 * @return
	 */
	@RequestMapping(value = "/adjustBooks")
	public String adjustBooks() {
		bookService.processOneISBNToMultiBooks();
		bookService.processSameBooksToOneGroup();
		return "success";
	}

	/**
	 * 智能分析哪些书是相同的
	 * 
	 * @return
	 */
	@RequestMapping(value = "/analysisSameBook")
	public String analysisSameBook() {
		this.bookService.analysisSimilarityBook();
		return "success";
	}

	/**
	 * 展示疑似相同图书-列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/showSameBooksList")
	public String showSameBookList(Integer pageNow, ModelMap context, String similarityCheckLevel) {
		DetachedCriteria criteria = buildSimilaritySearchContition(similarityCheckLevel);

		criteria.add(Restrictions.gt("totalCount", 1));
		// 分页查询
		if (pageNow == null || pageNow < 1) {
			pageNow = 1;
		}
		Integer totalCount = PaginationQueryUtil.getTotalCount(criteria);
		Page page = Page.createPage(pageNow, 100, totalCount);
		@SuppressWarnings("unchecked")
		List<BookinfoAid> sameBookList = PaginationQueryUtil.paginationQuery(criteria, page);

		// 将返回结果存入ModelMap中
		context.put("similarityCheckLevel", similarityCheckLevel);
		context.put("sameBookList", sameBookList);
		context.put("pageNow", pageNow);
		if (page.isHasPre()) {
			context.put("prePage", pageNow - 1);
		}
		if (page.isHasNext()) {
			context.put("nextPage", pageNow + 1);
		}
		return "/bookinfo/sameBook";
	}

	private DetachedCriteria buildSimilaritySearchContition(String similarityCheckLevel) {
		DetachedCriteria dc = DetachedCriteria.forClass(BookinfoAid.class);
		if (StringUtils.isBlank(similarityCheckLevel)) {
			similarityCheckLevel = "0";
		}
		try {
			Integer checkLevel = Integer.parseInt(similarityCheckLevel);
			dc.add(Restrictions.eq("checkLevel", checkLevel));
		} catch (NumberFormatException e) {
		}
		return dc;
	}

	/**
	 * 展示疑似相同图书-详情
	 * 
	 * @return
	 */
	@RequestMapping(value = "/showSameBooks")
	public String showSameBooks(@PathVariable String sameBookIds, ModelMap context) {
		if (StringUtils.isBlank(sameBookIds)) {
			return "error";
		}
		String ids = sameBookIds.replace("[", "");
		ids = ids.replace("]", "");
		String[] idArr = ids.split(",");
		List<Bookinfo> list = new ArrayList<Bookinfo>();
		for (String bookIdStr : idArr) {
			try {
				Integer id = Integer.valueOf(bookIdStr.trim());
				if (id == null) {
					continue;
				}
				Bookinfo book = this.bookService.findBookById(id);
				if (book != null && !ConstantString.ABANDON.equals(book.getBookStatus())) {
					list.add(book);
				}
			} catch (Exception e) {
				return "error";
			}
		}
		// 将返回结果存入modelMap中
		context.put("ALLBOOKS", list);
		System.out.println(list);
		return "bookinfo/sameBookList";
	}

	private void setQueryConditionToPage(ModelMap context, String bookISBN, String bookName, Boolean strictBookName,
			String bookAuthor, String bookPublisher) {
		StringBuilder condition = new StringBuilder();
		if (StringUtils.isNotBlank(bookISBN)) {
			condition.append("bookISBN=" + bookISBN + "&");
		}
		if (StringUtils.isNotBlank(bookName)) {
			condition.append("bookName=" + bookName + "&");
		}
		if (strictBookName != null && strictBookName == true) {
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

	private int getWaitingCount(HttpServletRequest request) {
		ServletContext servletContext = request.getSession().getServletContext();
		Date lastCountTime = (Date) servletContext.getAttribute(BOOKINFO_WAIT_COUNT_TIME);

		if (lastCountTime != null) {
			long diff = new Date().getTime() - lastCountTime.getTime();
			if (diff <= BOOKINFO_WAIT_COUNT_EXPIRE_TIME) {
				return (Integer) servletContext.getAttribute(BOOKINFO_WAIT_COUNT);
			}
		}

		int waitingCount = bookService.countWaitingBooks();
		servletContext.setAttribute(BOOKINFO_WAIT_COUNT, waitingCount);
		servletContext.setAttribute(BOOKINFO_WAIT_COUNT_TIME, new Date());
		return waitingCount;
	}

	// private String bookId;
	// private Integer batchSelectedItem[];
	// private String operateType;
	// private String relateId;
	// private String groupId;
	// private String id;
	// private String pageType; // 分组类型：同书分组和关联分组
	// private String pageSource; // 页面来源，点击下一页的，需要经过ISO-8859-1->UTF-8转码

	// 疑似相同图书的ID
	// private String sameBookIds;
	// private String similarityCheckLevel;

	private DetachedCriteria buildDetachedCriteria(String bookName, Boolean strictBookName, String bookISBN,
			String bookAuthor, String bookPublisher) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Bookinfo.class);
		if (!StringUtils.isBlank(bookName)) {
			if (strictBookName != null && strictBookName == true) {
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
			criteria.add(Restrictions.like("bookAuthor", "%" + bookAuthor + "%"));
		if (!StringUtils.isBlank(bookPublisher)) {
			criteria.add(Restrictions.eq("bookPublisher", bookPublisher));
		}
		// 状态为废弃的不查询
		criteria.add(Restrictions.ne("bookStatus", ConstantString.ABANDON));
		return criteria;
	}
}
