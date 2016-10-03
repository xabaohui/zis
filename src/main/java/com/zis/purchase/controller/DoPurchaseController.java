package com.zis.purchase.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.service.BookService;
import com.zis.bookinfo.util.ConstantString;
import com.zis.common.util.Page;
import com.zis.common.util.PaginationQueryUtil;
import com.zis.common.util.ZisUtils;
import com.zis.purchase.biz.DoPurchaseService;

@Controller
@RequestMapping(value="/purchase")
public class DoPurchaseController {

	private static final Logger logger = Logger
			.getLogger(DoPurchaseController.class);
	@Autowired
	private DoPurchaseService doPurchaseService;
	@Autowired
	private BookService bookService;
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/batchUpdatePurchasePlanForPurchaseAmount")
	public String batchUpdatePurchasePlanForPurchaseAmount() {
		this.doPurchaseService.batchUpdatePurchasePlanForPurchaseAmount();
		return "success";
	}
	
	@RequestMapping(value="/doPurchase",method=RequestMethod.POST)
	public String doPurchase(String isbn) {
		// 如果指定了bookId则只处理这一条
		if (StringUtils.isNotBlank(isbn)) {
			DetachedCriteria dc = DetachedCriteria.forClass(Bookinfo.class);
			dc.add(Restrictions.eq("isbn", isbn));
			List<Bookinfo> list = this.bookService.findBookByCriteria(dc);
			doPurchaseService.addPurchasePlanForBatch(list);
			return "success";
		}
		// 考虑到服务器压力，分批处理采购计划
		final int batchSize = 1000;
		DetachedCriteria dc = DetachedCriteria.forClass(Bookinfo.class);
		Integer totalCount = PaginationQueryUtil.getTotalCount(dc);
		Page page = Page.createPage(1, batchSize, totalCount);
		for (int i = 0; i < page.getTotalPageCount(); i++) {
			page = Page.createPage(i + 1, batchSize, totalCount);
			logger.info("采购计划开始，from=" + page.getBeginIndex() + ", to="
					+ (page.getBeginIndex() + batchSize));
			try {
				@SuppressWarnings("unchecked")
				List<Bookinfo> list = PaginationQueryUtil.paginationQuery(dc,
						page);
				doPurchaseService.addPurchasePlanForBatch(list);
			} catch (Exception e) {
				logger.error("执行采购计划过程中出错" + e);
			}
		}
		return "success";
	}
	
	/**
	 * 清理在途库存
	 * 
	 * @return
	 */
	@RequestMapping(value="/clearOnwayStock")
	public String clearOnwayStock(String purchaseOperator) {
		this.doPurchaseService.deleteOnwayStock(purchaseOperator);
		return "success";
	}

	/**
	 * 批量添加图书到黑名单
	 * 
	 * @return
	 */
	@RequestMapping(value="/batchAddIntoBlackList")
	public String batchAddIntoBlackList(String keyword) {
		if (StringUtils.isBlank(keyword)) {
			return "success";
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(Bookinfo.class);
		criteria.add(Restrictions.like("bookName", "%" + keyword + "%"));
		criteria.add(Restrictions.lt("publishDate",
				ZisUtils.stringToDate("2016-01")));// FIXME 时间限定写死
		criteria.add(Restrictions.eq("bookStatus", ConstantString.USEFUL));
		criteria.setProjection(Projections.property("id"));
		List<Bookinfo> list = this.bookService.findBookByCriteria(criteria);
		for (Object obj : list) {
			Integer bookId = (Integer) obj;
			doPurchaseService.addBlackList(bookId);
		}
		return "success";
	}
}
