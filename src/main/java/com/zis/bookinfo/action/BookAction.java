package com.zis.bookinfo.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.bean.BookinfoAid;
import com.zis.bookinfo.service.BookService;
import com.zis.bookinfo.util.ConstantString;
import com.zis.common.util.Page;
import com.zis.common.util.PaginationQueryUtil;
import com.zis.common.util.ZisUtils;
import com.zis.purchase.biz.DoPurchaseService;

public class BookAction extends ActionSupport implements SessionAware,
		RequestAware {
	private static final long serialVersionUID = 1L;
	/** 待审核记录数 */
	private static final String BOOKINFO_WAIT_COUNT = "bookAction.waitingCount";
	/** 最近一次查询待审核记录数的时间 */
	private static final String BOOKINFO_WAIT_COUNT_TIME = "bookAction.waitingCountTime";
	private static final int BOOKINFO_WAIT_COUNT_EXPIRE_TIME = 30 * 60 * 1000;// 临时提取待审核数量的过期时间

	private Map<String, Object> request;
	private Map<String, Object> session;
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

	private Integer pageNow;

	private Logger logger = Logger.getLogger(BookAction.class);

	private BookService bookService;
	private DoPurchaseService doPurchaseService;

	/**
	 * 查询所有图书
	 * 
	 * @return
	 */
	public String getAllBooks() {
		// 处理GET请求中的中文字符
		preProcessGetRequestCHN();
		logger.debug("start:" + System.currentTimeMillis());
		// 待审核数
		int waitCheckCount = getWaitingCount();
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
		ActionContext context = ActionContext.getContext();
		context.put("waitCheckCount", waitCheckCount + "");
		context.put("ALLBOOKS", list);
		context.put("bookISBN", bookISBN);
		context.put("bookName", bookName);
		context.put("bookAuthor", bookAuthor);
		context.put("bookPublisher", bookPublisher);
		setQueryConditionToPage(context);
		context.put("pageNow", pageNow);
		if (page.isHasPre()) {
			context.put("prePage", pageNow - 1);
		}
		if (page.isHasNext()) {
			context.put("nextPage", pageNow + 1);
		}
		return SUCCESS;
	}

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
	public String findBookById() {
		int id = Integer.parseInt(bookId);//
		ActionContext ctx = ActionContext.getContext();
		Bookinfo book = bookService.findBookById(id);
		if (book == null) {
			return ERROR;
		}
		ctx.put("book", book);
		ctx.put("bookId", book.getId());
		ctx.put("isNewEdition", book.getIsNewEdition());
		ctx.put("repeatIsbn", book.getRepeatIsbn());
		return SUCCESS;
	}

	/**
	 * 批量操作图书
	 * 
	 * @return
	 */
	public String batchOperate() {
		try {
			// 设置成不同版本
			if (BookBatchOperateType.SET_TO_GROUP.equals(operateType)) {
				bookService.updateSameBooksToOneGroup(batchSelectedItem);
				return SUCCESS;
			}
			// 设置成相关图书
			else if (BookBatchOperateType.SET_TO_RELATED.equals(operateType)) {
				bookService.updateRelatedBooksToOneGroup(batchSelectedItem);
				return SUCCESS;
			}
			// 批量删除
			else if (BookBatchOperateType.BATCH_DELETE.equals(operateType)) {
				bookService.updateBookForBatchDelete(batchSelectedItem);
				return SUCCESS;
			}
			// 批量拉黑
			else if (BookBatchOperateType.BATCH_ADD_TO_BLACK_LIST
					.equals(operateType)) {
				for (Integer bookId : batchSelectedItem) {
					doPurchaseService.addBlackList(bookId);
				}
				return SUCCESS;
			} else {
				this.addActionError("系统错误，不支持的操作类型" + operateType);
				return ERROR;
			}
		} catch (Exception e) {
			logger.error("操作失败，" + e.getMessage(), e);
			this.addActionError("操作失败，" + e.getMessage());
			return ERROR;
		}
	}

	/**
	 * 展示同书不同版的一组书
	 * 
	 * @return
	 */
	public String showGroupList() {
		ActionContext context = ActionContext.getContext();
		// 通过组的id将查询到的集合放入容器
		if (groupId != null) {
			List<Bookinfo> groupList = bookService.getBooksByGroupId(groupId);
			context.put("relateList", groupList);
			session.put("relateList", groupList);
			context.put("pageType", ConstantString.PAGEGROUP);
			// 关联id不为空则将关联的信息房屋容器中
		} else if (relateId != null) {
			List<Bookinfo> relateList = bookService
					.getBooksByRelateId(relateId);
			context.put("relateList", relateList);
			session.put("relateList", relateList);
			context.put("pageType", ConstantString.PAGEGRELATE);
		}
		return SUCCESS;
	}

	/**
	 * 移除关联id XXX 移除成功后，回到原页面
	 * 
	 * @return
	 */
	public String removeRelateId() {
		int ID = Integer.parseInt(id);
		bookService.removeRelateId(ID, pageType);
		return SUCCESS;
	}

	/**
	 * 移除所有信息
	 * 
	 * @return
	 */
	public String removeAll() {
		@SuppressWarnings("unchecked")
		List<Bookinfo> list = (List<Bookinfo>) session.get("relateList");
		this.bookService.removeAllRelation(list, pageType);
		return SUCCESS;
	}

	//
	public String getWaitCheckList() {
		List<Bookinfo> list = bookService.getWaitCheckList();
		ActionContext context = ActionContext.getContext();
		context.put("ALLBOOKS", list);
		return SUCCESS;
	}

	/**
	 * 调整图书信息<br/>
	 * 1.处理一码多书<br/>
	 * 2.把相同图书关联到一起
	 * 
	 * @return
	 */
	public String adjustBooks() {
		bookService.processOneISBNToMultiBooks();
		bookService.processSameBooksToOneGroup();
		return SUCCESS;
	}

	/**
	 * 智能分析哪些书是相同的
	 * 
	 * @return
	 */
	public String analysisSameBook() {
		this.bookService.analysisSimilarityBook();
		return SUCCESS;
	}

	/**
	 * 展示疑似相同图书-列表
	 * 
	 * @return
	 */
	public String showSameBookList() {
		DetachedCriteria criteria = buildSimilaritySearchContition();

		criteria.add(Restrictions.gt("totalCount", 1));
		// 分页查询
		if (pageNow == null || pageNow < 1) {
			pageNow = 1;
		}
		Integer totalCount = PaginationQueryUtil.getTotalCount(criteria);
		Page page = Page.createPage(pageNow, 100, totalCount);
		@SuppressWarnings("unchecked")
		List<BookinfoAid> sameBookList = PaginationQueryUtil.paginationQuery(
				criteria, page);

		// 将返回结果存入ActionContext中
		ActionContext context = ActionContext.getContext();
		context.put("similarityCheckLevel", similarityCheckLevel);
		context.put("sameBookList", sameBookList);
		context.put("pageNow", pageNow);
		if (page.isHasPre()) {
			context.put("prePage", pageNow - 1);
		}
		if (page.isHasNext()) {
			context.put("nextPage", pageNow + 1);
		}
		return SUCCESS;
	}

	private DetachedCriteria buildSimilaritySearchContition() {
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
	public String showSameBooks() {
		if (StringUtils.isBlank(sameBookIds)) {
			return ERROR;
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
				if (book != null
						&& !ConstantString.ABANDON.equals(book.getBookStatus())) {
					list.add(book);
				}
			} catch (Exception e) {
				return ERROR;
			}
		}
		// 将返回结果存入ActionContext中
		ActionContext context = ActionContext.getContext();
		context.put("ALLBOOKS", list);
		return SUCCESS;
	}

	public BookService getBookService() {
		return bookService;
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public Integer getPageNow() {
		return pageNow;
	}

	public void setPageNow(Integer pageNow) {
		this.pageNow = pageNow;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookISBN() {
		return bookISBN;
	}

	public void setBookISBN(String bookISBN) {
		this.bookISBN = bookISBN;
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public Integer[] getBatchSelectedItem() {
		return batchSelectedItem;
	}

	public void setBatchSelectedItem(Integer[] batchSelectedItem) {
		this.batchSelectedItem = batchSelectedItem;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getRelateId() {
		return relateId;
	}

	public void setRelateId(String relateId) {
		this.relateId = relateId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPageType() {
		return pageType;
	}

	public void setPageType(String pageType) {
		this.pageType = pageType;
	}

	public Map<String, Object> getRequest() {
		return request;
	}

	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

	public String getPageSource() {
		return pageSource;
	}

	public void setPageSource(String pageSource) {
		this.pageSource = pageSource;
	}

	public String getSameBookIds() {
		return sameBookIds;
	}

	public void setSameBookIds(String sameBookIds) {
		this.sameBookIds = sameBookIds;
	}

	public String getSimilarityCheckLevel() {
		return similarityCheckLevel;
	}

	public void setSimilarityCheckLevel(String similarityCheckLevel) {
		this.similarityCheckLevel = similarityCheckLevel;
	}

	public void setBookPublisher(String bookPublisher) {
		this.bookPublisher = bookPublisher;
	}
	
	public Boolean getStrictBookName() {
		return strictBookName;
	}

	public void setStrictBookName(Boolean strictBookName) {
		this.strictBookName = strictBookName;
	}

	public void setDoPurchaseService(DoPurchaseService doPurchaseService) {
		this.doPurchaseService = doPurchaseService;
	}
}
