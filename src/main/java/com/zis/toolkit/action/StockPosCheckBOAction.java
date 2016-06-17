package com.zis.toolkit.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.service.BookService;
import com.zis.purchase.bean.TempImportDetail;
import com.zis.purchase.bean.TempImportDetailStatus;
import com.zis.purchase.biz.DoPurchaseService;

/**
 * 库位信息校准Action
 * <p/>
 * 
 * @author yz
 * 
 */
public class StockPosCheckBOAction {

	private static final String SESSION_KEY_RECORDS = "stockPosCheck.InputRecords";
	private static final String SESSION_KEY_POS = "stockPosCheck.StockPos";
	private static final Integer taskId = 39;//FIXME 

	private DoPurchaseService doPurchaseService;
	private BookService bookService;
	
	public StockPosCheckResult addBookRecord(String isbn, HttpSession session) {
		if(StringUtils.isBlank(isbn)) {
			return new StockPosCheckResult(false, "条形码不能为空");
		}
		// 跳过错误条码和一码多书
		List<Bookinfo> books = this.bookService.findBookByISBN(isbn);
		if(books == null || books.isEmpty()) {
			return new StockPosCheckResult(false, "条形码错误，请跳过此条");
		}
		if(books.size() > 1) {
			return new StockPosCheckResult(false, "一码多书，请跳过此条");
		}
		// 记录本次扫描条码到session中
		List<Bookinfo> recordList = putBookIntoSession(books.get(0), session);
		// 查询条码匹配的库位
		DetachedCriteria criteria = DetachedCriteria.forClass(TempImportDetail.class);
		criteria.add(Restrictions.eq("isbn", isbn));
		criteria.add(Restrictions.eq("taskId", 18));
		criteria.add(Restrictions.eq("status", TempImportDetailStatus.MATCHED));
		List<TempImportDetail> list = this.doPurchaseService.findTempImportDetailByCritera(criteria);
		if(list == null || list.isEmpty()) {
			return new StockPosCheckResult(false, "没有找到条形码对应的库位，请跳过此条");
		}
		// 记录库位
		Set<String> stockPosLabel = new HashSet<String>();
		for (TempImportDetail detail : list) {
			stockPosLabel.add(detail.getAdditionalInfo());
		}
		// 库位和session中的库位合并
		Set<String> properStockPos = putPosIntoSession(stockPosLabel, session);
		StockPosCheckResult result = new StockPosCheckResult(true, "");
		result.setScannedBookList(recordList);
		result.setComparablePos(properStockPos);
		return result;
	}
	
	private Set<String> putPosIntoSession(Set<String> stockPosLabel, HttpSession session) {
		@SuppressWarnings("unchecked")
		Set<String> pos = (Set<String>) session.getAttribute(SESSION_KEY_POS);
		if(pos == null) {
			// 如果是第一次处理，将stockPosLabel直接放入缓存并返回
			pos = stockPosLabel;
			session.setAttribute(SESSION_KEY_POS, pos);
		} else {
			// 如果不是第一次处理，将stockPosLabel和缓存中的set取交集并返回
			pos.retainAll(stockPosLabel);
		}
		return pos;
	}

	private List<Bookinfo> putBookIntoSession(Bookinfo bookinfo, HttpSession session) {
		@SuppressWarnings("unchecked")
		List<Bookinfo> bookList = (List<Bookinfo>) session.getAttribute(SESSION_KEY_RECORDS);
		if(bookList == null) {
			bookList = new ArrayList<Bookinfo>();
			session.setAttribute(SESSION_KEY_RECORDS, bookList);
		}
		bookList.add(bookinfo);
		return bookList;
	}

	public void clearSession(HttpSession session) {
		session.setAttribute(SESSION_KEY_POS, null);
		session.setAttribute(SESSION_KEY_RECORDS, null);
	}
	
	public void setDoPurchaseService(DoPurchaseService doPurchaseService) {
		this.doPurchaseService = doPurchaseService;
	}
	
	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}
}
