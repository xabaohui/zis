package com.zis.purchase.biz;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.bean.BookinfoStatus;
import com.zis.bookinfo.service.BookService;
import com.zis.bookinfo.util.ConstantString;
import com.zis.common.cache.SysVarCache;
import com.zis.common.cache.SysVarConstant;
import com.zis.common.util.AlterEditionPurchaseStrategyEnum;
import com.zis.common.util.ZisUtils;
import com.zis.purchase.bean.Bookblacklist;
import com.zis.purchase.bean.Bookwhitelist;
import com.zis.purchase.bean.PurchaseDetail;
import com.zis.purchase.bean.PurchaseDetailStatus;
import com.zis.purchase.bean.PurchasePlan;
import com.zis.purchase.bean.PurchasePlanStatus;
import com.zis.purchase.calcMode.CalcModeFactory;
import com.zis.purchase.calcMode.CalculateModeInterface;
import com.zis.purchase.dao.BookblacklistDao;
import com.zis.purchase.dao.BookwhitelistDao;
import com.zis.purchase.dao.PurchaseDetailDao;
import com.zis.purchase.dao.PurchasePlanDao;

/**
 * 采购核心业务逻辑
 * 
 * @author yz
 * 
 */
public class PurchaseBO {

	private SysVarCache sysVarCache;
	private BookblacklistDao bookblacklistDao;
	private BookwhitelistDao bookwhitelistDao;
	private PurchaseDetailDao purchaseDetailDao;
	private PurchasePlanDao purchasePlanDao;

	private BookService bookService;
	private ThreadPoolTaskExecutor taskExecutor;

	private static final Logger logger = Logger.getLogger(PurchaseBO.class);

	/**
	 * 批量添加采购计划
	 * 
	 * @param bookList
	 */
	public void addPurchasePlanForBatch(final List<Bookinfo> bookList) {
		Thread task = new Thread(new Runnable() {

			public void run() {
				if (bookList == null || bookList.isEmpty()) {
					return;
				}
				// Date now = new Date();
				for (Bookinfo book : bookList) {
					try {
						addPurchasePlan(book);
					} catch (Exception e) {
						logger.error("采购计划初始化失败, bookId=" + book.getId(), e);
					}
				}
				// int firstBookId = bookList.get(0).getId();
				// int lastBookId = bookList.get(bookList.size() - 1).getId();
				// int clearCount = updatePurchasePlanClearRequireAmount(now,
				// firstBookId, lastBookId);
				// logger.info("采购计划部分更新完成，清空 " + clearCount + " 条无用记录");
			}
		});
		// 添加任务，如果遇到新的任务被拒绝，则稍后重试
		while (true) {
			try {
				taskExecutor.execute(task);
				break;
			} catch (TaskRejectedException e) {
				logger.info("new task reject, wait a moment...");
				try {
					Thread.sleep(300000);
				} catch (InterruptedException e1) {
				}
			}
		}
	}

	/**
	 * 添加/更新采购计划
	 */
	public void addPurchasePlan(Bookinfo bi) {
		if (bi == null) {
			return;
		}
		// 判断图书状态
		// 1. 待审核记录不录入采购计划
		if(BookinfoStatus.WAITCHECK.equals(bi.getBookStatus())) {
			return;
		}
		// 2. 废弃记录不处理/或作废已存在的采购计划
		else if(BookinfoStatus.DISCARD.equals(bi.getBookStatus())) {
			dealPurchasePlanForUseless(bi);
			return;
		}
		// 3. 状态为“正式”的记录，新增/更新采购计划
		else if(BookinfoStatus.NORMAL.equals(bi.getBookStatus())) {
			dealPurchasePlanForNormal(bi);
		} else {
			throw new RuntimeException("图书状态不合法，bookId="+bi.getId()+"bookStatus=" + bi.getBookStatus());
		}
	}
	
	// 状态为“正式”的记录，新增/更新采购计划
	private void dealPurchasePlanForNormal(Bookinfo bi) {
		// 计算需求量
		Integer requireAmount = calculateRequireAmount(bi);
		// 如果没有图书对应的采购计划，则新增
		PurchasePlan plan = this.findPurchasePlanByBookId(bi.getId());
		if (plan == null) {
			plan = new PurchasePlan();
			BeanUtils.copyProperties(bi, plan);
			plan.setBookId(bi.getId());
			plan.setRequireAmount(requireAmount);
			plan.setManualDecision(0);
			plan.setStockAmount(0);
			plan.setPurchasedAmount(0);
			plan.setId(null);
			plan.setStatus(PurchasePlanStatus.NORMAL);
			plan.setGmtCreate(ZisUtils.getTS());
			plan.setGmtModify(ZisUtils.getTS());
			plan.setVersion(0);
			this.purchasePlanDao.save(plan);
			logger.info("add new purchasePlan, bookId=" + bi.getId());
		}
		// 如果存在采购计划，则仅更新需求量
		else {
			plan.setBookName(bi.getBookName());
			plan.setBookAuthor(bi.getBookAuthor());
			plan.setBookEdition(bi.getBookEdition());
			plan.setBookPublisher(bi.getBookPublisher());
			plan.setRequireAmount(requireAmount);
			// if (isBookInBlackList(bi.getId())) {
			// plan.setManualDecision(0); // 黑名单中的记录，人工定义需求量也设置为0(补偿处理之前的老数据)
			// }
			plan.setGmtModify(ZisUtils.getTS());
			plan.setVersion(plan.getVersion() + 1);
			purchasePlanDao.update(plan);
			logger.info("update exist purchasePlan, bookId=" + bi.getId());
		}
	}

	// 废弃的记录，不处理/或作废已存在的采购计划
	private void dealPurchasePlanForUseless(Bookinfo bi) {
		PurchasePlan plan = this.findPurchasePlanByBookId(bi.getId());
		if(plan == null || PurchasePlanStatus.USELESS.equals(plan.getStatus())) {
			return;
		}
		plan.setStatus(PurchasePlanStatus.USELESS);
		plan.setGmtModify(ZisUtils.getTS());
		plan.setVersion(plan.getVersion() + 1);
		logger.info("make purchasePLan useless, for bookId=" + bi.getId());
		this.purchasePlanDao.update(plan);
	}

	/**
	 * 计算采购需求量(不会排除库存和已采购未入库的记录)
	 * 
	 * @param bi
	 * @return
	 */
	private Integer calculateRequireAmount(Bookinfo bi) {
		if (StringUtils.isNotBlank(isUsefulBook(bi))) {
			return 0;
		}
		// 计算采购计划量：根据系统预设的策略
		String keyName = SysVarConstant.PURCHASE_STRATEGY_AMT_CALCULATE
				.getKeyName();
		Integer strategy = sysVarCache.getSystemVar(keyName);
		CalculateModeInterface mode = CalcModeFactory.getInstance(strategy);
		if (mode == null) {
			throw new RuntimeException("没有此种类型的采购计划量计算策略，stragegy=" + strategy);
		}
		return mode.doCalculate(bi.getId());
	}

	/**
	 * 判断图书是否有用
	 * 
	 * @param bookId
	 * @return 不可用原因，返回空表示可用
	 */
	private String isUsefulBook(Bookinfo bi) {
		if (bi == null) {
			return "参数图书为空";
		}
		// 状态不是“正式”，false
		if (!bi.getBookStatus().equals(ConstantString.USEFUL)) {
			return "图书" + bi.getId() + "状态不是“正式”";
		}
		// 黑名单，false
		if (isBookInBlackList(bi.getId())) {
			return "图书" + bi.getId() + "已加入黑名单";
		}
		// 如果是最新版，true
		if (bi.getIsNewEdition()) {
			return StringUtils.EMPTY;
		}
		// 如果不是最新版，则根据过期策略来定
		Integer strategy = sysVarCache
				.getSystemVar(SysVarConstant.PURCHASE_STRATEGY_ALTER_EDITION_ALLOW
						.getKeyName());
		AlterEditionPurchaseStrategyEnum st = AlterEditionPurchaseStrategyEnum
				.getEnumByValue(strategy);
		switch (st) {
		case GET_ALL:// 全要
			return StringUtils.EMPTY;
		case GET_NONE:// 全不要
			return "系统不允许使用过期图书" + bi.getId();
		case GET_WHITE_LIST:// 只要白名单里的
			return isBookInWhiteList(bi.getId()) ? StringUtils.EMPTY
					: "系统不允许使用此图书" + bi.getId();
		default:
			return "系统错误";
		}
	}

	/**
	 * 检查图书是否在黑名单里
	 * 
	 * @param bookId
	 * @return
	 */
	private boolean isBookInBlackList(int bookId) {
		Bookblacklist example = new Bookblacklist();
		example.setBookId(bookId);
		example.setStatus(ConstantString.STATUS_VALID);
		@SuppressWarnings("unchecked")
		List<Bookblacklist> blackList = bookblacklistDao.findByExample(example);
		return blackList != null && !blackList.isEmpty();
	}

	/**
	 * 检查图书是否在白名单里
	 * 
	 * @param bookId
	 * @return
	 */
	private boolean isBookInWhiteList(int bookId) {
		Bookwhitelist example = new Bookwhitelist();
		example.setBookId(bookId);
		example.setStatus(ConstantString.STATUS_VALID);
		@SuppressWarnings("unchecked")
		List<Bookwhitelist> whiteList = bookwhitelistDao.findByExample(example);
		return whiteList != null && !whiteList.isEmpty();
	}

	// /**
	// * 清空更新时间在某个时间点之前的需求量
	// *
	// * @param now
	// */
	// private int updatePurchasePlanClearRequireAmount(Date now, int
	// bookIdStart,
	// int bookIdEnd) {
	// if (now == null) {
	// return 0;
	// }
	// // 查询出某个时间点之前修改的记录
	// DetachedCriteria dc = DetachedCriteria.forClass(PurchasePlan.class);
	// dc.add(Restrictions.lt("gmtModify", now));
	// dc.add(Restrictions.between("bookId", bookIdStart, bookIdEnd));
	// List<PurchasePlan> planList = this.purchasePlanDao.findbyCriteria(dc);
	// for (PurchasePlan plan : planList) {
	// plan.setRequireAmount(0);
	// // 如果图书不存在或者已删除，则标记采购计划无效
	// Bookinfo book = this.bookService.findNormalBookById(plan
	// .getBookId());
	// if (book == null
	// || BookinfoStatus.DISCARD.equals(book.getBookStatus())) {
	// plan.setStatus(PurchasePlanStatus.USELESS);
	// }
	// plan.setGmtModify(ZisUtils.getTS());
	// plan.setVersion(plan.getVersion() + 1);
	// this.purchasePlanDao.update(plan);
	// }
	// return planList.size();
	// }

	/**
	 * 是否允许手动设置采购量
	 * 
	 * @return
	 */
	public boolean isAllowManualDecisionForPurchasePlan() {
		return sysVarCache
				.getSystemVar(SysVarConstant.PURCHASE_STRATEGY_MANUAL_FIRST
						.getKeyName()) > 0;
	}

	/**
	 * 计算仍需采购数量
	 * 
	 * @param plan
	 * @return
	 */
	public Integer calculateStillRequireAmount(PurchasePlan plan) {
		Integer requireAmount = plan.getRequireAmount();
		boolean manualFirst = sysVarCache
				.getSystemVar(SysVarConstant.PURCHASE_STRATEGY_MANUAL_FIRST
						.getKeyName()) > 0;
		if (manualFirst && plan.getManualDecision() > 0) {
			requireAmount = plan.getManualDecision();
		}
		Integer still = requireAmount - plan.getStockAmount()
				- plan.getPurchasedAmount();
		return still > 0 ? still : 0;
	}

	/**
	 * 录入黑名单
	 * 
	 * @param bookId
	 */
	public void addBlackList(Bookinfo bookinfo) {
		if (bookinfo == null) {
			return;
		}
		// 如果黑名单已有该记录，则不再重复添加
		if (!this.isBookInBlackList(bookinfo.getId())) {
			Bookblacklist bl = new Bookblacklist();
			bl.setBookId(bookinfo.getId());
			bl.setIsbn(bookinfo.getIsbn());
			bl.setBookAuthor(bookinfo.getBookAuthor());
			bl.setBookEdition(bookinfo.getBookEdition());
			bl.setBookName(bookinfo.getBookName());
			bl.setBookPublisher(bookinfo.getBookPublisher());
			bl.setGmtCreate(ZisUtils.getTS());
			bl.setGmtModify(ZisUtils.getTS());
			bl.setStatus("valid");
			bl.setVersion(0);
			bookblacklistDao.save(bl);
			logger.info("add bookInfo to blackList, bookId=" + bookinfo.getId());
		}
		// 更新采购计划，需求量（系统和人工）都设置成0
		PurchasePlan plan = this.findPurchasePlanByBookId(bookinfo.getId());
		if (plan != null) {
			plan.setRequireAmount(0);
			plan.setManualDecision(0);
			plan.setGmtModify(ZisUtils.getTS());
			plan.setVersion(plan.getVersion() + 1);
			purchasePlanDao.update(plan);
			logger.info("update purchasePlan, for clear requireAmount, bookId="
					+ bookinfo.getId());
		}
	}

	/**
	 * 录入白名单
	 * 
	 * @param bookId
	 */
	public void addWhiteList(Bookinfo bookinfo) {
		if (bookinfo == null) {
			return;
		}
		// 如果白名单已有该记录，直接返回
		if (isBookInWhiteList(bookinfo.getId())) {
			return;
		}
		Bookwhitelist bl = new Bookwhitelist();
		bl.setBookId(bookinfo.getId());
		bl.setIsbn(bookinfo.getIsbn());
		bl.setBookAuthor(bookinfo.getBookAuthor());
		bl.setBookEdition(bookinfo.getBookEdition());
		bl.setBookName(bookinfo.getBookName());
		bl.setBookPublisher(bookinfo.getBookPublisher());
		bl.setGmtCreate(ZisUtils.getTS());
		bl.setGmtModify(ZisUtils.getTS());
		bl.setStatus("valid");
		bl.setVersion(0);
		bookwhitelistDao.save(bl);
		logger.info("add bookInfo to whiteList, bookId=" + bookinfo.getId());
	}

	/**
	 * 删除黑名单或白名单
	 * 
	 * @param id
	 * @param flag
	 */
	public void deleteBlackOrWhiteList(Integer id, String flag) {
		if ("white".equals(flag)) {
			Bookwhitelist wl = bookwhitelistDao.findById(id);
			wl.setStatus(ConstantString.STATUS_INVALID);
			wl.setGmtModify(ZisUtils.getTS());
			wl.setVersion(wl.getVersion() + 1);
			bookwhitelistDao.update(wl);
		} else {
			Bookblacklist bl = bookblacklistDao.findById(id);
			bl.setStatus(ConstantString.STATUS_INVALID);
			bl.setGmtModify(ZisUtils.getTS());
			bl.setVersion(bl.getVersion() + 1);
			bookblacklistDao.update(bl);
		}
	}

	/**
	 * 更新采购计划中的图书库存
	 * 
	 * @param bookId
	 * @param amount
	 * @return
	 */
	public String updateBookStock(Integer bookId, Integer amount) {
		PurchasePlan plan = this.findPurchasePlanByBookId(bookId);
		if (plan == null) {
			return "没有找可用的采购计划,bookId=" + bookId;
		}
		plan.setStockAmount(amount);
		plan.setGmtModify(ZisUtils.getTS());
		plan.setVersion(plan.getVersion() + 1);
		this.purchasePlanDao.update(plan);
		logger.info("update stock, bookId=" + bookId + ",stockBalance="
				+ amount);
		return StringUtils.EMPTY;
	}

	/**
	 * 针对特定图书设置需求量
	 * 
	 * @param bookId
	 *            图书ID
	 * @param amount
	 *            数量
	 * @return
	 */
	public String addManualDecision(Integer bookId, Integer amount) {
		if (bookId == null) {
			return "参数非法，bookId不能为空";
		}
		if (amount == null || amount <= 0) {
			return "参数非法，数量必须大于0";
		}
		// 检查系统是否打开人工定量开关
		if (!isAllowManualDecisionForPurchasePlan()) {
			return "不允许手动设置采购量";
		}
		// 检查图书是否有用
		String uselessReason = isUsefulBook(this.bookService
				.findBookById(bookId));
		if (StringUtils.isNotBlank(uselessReason)) {
			return uselessReason;
		}
		// 检查采购计划是否存在
		PurchasePlan plan = this.findPurchasePlanByBookId(bookId);
		if (plan == null) {
			return "没有可用的采购计划,bookId=" + bookId;
		}
		// 设置需求量
		plan.setManualDecision(amount);
		plan.setGmtModify(ZisUtils.getTS());
		plan.setVersion(plan.getVersion() + 1);
		this.purchasePlanDao.update(plan);
		logger.info("update PurchasePlan by manual, bookId=" + bookId
				+ ",manualDecision=" + amount);
		return StringUtils.EMPTY;
	}

	/**
	 * 添加采购明细，如果不满足采购条件，直接返回失败原因
	 * 
	 * @param bookId
	 * @param purchasedAmount
	 * @param operator
	 * @param position
	 * @param memo
	 * @return 返回失败原因，如果添加成功，返回空字符串
	 */
	public String addPurchaseDetail(int bookId, int purchasedAmount,
			String operator, String position, String memo) {
		if (purchasedAmount <= 0) {
			throw new IllegalArgumentException(
					"illegal purchasedAmount, for input:" + purchasedAmount);
		}
		// 查询采购计划
		PurchasePlan plan = this.findPurchasePlanByBookId(bookId);
		if (plan == null) {
			return "没有可用的采购计划，bookId=" + bookId;
		}
		// 判断本次采购量是否超出计划
		Integer count = calculateStillRequireAmount(plan);
		if (purchasedAmount > count) {
			return "已采购量超出需求量:" + (purchasedAmount - count);
		}
		// 更新采购计划
		plan.setPurchasedAmount(purchasedAmount + plan.getPurchasedAmount());
		plan.setGmtModify(ZisUtils.getTS());
		plan.setVersion(plan.getVersion() + 1);
		purchasePlanDao.update(plan);
		// 新增采购明细
		PurchaseDetail detail = buildPurchaseDetail(bookId, purchasedAmount,
				operator, position, memo);
		purchaseDetailDao.save(detail);
		logger.info("successfully add purchaseDetail, bookId=" + bookId
				+ ",purchasedAmount=" + purchasedAmount);
		return StringUtils.EMPTY;
	}

	// 建立purchaseDetail对象
	private PurchaseDetail buildPurchaseDetail(int bookId, int purchasedAmount,
			String operator, String position, String memo) {
		PurchaseDetail po = new PurchaseDetail();
		po.setBookId(bookId);
		po.setMemo(memo);
		po.setStatus(PurchaseDetailStatus.PURCHASED);
		po.setOperator(operator);
		po.setPosition(position);
		po.setPurchasedAmount(purchasedAmount);
		po.setInwarehouseAmount(0);
		po.setGmtCreate(ZisUtils.getTS());
		po.setGmtModify(ZisUtils.getTS());
		po.setVersion(0);
		return po;
	}

	/**
	 * 查询可用的采购计划
	 * 
	 * @param bookId
	 * @return
	 */
	private PurchasePlan findPurchasePlanByBookId(int bookId) {
		PurchasePlan example = new PurchasePlan();
		example.setStatus(PurchasePlanStatus.NORMAL);
		example.setBookId(bookId);
		return this.purchasePlanDao.findByBookId(bookId);
	}

	/**
	 * 查询采购计划
	 * 
	 * @param dc
	 * @return
	 */
	public List<PurchasePlan> findPurchasePlanByCriteria(DetachedCriteria dc) {
		return purchasePlanDao.findbyCriteria(dc);
	}

	/**
	 * （临时方法）批量更新采购计划中的在途库存量
	 */
	@Deprecated
	public void batchUpdatePurchasePlanForPurchaseAmount() {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(PurchasePlan.class);
		criteria.add(Restrictions.eq("status", PurchasePlanStatus.NORMAL));
		criteria.add(Restrictions.gt("purchasedAmount", 0));
		List<PurchasePlan> list = this.purchasePlanDao.findbyCriteria(criteria);
		for (PurchasePlan plan : list) {
			plan.setPurchasedAmount(sumPurchaseAmount(plan.getBookId()));
			this.purchasePlanDao.update(plan);
		}
	}

	private int sumPurchaseAmount(int bookId) {
		DetachedCriteria detailDc = DetachedCriteria
				.forClass(PurchaseDetail.class);
		detailDc.add(Restrictions.eq("status", PurchaseDetailStatus.PURCHASED));
		detailDc.add(Restrictions.eq("bookId", bookId));
		List<PurchaseDetail> details = this.purchaseDetailDao
				.findByCriteria(detailDc);
		if (details == null || details.isEmpty()) {
			return 0;
		}
		int sum = 0;
		for (PurchaseDetail detail : details) {
			sum += (detail.getPurchasedAmount() - detail.getInwarehouseAmount());
		}
		return sum;
	}

	/**
	 * 清理指定采购员的在途库存
	 * 
	 * @param purchaseOperator
	 */
	public void deleteOnwayStock(String purchaseOperator) {
		if (StringUtils.isBlank(purchaseOperator)) {
			return;
		}
		DetachedCriteria criteria = DetachedCriteria
				.forClass(PurchaseDetail.class);
		criteria.add(Restrictions.eq("operator", purchaseOperator));
		criteria.add(Restrictions.eq("status", PurchaseDetailStatus.PURCHASED));
		List<PurchaseDetail> details = this.purchaseDetailDao
				.findByCriteria(criteria);
		if (details == null || details.isEmpty()) {
			return;
		}
		for (PurchaseDetail detail : details) {
			// 更新采购明细状态
			detail.setStatus(PurchaseDetailStatus.USELESS);
			detail.setGmtModify(ZisUtils.getTS());
			detail.setVersion(detail.getVersion() + 1);
			this.purchaseDetailDao.save(detail);
			// 更新采购计划表
			PurchasePlan plan = findPurchasePlanByBookId(detail.getBookId());
			plan.setPurchasedAmount(plan.getPurchasedAmount()
					- (detail.getPurchasedAmount() - detail
							.getInwarehouseAmount()));
			plan.setGmtModify(ZisUtils.getTS());
			plan.setVersion(plan.getVersion() + 1);
			this.purchasePlanDao.update(plan);
		}
	}

	public void setSysVarCache(SysVarCache sysVarCache) {
		this.sysVarCache = sysVarCache;
	}

	public void setBookblacklistDao(BookblacklistDao bookblacklistDao) {
		this.bookblacklistDao = bookblacklistDao;
	}

	public void setBookwhitelistDao(BookwhitelistDao bookwhitelistDao) {
		this.bookwhitelistDao = bookwhitelistDao;
	}

	public void setPurchaseDetailDao(PurchaseDetailDao purchaseDetailDao) {
		this.purchaseDetailDao = purchaseDetailDao;
	}

	public void setPurchasePlanDao(PurchasePlanDao purchasePlanDao) {
		this.purchasePlanDao = purchasePlanDao;
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}

	public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
}
