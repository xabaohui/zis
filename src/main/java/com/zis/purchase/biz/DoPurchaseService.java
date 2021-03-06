package com.zis.purchase.biz;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.purchase.bean.Inwarehouse;
import com.zis.purchase.bean.InwarehouseBizType;
import com.zis.purchase.bean.InwarehouseDetail;
import com.zis.purchase.bean.InwarehousePosition;
import com.zis.purchase.bean.InwarehouseStatus;
import com.zis.purchase.bean.PurchaseDetail;
import com.zis.purchase.bean.PurchasePlan;
import com.zis.purchase.bean.TempImportDetail;
import com.zis.purchase.bean.TempImportTask;
import com.zis.purchase.bean.TempImportTaskBizTypeEnum;
import com.zis.purchase.dto.InwarehouseCreateDTO;
import com.zis.purchase.dto.InwarehouseCreateResult;
import com.zis.purchase.dto.InwarehouseDealtResult;
import com.zis.purchase.dto.InwarehouseView;
import com.zis.purchase.dto.StockDTO;
import com.zis.purchase.dto.TempImportDTO;
import com.zis.purchase.dto.TempImportDetailView;
import com.zis.purchase.repository.InwarehouseDao;
import com.zis.purchase.repository.InwarehouseDetailDao;
import com.zis.purchase.repository.InwarehousePositionDao;
import com.zis.purchase.repository.PurchaseDetailDao;
import com.zis.purchase.repository.PurchasePlanDao;
import com.zis.purchase.repository.TempImportDetailDao;

@Service
public class DoPurchaseService {

	@Autowired
	private InwarehouseDao inwarehouseDao;
	@Autowired
	private InwarehousePositionDao inwarehousePositionDao;
	@Autowired
	private InwarehouseDetailDao inwarehouseDetailDao;
	@Autowired
	private PurchasePlanDao purchasePlanDao;
	@Autowired
	private PurchaseBO purchaseBO;
	@Autowired
	private TempImportBO tempImportBO;
	@Autowired
	private InwarehouseBO inwarehouseBO;
	@Autowired
	private PurchaseInwarehouseBOV2 purchaseInwarehouseBO;
	@Autowired
	private PurchaseDetailDao purchaseDetailDao;
	@Autowired
	private TempImportDetailDao tempImportDetailDao;

	private static final Logger logger = Logger.getLogger(DoPurchaseService.class);

	@Deprecated
	public void batchUpdatePurchasePlanForPurchaseAmount(Integer repoId) {
		this.purchaseBO.batchUpdatePurchasePlanForPurchaseAmount(repoId);
	}

	public List<PurchaseDetail> findPurchaseDetailByBatchId(Integer batchId) {
		return this.purchaseDetailDao.findByBatchId(batchId);
	}

	/**
	 * 入库前检查
	 * 
	 * @param purchaseOperator
	 * @param bookId
	 * @param amount
	 * @return
	 */
	public String checkForDoInwarehouse(String purchaseOperator, Integer bookId, Integer amount) {
		return this.purchaseInwarehouseBO.checkForDoInwarehouseNew(purchaseOperator, bookId, amount);
	}

	/**
	 * 入库完成后的后续操作，由子类进行扩展
	 * 
	 * @param purchaseOperator
	 * @param bookId
	 * @param amount
	 * @param repoId
	 */
	public void afterPut(String purchaseOperator, Integer bookId, Integer amount, Integer repoId, Integer batchId) {
		this.purchaseInwarehouseBO.afterPutNew(purchaseOperator, bookId, amount, repoId, batchId);
	}

	/**
	 * 录入黑名单
	 * 
	 * @param bookId
	 * @return 操作失败原因，如果成功，返回空字符串
	 */
	public String addBlackList(Integer bookId, Integer repoId) {
		try {
			purchaseBO.addBlackList(bookId, repoId);
			return "";
		} catch (Exception e) {
			String msg = "加入黑名单失败," + e.getMessage();
			logger.error(msg, e);
			return msg;
		}
	}

	/**
	 * 录入白名单
	 * 
	 * @return 操作失败原因，如果成功，返回空字符串
	 */
	public String addWhiteList(Integer bookId, Integer repoId) {
		try {
			purchaseBO.addWhiteList(bookId, repoId);
			return "";
		} catch (Exception e) {
			String msg = "加入白名单失败," + e.getMessage();
			logger.error(msg, e);
			return msg;
		}
	}

	/**
	 * 删除黑名单或白名单
	 * 
	 * @param bookId
	 * @return 操作失败原因，如果成功，返回空字符串
	 */
	public String deleteBlackOrWhiteList(Integer bookId, Integer repoId) {
		try {
			purchaseBO.deleteBlackOrWhiteList(bookId, repoId);
			return "";
		} catch (Exception e) {
			String msg = "删除黑名单或白名单失败," + e.getMessage();
			logger.error(msg, e);
			return msg;
		}
	}

	/**
	 * 计算仍需采购数量
	 * 
	 * @param plan
	 * @return
	 */
	public Integer calculateStillRequireAmount(PurchasePlan plan, Integer stockAmount) {
		return purchaseBO.calculateStillRequireAmount(plan, stockAmount);
	}

	/**
	 * 添加/更新图书库存
	 * 
	 * @param importList
	 */
	public void addBookStock(List<StockDTO> importList, Integer repoId) {
		if (importList == null || importList.isEmpty()) {
			return;
		}
		// 批量保存
		for (StockDTO record : importList) {
			String errMsg = updateBookStock(record.getBookId(), record.getStockBalance(), repoId);
			if (StringUtils.isNotBlank(errMsg)) {
				logger.error("更新采购计划中的库存失败:" + errMsg);
			}
		}
	}

	/**
	 * 更新采购计划中的图书库存
	 * 
	 * @param bookId
	 * @param amount
	 * @return
	 */
	public String updateBookStock(Integer bookId, Integer amount, Integer repoId) {
		return purchaseBO.updateBookStock(bookId, amount, repoId);
	}

	/**
	 * 是否允许手动设置采购量
	 * 
	 * @return
	 */
	public boolean isAllowManualDecisionForPurchasePlan() {
		return purchaseBO.isAllowManualDecisionForPurchasePlan();
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
	public String addManualDecision(Integer bookId, Integer amount, Integer repoId) {
		return purchaseBO.addManualDecision(bookId, amount, repoId);
	}

	/**
	 * 去除人工定量
	 * 
	 * @param bookId
	 *            图书ID
	 * @return 操作失败原因，如果成功，返回空字符串
	 */
	public String removeManualDecision(Integer bookId, Integer repoId) {
		try {
			purchaseBO.removeManualDecision(bookId, repoId);
			return "";
		} catch (Exception e) {
			String msg = "去除人工定量失败," + e.getMessage();
			logger.error(msg, e);
			return msg;
		}
	}

	/**
	 * 重新计算计划采购量
	 * 
	 * @param bookId
	 * @return 操作失败原因，如果成功，返回空字符串
	 */
	public String recalculateRequireAmount(Integer bookId, Integer repoId) {
		try {
			purchaseBO.recalculateRequireAmount(bookId, repoId);
			return "";
		} catch (Exception e) {
			String msg = "重新计算计划采购量失败," + e.getMessage();
			logger.error(msg, e);
			return msg;
		}
	}

	/**
	 * 批量添加采购计划
	 * 
	 * @param bookList
	 */
	public void addPurchasePlanForBatch(List<Bookinfo> bookList, Integer repoId) {
		purchaseBO.addPurchasePlanForBatch(bookList, repoId);
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
	public String addPurchaseDetail(int bookId, int purchasedAmount, String operator, String position, String memo,
			Integer repoId, Integer stockAmount) {
		return purchaseBO.addPurchaseDetail(bookId, purchasedAmount, operator, position, memo, repoId, stockAmount);
	}

	/**
	 * 查询采购计划
	 * 
	 * @param dc
	 * @return
	 */
	public List<PurchasePlan> findPurchasePlanByIsbn(String isbn, Integer repoId) {
		return purchasePlanDao.findByIsbn(isbn, repoId);
	}

	/**
	 * 查询采购计划
	 * 
	 * @param bookId
	 * @return
	 */
	public PurchasePlan findPurchasePlanByBookId(int bookId, Integer repoId) {
		return purchaseBO.findPurchasePlanByBookId(bookId, repoId);
	}

	/**
	 * 添加库存或者采购记录到临时表
	 */
	public void addTempImportTask(List<TempImportDTO> list, TempImportTaskBizTypeEnum bizType, String memo) {
		tempImportBO.addTempImportTask(list, bizType, memo);
	}

	/**
	 * 批量处理临时表中的库存或者采购记录，使之与BookInfo关联
	 */
	public void updateTempImportDetail(Integer taskId) {
		tempImportBO.updateTempImportDetail(taskId);
	}

	/**
	 * 将临时表中的库存或者采购记录与BookInfo关联
	 * 
	 * @param purchaseTempRecordId
	 * @param bookId
	 * @return
	 */
	public String updateAssociateTempImportDetailWithBookInfo(Integer tempImportDetailId, Integer bookId) {
		return tempImportBO.updateAssociateTempImportDetailWithBookInfo(tempImportDetailId, bookId);
	}

	/**
	 * 将临时表中的库存或者采购记录与BookInfo关联
	 * 
	 * @param purchaseTempRecordId
	 * @param bookId
	 * @return 失败原因，成功返回null
	 */
	public String updateAssociatePurchaseTempImportWithBookInfo(TempImportDetail detail, Bookinfo book) {
		return tempImportBO.updateAssociatePurchaseTempImportWithBookInfo(detail, book);
	}

	/**
	 * 查出所有有效状态的Task
	 * 
	 * @return
	 */
	public Page<TempImportTask> findAllTempImportTask(Pageable page) {
		return tempImportBO.findAllTempImportTask(page);
	}

	public TempImportTask findTempImportTaskByTaskId(Integer taskId) {
		return tempImportBO.findTempImportTaskByTaskId(taskId);
	}

	/**
	 * 列出特定批次下特定状态的临时记录，用于页面展示或者导出
	 * 
	 * @param taskId
	 * @param tempImportDetailStatus
	 * @return
	 */
	public List<TempImportDetailView> findTempImportDetail(Integer taskId, String tempImportDetailStatus) {
		return tempImportBO.findTempImportDetail(taskId, tempImportDetailStatus);
	}

	/**
	 * 批量更新临时导入记录，使之成功或者失效
	 * 
	 * @param taskId
	 * @return 错误原因，null表示处理成功
	 */
	public String updateTempImportTaskStatus(Integer taskId, Integer tempImportTaskStatus) {
		try {
			tempImportBO.updateTempImportTaskStatus(taskId, tempImportTaskStatus);
			return null;
		} catch (Exception e) {
			logger.error("更新TempImportTask出错", e);
			return "更新出错，原因为" + e.getMessage();
		}
	}

	/**
	 * 创建采购入库单
	 * 
	 * @param inwarehouse
	 * @return 入库单ID
	 */
	public InwarehouseCreateResult createInwarehouse(InwarehouseCreateDTO inwarehouse) {
		// 输入有效性检查
		if (inwarehouse == null) {
			throw new RuntimeException("illegal argument, for input null");
		}
		// 采购入库
		if (InwarehouseBizType.PURCHASE.equals(inwarehouse.getBizType())) {
			// return this.purchaseInwarehouseBO.createInwarehouse(inwarehouse);
			return null;
		}
		// 其他入库
		else {
			return this.inwarehouseBO.createInwarehouse(inwarehouse);
		}
	}

	/**
	 * 执行入库操作
	 * 
	 * @param inwarehouseId
	 *            入库单ID
	 * @param posLabel
	 *            库位标签
	 * @param bookId
	 *            图书ID
	 * @param amount
	 *            入库数量
	 */
	public InwarehouseDealtResult applyInwarehouse(Integer inwarehouseId, String posLabel, Integer bookId,
			Integer amount) {
		// 参数基本检查
		if (inwarehouseId == null) {
			throw new IllegalArgumentException("inwarehouseId could not be null");
		}
		// 入库单检查，必须存在且状态是处理中
		Inwarehouse in = this.inwarehouseDao.findOne(inwarehouseId);
		if (in == null) {
			throw new IllegalArgumentException("不存在的入库单，id=" + inwarehouseId);
		}
		if (!InwarehouseStatus.PROCESSING.equals(in.getStatus())) {
			throw new RuntimeException("入库单的状态必须是处理中, id=" + inwarehouseId);
		}
		// 采购入库
		if (InwarehouseBizType.PURCHASE.equals(in.getBizType())) {
			// return this.purchaseInwarehouseBO.doInwarehouse(in, posLabel,
			// bookId, amount);
			return null;
		}
		// 退货入库或直接入库
		else {
			return this.inwarehouseBO.doInwarehouse(in, posLabel, bookId, amount);
		}
	}

	/**
	 * 完成入库操作
	 * 
	 */
	public void applyTerminateInwarehouse(Integer inwarehouseId) {
		this.inwarehouseBO.terminateInwarehouse(inwarehouseId);
	}

	/**
	 * 删除入库单(置为无效)
	 * 
	 * @param inwarehouseId
	 * @return
	 */
	public void deleteInwarehouse(Integer inwarehouseId) {
		inwarehouseBO.deleteInwarehouse(inwarehouseId);
	}

	/**
	 * 删除入库详细记录
	 * 
	 * @param detailId
	 * @return
	 */
	public void deleteInwarehouseDetail(Integer detailId) {
		this.inwarehouseBO.deleteInwarehouseDetail(detailId);
	}

	/**
	 * 查询入库单
	 * 
	 * @param inwarehouseId
	 * @return
	 */
	public InwarehouseView findInwarehouseViewById(Integer inwarehouseId) {
		// 查询入库单
		Inwarehouse in = this.inwarehouseDao.findOne(inwarehouseId);
		if (in == null) {
			return null;
		}
		InwarehouseView inView = new InwarehouseView();
		BeanUtils.copyProperties(in, inView);
		inView.setBizTypeDisplay(InwarehouseBizType.getDisplay(in.getBizType()));
		inView.setStatusDisplay(InwarehouseStatus.getDisplay(in.getStatus()));
		if (InwarehouseStatus.PROCESSING.equals(in.getStatus())) {
			// 查询入库单下的可用库位
			// DetachedCriteria criteria = DetachedCriteria
			// .forClass(InwarehousePosition.class);
			// criteria.add(Restrictions.eq("inwarehouseId", inwarehouseId));
			// criteria.add(Restrictions.eq("isFull", false));
			// criteria.addOrder(Order.asc("gmtCreate"));
			// List<InwarehousePosition> list = this.inwarehousePositionDao
			// .findByCriteria(criteria);
			// 按照ID排序，由于是同一时间创建的，如果按照时间排序会导致库位顺序错乱的bug
			List<InwarehousePosition> list = this.inwarehousePositionDao.findAvailablePosition(inwarehouseId);
			if (list == null || list.isEmpty()) {
				return inView;
			}
			String[] positionLabel = new String[list.size()];
			Integer[] capacity = new Integer[list.size()];
			for (int i = 0; i < list.size(); i++) {
				positionLabel[i] = list.get(i).getPositionLabel();
				capacity[i] = list.get(i).getCapacity();
			}
			inView.setPositionLabel(positionLabel);
			inView.setCapacity(capacity);
		}
		return inView;
	}

	/**
	 * 按照入库单ID查询入库详情
	 * 
	 * @param inwarehouseId
	 * @return
	 */
	public List<InwarehouseDetail> findInwarehouseDetailByInwarehouseIds(Integer[] inwarehouseIds) {
		return this.inwarehouseDetailDao.findByInwarehouseIds(inwarehouseIds);
	}

	public void deleteOnwayStock(String purchaseOperator, Integer repoId) {
		if (StringUtils.isBlank(purchaseOperator)) {
			return;
		}
		this.purchaseBO.deleteOnwayStock(purchaseOperator, repoId);
	}

	/**
	 * 分页查询所有 PurchasePlan 对象
	 * 
	 * @param page
	 * @return
	 */
	public Page<PurchasePlan> findAllPurchasePlan(Pageable page) {
		return this.purchasePlanDao.findAll(page);
	}

	/**
	 * 分页查询(带条件) PurchasePlan 对象
	 * 
	 * @param spec
	 * @param page
	 * @return
	 */
	public Page<PurchasePlan> findPurchasePlanBySpecPage(Specification<PurchasePlan> spec, Pageable page) {
		return this.purchasePlanDao.findAll(spec, page);
	}

	/**
	 * 分页查询所有 InwarehouseDetail 对象
	 * 
	 * @param page
	 * @return
	 */
	public Page<InwarehouseDetail> findAllInwarehouseDetail(Pageable page) {
		return this.inwarehouseDetailDao.findAll(page);
	}

	/**
	 * 分页查询(带条件) InwarehouseDetail 对象
	 * 
	 * @param spec
	 * @param page
	 * @return
	 */
	public Page<InwarehouseDetail> findInwarehouseDetailBySpecPage(Specification<InwarehouseDetail> spec, Pageable page) {
		return this.inwarehouseDetailDao.findAll(spec, page);
	}

	/**
	 * 分页查询所有 Inwarehouse 对象
	 * 
	 * @param page
	 * @return
	 */
	public Page<Inwarehouse> findAllInwarehouse(Pageable page) {
		return this.inwarehouseDao.findAll(page);
	}

	/**
	 * 分页查询(带条件) Inwarehouse 对象
	 * 
	 * @param spec
	 * @param page
	 * @return
	 */
	public Page<Inwarehouse> findInwarehouseBySpecPage(Specification<Inwarehouse> spec, Pageable page) {
		return this.inwarehouseDao.findAll(spec, page);
	}

	/**
	 * 分页查询所有 PurchaseDetail 对象
	 * 
	 * @param page
	 * @return
	 */
	public Page<PurchaseDetail> findAllPurchaseDetail(Pageable page) {
		return this.purchaseDetailDao.findAll(page);
	}

	/**
	 * 分页查询(带条件) PurchaseDetail 对象
	 * 
	 * @param spec
	 * @param page
	 * @return
	 */
	public Page<PurchaseDetail> findPurchaseDetailBySpecPage(Specification<PurchaseDetail> spec, Pageable page) {
		return this.purchaseDetailDao.findAll(spec, page);
	}

	/**
	 * 分页查询所有 TempImportDetail 对象
	 * 
	 * @param page
	 * @return
	 */
	public Page<TempImportDetail> findAllTempImportDetail(Pageable page) {
		return this.tempImportDetailDao.findAll(page);
	}

	/**
	 * 分页查询(带条件) TempImportDetail 对象
	 * 
	 * @param spec
	 * @param page
	 * @return
	 */
	public Page<TempImportDetail> findTempImportDetailBySpecPage(Specification<TempImportDetail> spec, Pageable page) {
		return this.tempImportDetailDao.findAll(spec, page);
	}

}
