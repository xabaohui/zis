package com.zis.purchase.biz;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.bean.BookinfoStatus;
import com.zis.bookinfo.service.BookService;
import com.zis.bookinfo.util.ConstantString;
import com.zis.common.cache.SysVarCache;
import com.zis.common.cache.SysVarConstant;
import com.zis.common.util.AlterEditionPurchaseStrategyEnum;
import com.zis.common.util.ZisUtils;
import com.zis.purchase.bean.PurchaseDetail;
import com.zis.purchase.bean.PurchaseDetailStatus;
import com.zis.purchase.bean.PurchasePlan;
import com.zis.purchase.bean.PurchasePlanFlag;
import com.zis.purchase.bean.PurchasePlanStatus;
import com.zis.purchase.calcMode.CalcModeFactory;
import com.zis.purchase.calcMode.CalculateModeInterface;
import com.zis.purchase.repository.PurchaseDetailDao;
import com.zis.purchase.repository.PurchasePlanDao;

/**
 * 采购核心业务逻辑
 * 
 * @author yz
 * 
 */
@Component
public class PurchaseBO {
	@Autowired
	private SysVarCache sysVarCache;
	@Autowired
	private PurchaseDetailDao purchaseDetailDao;
	@Autowired
	private PurchasePlanDao purchasePlanDao;

	@Autowired
	private BookService bookService;
	@Autowired
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
				for (Bookinfo book : bookList) {
					try {
						addPurchasePlan(book);
					} catch (Exception e) {
						logger.error("采购计划初始化失败, bookId=" + book.getId(), e);
					}
				}
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
		PurchasePlan plan = this.findPurchasePlanByBookId(bi.getId());
		Integer requireAmount = calculateRequireAmount(bi, plan);
		// 如果没有图书对应的采购计划，则新增
		if (plan == null) {
			plan = new PurchasePlan();
			BeanUtils.copyProperties(bi, plan);
			plan.setBookId(bi.getId());
			plan.setRequireAmount(requireAmount);
			plan.setManualDecision(0);
			plan.setStockAmount(0);
			plan.setPurchasedAmount(0);
			plan.setStatus(PurchasePlanStatus.NORMAL);
			plan.setGmtCreate(ZisUtils.getTS());
			plan.setGmtModify(ZisUtils.getTS());
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
			purchasePlanDao.save(plan);
			logger.info("update exist purchasePlan, bookId=" + bi.getId());
		}
	}

	// 废弃的记录，不处理/或作废已存在的采购计划
	private void dealPurchasePlanForUseless(Bookinfo bi) {
		this.purchasePlanDao.updateToUselessByBookId(bi.getId());
		logger.info("make purchasePLan useless, for bookId=" + bi.getId());
	}

	/**
	 * 计算采购需求量(不会排除库存和已采购未入库的记录)
	 * 
	 * @param bi
	 * @return
	 */
	private Integer calculateRequireAmount(Bookinfo bi, PurchasePlan plan) {
		// 定性：参考黑名单、白名单、过期等多方面因素，判断图书是否需要采购
		if (StringUtils.isNotBlank(isUsefulBook(bi, plan))) {
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
	 * @param bi 图书信息
	 * @param plan 采购计划
	 * @return 不可用原因，返回空表示可用
	 */
	private String isUsefulBook(Bookinfo bi, PurchasePlan plan) {
		if (bi == null) {
			return "参数图书为空";
		}
		// 状态不是“正式”，false
		if (!bi.getBookStatus().equals(ConstantString.USEFUL)) {
			return "图书" + bi.getId() + "状态不是“正式”";
		}
		// 黑名单，false
		if (isBookInBlackList(plan)) {
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
			return isBookInWhiteList(plan) ? StringUtils.EMPTY
					: "系统不允许使用此图书" + bi.getId();
		default:
			return "系统错误";
		}
	}

	/**
	 * 检查图书是否在黑名单里
	 * 
	 * @return
	 */
	private boolean isBookInBlackList(PurchasePlan plan) {
		return plan != null && PurchasePlanFlag.BLACK.equals(plan.getFlag());
	}

	/**
	 * 检查图书是否在白名单里
	 * 
	 * @return
	 */
	private boolean isBookInWhiteList(PurchasePlan plan) {
		return plan != null && PurchasePlanFlag.WHITE.equals(plan.getFlag());
	}

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
	public void addBlackList(int bookId) {
		PurchasePlan plan = this.findPurchasePlanByBookId(bookId);
		if(plan == null) {
			throw new RuntimeException("采购计划不存在，bookId=" + bookId);
		}
		// 如果已经在黑名单里，直接返回
		if(isBookInBlackList(plan)) {
			return;
		}
		// 设置为黑名单记录，清空计划采购量(系统和人工)
		plan.setFlag(PurchasePlanFlag.BLACK);
		plan.setRequireAmount(0);
		plan.setManualDecision(0);
		plan.setGmtModify(ZisUtils.getTS());
		purchasePlanDao.save(plan);
		logger.info("update purchasePlan, for clear requireAmount, bookId=" + bookId);
	}

	/**
	 * 录入白名单
	 * 
	 * @param bookId
	 */
	public void addWhiteList(int bookId) {
		PurchasePlan plan = this.findPurchasePlanByBookId(bookId);
		if(plan == null) {
			throw new RuntimeException("采购计划不存在，bookId=" + bookId);
		}
		// 如果已经在黑名单里，直接返回
		if(isBookInWhiteList(plan)) {
			return;
		}
		// 设置为白名单记录，重新计算计划采购量(系统)
		Bookinfo bi = this.bookService.findBookById(bookId);
		if(bi == null) {
			throw new RuntimeException("图书信息不存在，bookId=" + bookId);
		}
		plan.setFlag(PurchasePlanFlag.WHITE);
		plan.setRequireAmount(calculateRequireAmount(bi, plan));
		plan.setGmtModify(ZisUtils.getTS());
		purchasePlanDao.save(plan);
		logger.info("add bookInfo to whiteList, bookId=" + bookId);
	}
	
	

	/**
	 * 删除黑名单或白名单
	 * 
	 * @param id
	 * @param flag
	 */
	public void deleteBlackOrWhiteList(Integer bookId) {
		PurchasePlan plan = this.findPurchasePlanByBookId(bookId);
		if(plan == null) {
			throw new RuntimeException("采购计划不存在，bookId=" + bookId);
		}
		// 没有黑白名单标记的记录不处理
		if(PurchasePlanFlag.NORMAL.equals(plan.getFlag())) {
			return;
		}
		// 取消标记，重新计算计划采购量
		Bookinfo bi = this.bookService.findBookById(bookId);
		if(bi == null) {
			throw new RuntimeException("图书信息不存在，bookId=" + bookId);
		}
		plan.setFlag(PurchasePlanFlag.NORMAL);
		plan.setRequireAmount(calculateRequireAmount(bi, plan));
		plan.setGmtModify(ZisUtils.getTS());
		purchasePlanDao.save(plan);
		logger.info("cancel while or black flag for purchasePlan, bookId=" + bookId);
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
		this.purchasePlanDao.save(plan);
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
		PurchasePlan plan = this.findPurchasePlanByBookId(bookId);
		String uselessReason = isUsefulBook(this.bookService
				.findBookById(bookId), plan);
		if (StringUtils.isNotBlank(uselessReason)) {
			return uselessReason;
		}
		// 检查采购计划是否存在
		if (plan == null) {
			return "没有可用的采购计划,bookId=" + bookId;
		}
		// 设置需求量
		plan.setManualDecision(amount);
		plan.setGmtModify(ZisUtils.getTS());
		this.purchasePlanDao.save(plan);
		logger.info("update PurchasePlan by manual, bookId=" + bookId
				+ ",manualDecision=" + amount);
		return StringUtils.EMPTY;
	}
	
	/**
	 * 去除人工定量
	 * @param bookId
	 */
	public void removeManualDecision(Integer bookId) {
		PurchasePlan plan = this.findPurchasePlanByBookId(bookId);
		if(plan == null) {
			throw new RuntimeException("采购计划不存在，bookId=" + bookId);
		}
		plan.setManualDecision(0);
		plan.setGmtModify(ZisUtils.getTS());
		this.purchasePlanDao.save(plan);
		logger.info("remove manual decision successfully, bookId=" + bookId);
	}
	
	/**
	 * 重新计算计划采购量
	 * @param bookId
	 */
	public void recalculateRequireAmount(Integer bookId) {
		Bookinfo bi = this.bookService.findBookById(bookId);
		if(bi == null) {
			throw new RuntimeException("图书信息不存在，bookId=" + bookId);
		}
		PurchasePlan plan = this.findPurchasePlanByBookId(bookId);
		if(plan == null) {
			throw new RuntimeException("采购计划不存在，bookId=" + bookId);
		}
		int planAmt = this.calculateRequireAmount(bi, plan);
		plan.setRequireAmount(planAmt);
		plan.setGmtModify(ZisUtils.getTS());
		this.purchasePlanDao.save(plan);
		logger.info("recalculate purchasePlan successfully, bookId=" + bookId);
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
		purchasePlanDao.save(plan);
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
		return po;
	}

	/**
	 * 查询可用的采购计划
	 * 
	 * @param bookId
	 * @return
	 */
	public PurchasePlan findPurchasePlanByBookId(int bookId) {
		return this.purchasePlanDao.findByBookId(bookId);
	}

	/**
	 * （临时方法）批量更新采购计划中的在途库存量
	 */
	@Deprecated
	public void batchUpdatePurchasePlanForPurchaseAmount() {
		List<PurchasePlan> list = purchasePlanDao.findForRecalcOnwayStock();
		for (PurchasePlan plan : list) {
			plan.setPurchasedAmount(sumPurchaseAmount(plan.getBookId()));
			plan.setGmtModify(ZisUtils.getTS());
			this.purchasePlanDao.save(plan);
		}
	}

	private int sumPurchaseAmount(int bookId) {
		// DetachedCriteria detailDc = DetachedCriteria
		// .forClass(PurchaseDetail.class);
		// detailDc.add(Restrictions.eq("status",
		// PurchaseDetailStatus.PURCHASED));
		// detailDc.add(Restrictions.eq("bookId", bookId));
		// List<PurchaseDetail> details = this.purchaseDetailDao
		// .findByCriteria(detailDc);
		// if (details == null || details.isEmpty()) {
		// return 0;
		// }
		// int sum = 0;
		// for (PurchaseDetail detail : details) {
		// sum += (detail.getPurchasedAmount() - detail.getInwarehouseAmount());
		// }
		// return sum;
		// select sum(purchasedAmount - inwarehouseAmount) from PurchaseDetail where status = '' and bookId =
		return this.purchaseDetailDao.sumPurchasedAmount(bookId);
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
		List<PurchaseDetail> details = this.purchaseDetailDao.findByOperatorAndStatus(purchaseOperator, PurchaseDetailStatus.PURCHASED);
		if (details == null || details.isEmpty()) {
			return;
		}
		for (PurchaseDetail detail : details) {
			// 更新采购明细状态
			detail.setStatus(PurchaseDetailStatus.USELESS);
			detail.setGmtModify(ZisUtils.getTS());
			this.purchaseDetailDao.save(detail);
			// 更新采购计划表
			PurchasePlan plan = findPurchasePlanByBookId(detail.getBookId());
			plan.setPurchasedAmount(plan.getPurchasedAmount()
					- (detail.getPurchasedAmount() - detail
							.getInwarehouseAmount()));
			plan.setGmtModify(ZisUtils.getTS());
			this.purchasePlanDao.save(plan);
		}
	}
}
