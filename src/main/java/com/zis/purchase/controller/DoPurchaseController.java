package com.zis.purchase.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.service.BookService;
import com.zis.common.mvc.ext.QueryUtil;
import com.zis.common.util.ZisUtils;
import com.zis.purchase.biz.DoPurchaseService;

@Controller
@RequestMapping(value = "/purchase")
public class DoPurchaseController {

	private static final Logger logger = Logger.getLogger(DoPurchaseController.class);
	@Autowired
	private DoPurchaseService doPurchaseService;
	@Autowired
	private BookService bookService;

	@SuppressWarnings("deprecation")
	@RequiresPermissions(value = {"purchase:batchUpdatePurchasePlanForPurchaseAmount"})
	@RequestMapping(value = "/batchUpdatePurchasePlanForPurchaseAmount")
	public String batchUpdatePurchasePlanForPurchaseAmount() {
		this.doPurchaseService.batchUpdatePurchasePlanForPurchaseAmount();
		return "success";
	}
	
	@RequiresPermissions(value = "purchase:doPurchase")
	@RequestMapping(value = "/doPurchase", method = RequestMethod.POST)
	public String doPurchase(String isbn) {
		// 如果指定了bookId则只处理这一条
		if (StringUtils.isNotBlank(isbn)) {
			QueryUtil<Bookinfo> query = new QueryUtil<Bookinfo>();
			query.eq("isbn", isbn);
			Specification<Bookinfo> spec = query.getSpecification();
			// DetachedCriteria dc = DetachedCriteria.forClass(Bookinfo.class);
			// dc.add(Restrictions.eq("isbn", isbn));
			List<Bookinfo> list = this.bookService.findBySpecificationAll(spec);
			doPurchaseService.addPurchasePlanForBatch(list);
			return "success";
		}
		// 考虑到服务器压力，分批处理采购计划
		final int batchSize = 1000;
		Pageable page = new PageRequest(0, batchSize);
		Page<Bookinfo> plist = null;
		Integer beginIndex = page.getPageNumber() * batchSize;
		do {
			logger.info("采购计划开始，from=" + beginIndex + ", to=" + (beginIndex + batchSize));
			try {
			plist = this.bookService.findAll(page);
			doPurchaseService.addPurchasePlanForBatch(plist.getContent());
			page = plist.nextPageable();
			} catch (Exception e) {
				logger.error("执行采购计划过程中出错" + e);
			}
		} while (plist.hasNext());
		return "success";
		// DetachedCriteria dc = DetachedCriteria.forClass(Bookinfo.class);
		// Integer totalCount = PaginationQueryUtil.getTotalCount(dc);
		// Page page = Page.createPage(1, batchSize, totalCount);
//		for (int i = 0; i < page.getTotalPageCount(); i++) {
//			page = Page.createPage(i + 1, batchSize, totalCount);
//			try {
//				@SuppressWarnings("unchecked")
//				List<Bookinfo> list = PaginationQueryUtil.paginationQuery(dc, page);
//			} catch (Exception e) {
//				logger.error("执行采购计划过程中出错" + e);
//			}
//		}
//		return "success";
	}

	/**
	 * 清理在途库存
	 * 
	 * @return
	 */
	@RequiresPermissions(value = "purchase:clearOnwayStock")
	@RequestMapping(value = "/clearOnwayStock")
	public String clearOnwayStock(String purchaseOperator) {
		this.doPurchaseService.deleteOnwayStock(purchaseOperator);
		return "success";
	}

	/**
	 * 批量添加图书到黑名单
	 * 
	 * @return
	 */
	@RequiresPermissions(value = "purchase:batchAddIntoBlackList")
	@RequestMapping(value = "/batchAddIntoBlackList")
	public String batchAddIntoBlackList(String keyword) {
		if (StringUtils.isBlank(keyword)) {
			return "success";
		}
		// DetachedCriteria criteria =
		// DetachedCriteria.forClass(Bookinfo.class);
		// criteria.add(Restrictions.like("bookName", "%" + keyword + "%"));
		// criteria.add(Restrictions.lt("publishDate",
		// ZisUtils.stringToDate("2016-01")));// FIXME 时间限定写死
		// criteria.add(Restrictions.eq("bookStatus", ConstantString.USEFUL));
		// criteria.setProjection(Projections.property("id"));
		QueryUtil<Bookinfo> query = new QueryUtil<Bookinfo>();
		query.like("bookName", "%" + keyword + "%");
		query.lt("publishDate", ZisUtils.stringToDate("2016-01"));
		Specification<Bookinfo> spec = query.getSpecification();
		List<Bookinfo> list = this.bookService.findBySpecificationAll(spec);
		for (Bookinfo obj : list) {
			Integer bookId = obj.getId();
			doPurchaseService.addBlackList(bookId);
		}
		return "success";
	}
}
