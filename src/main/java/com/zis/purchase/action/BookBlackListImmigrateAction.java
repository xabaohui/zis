package com.zis.purchase.action;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionSupport;
import com.zis.common.util.Page;
import com.zis.common.util.PaginationQueryUtil;
import com.zis.purchase.bean.Bookblacklist;
import com.zis.purchase.bean.Bookwhitelist;
import com.zis.purchase.bean.PurchasePlan;
import com.zis.purchase.bean.PurchasePlanFlag;
import com.zis.purchase.bean.PurchasePlanStatus;
import com.zis.purchase.biz.DoPurchaseService;
import com.zis.purchase.dao.BookblacklistDao;
import com.zis.purchase.dao.BookwhitelistDao;

/**
 * 黑名单白名单数据迁移
 * 
 * @author yz
 * 
 */
public class BookBlackListImmigrateAction extends ActionSupport {

	private DoPurchaseService doPurchaseService;
	private BookblacklistDao bookblacklistDao;
	private BookwhitelistDao bookwhitelistDao;
	
	private static final Logger logger = Logger.getLogger(BookBlackListImmigrateAction.class);

	public String immigrate() {
		// 考虑到服务器压力，分批处理采购计划
		final int batchSize = 1000;
		DetachedCriteria dc = DetachedCriteria.forClass(PurchasePlan.class);
		dc.add(Restrictions.eq("status", PurchasePlanStatus.NORMAL));
		Integer totalCount = PaginationQueryUtil.getTotalCount(dc);
		Page page = Page.createPage(1, batchSize, totalCount);
		for (int i = 0; i < page.getTotalPageCount(); i++) {
			page = Page.createPage(i + 1, batchSize, totalCount);
			logger.info("采购计划开始，from=" + page.getBeginIndex() + ", to="
					+ (page.getBeginIndex() + batchSize));
			try {
				@SuppressWarnings("unchecked")
				List<PurchasePlan> list = PaginationQueryUtil.paginationQuery(dc,
						page);
				processBatch(list);
			} catch (Exception e) {
				logger.error("执行采购计划过程中出错" + e);
			}
		}
		return SUCCESS;
	}

	private void processBatch(List<PurchasePlan> list) {
		for (PurchasePlan plan : list) {
			String flag = getPlanStatus(plan);
			if(PurchasePlanFlag.BLACK.equals(flag)) {
				this.doPurchaseService.addBlackList(plan.getBookId());
			} else if(PurchasePlanFlag.WHITE.equals(flag)) {
				this.doPurchaseService.addWhiteList(plan.getBookId());
			} else {
				this.doPurchaseService.deleteBlackOrWhiteList(plan.getBookId());
			}
			
		}
	}

	private String getPlanStatus(PurchasePlan plan) {
		Integer bookId = plan.getBookId();
		// 黑名单
		Bookblacklist example = new Bookblacklist();
		example.setBookId(bookId);
		example.setStatus("valid");
		List<Bookblacklist> black = this.bookblacklistDao.findByExample(example);
		if(black != null && !black.isEmpty()) {
			return PurchasePlanFlag.BLACK;
		}
		// 白名单
		Bookwhitelist ex2 = new Bookwhitelist();
		ex2.setBookId(bookId);
		ex2.setStatus("valid");
		List<Bookwhitelist> white = this.bookwhitelistDao.findByExample(ex2);
		if(white != null && !white.isEmpty()) {
			return PurchasePlanFlag.WHITE;
		}
		return PurchasePlanFlag.NORMAL;
	}

	public void setDoPurchaseService(DoPurchaseService doPurchaseService) {
		this.doPurchaseService = doPurchaseService;
	}

	public void setBookblacklistDao(BookblacklistDao bookblacklistDao) {
		this.bookblacklistDao = bookblacklistDao;
	}

	public void setBookwhitelistDao(BookwhitelistDao bookwhitelistDao) {
		this.bookwhitelistDao = bookwhitelistDao;
	}
}
