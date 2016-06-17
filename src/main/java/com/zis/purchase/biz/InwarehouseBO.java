package com.zis.purchase.biz;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.zis.common.util.ZisUtils;
import com.zis.purchase.bean.Inwarehouse;
import com.zis.purchase.bean.InwarehouseDetail;
import com.zis.purchase.bean.InwarehousePosition;
import com.zis.purchase.bean.InwarehouseStatus;
import com.zis.purchase.bean.PurchasePlan;
import com.zis.purchase.dao.InwarehouseDao;
import com.zis.purchase.dao.InwarehouseDetailDao;
import com.zis.purchase.dao.InwarehousePositionDao;
import com.zis.purchase.dao.PurchasePlanDao;
import com.zis.purchase.dto.InwarehouseCreateDTO;
import com.zis.purchase.dto.InwarehouseCreateResult;
import com.zis.purchase.dto.InwarehouseDealtResult;

/**
 * 入库核心业务逻辑
 * <p/>
 * 执行入库，分以下几个步骤：<br/>
 * 1. 创建入库单createInwarehouse()<br/>
 * 2. 执行入库操作doInwarehouse()<br/>
 * 3. 重复以上步骤2，如果库位已满，自动切换到下一个可用库位，并提示调用处<br/>
 * 4. 结束入库单terminateInwarehouse()<br/>
 * 
 * @author yz
 * 
 */
public class InwarehouseBO {

	private InwarehouseDao inwarehouseDao;
	private InwarehouseDetailDao inwarehouseDetailDao;
	private InwarehousePositionDao inwarehousePositionDao;
	protected PurchasePlanDao purchasePlanDao;

	protected Logger logger = Logger.getLogger(PurchaseInwarehouseBO.class);

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
		String[] labels = inwarehouse.getStockPosLabel();
		Integer[] capacities = inwarehouse.getStockPosCapacity();
		if(labels == null || capacities == null) {
			throw new RuntimeException("库位名称和库位容量必须设定");
		}
		if (labels.length != capacities.length) {
			throw new RuntimeException("库位名称和库位容量不匹配");
		}
		for (int i = 0; i < labels.length; i++) {
			if(StringUtils.isBlank(labels[i])) {
				throw new RuntimeException("库位名称不能为空");
			}
			if(capacities[i] <= 0) {
				throw new RuntimeException("库位容量必须大于0");
			}
		}
		String error = checkForCreateInwarehouse(inwarehouse);
		if(StringUtils.isNotBlank(error)) {
			InwarehouseCreateResult result = new InwarehouseCreateResult();
			result.setFailReason(error);
			result.setSuccess(false);
			return result;
		}
		// 创建采购入库单
		Inwarehouse record = new Inwarehouse();
		record.setBizType(inwarehouse.getBizType());
		record.setInwarehouseOperator(inwarehouse.getInwarehouseOperator());
		record.setSource(inwarehouse.getPurchaseOperator());// 来源设置成采购员
		record.setMemo(inwarehouse.getMemo());
		record.setStatus(InwarehouseStatus.PROCESSING);
		record.setAmount(0);
		record.setGmtCreate(ZisUtils.getTS());
		record.setGmtModify(ZisUtils.getTS());
		record.setVersion(0);
		this.inwarehouseDao.save(record);

		// 创建入库辅助表InwarehousePosition
		for (int i = 0; i < labels.length; i++) {
			createInwarehousePosition(record.getId(), labels[i], capacities[i]);
		}
		InwarehouseCreateResult result = new InwarehouseCreateResult();
		result.setSuccess(true);
		result.setInwarehouseId(record.getId());
		if(labels != null && labels.length > 0) {
			result.setCurrentPosition(labels[0]);
		}
		return result;
	}

	/**
	 * 创建入库单逻辑的附加检查，由子类完成实现
	 * 
	 * @param inwarehouse
	 * @return
	 */
	protected String checkForCreateInwarehouse(InwarehouseCreateDTO inwarehouse) {
		return StringUtils.EMPTY;
	}

	/**
	 * 创建辅助入库的库位记录
	 * 
	 * @param inwarehouseId
	 * @param positionLabel
	 * @param capacity
	 */
	private Integer createInwarehousePosition(Integer inwarehouseId,
			String positionLabel, Integer capacity) {
		InwarehousePosition pos = new InwarehousePosition();
		pos.setInwarehouseId(inwarehouseId);
		pos.setPositionLabel(positionLabel);
		pos.setCapacity(capacity);
		pos.setCurrentAmount(0);
		pos.setIsFull(false);
		pos.setGmtCreate(ZisUtils.getTS());
		pos.setGmtModify(ZisUtils.getTS());
		pos.setVersion(0);
		this.inwarehousePositionDao.save(pos);
		return pos.getId();
	}

	/**
	 * 选择库位，返回库位ID
	 * 
	 * @param in
	 *            入库单ID
	 * @param posLabel
	 *            库位标
	 * @param amount
	 *            入库数量，或者库位的可用存量
	 * @param autoCreate
	 *            当库位记录不存在时，自动创建
	 * @return 库位ID
	 * @throws RuntimeException
	 *             如果该库位容量不足，或者不允许存放该商品
	 */
	// public Integer selectPosition(Integer inwarehouseId, String posLabel,
	// Integer amount, boolean autoCreate) {
	// DetachedCriteria criteria = DetachedCriteria
	// .forClass(InwarehousePosition.class);
	// criteria.add(Restrictions.eq("inwarehouseId", inwarehouseId));
	// criteria.add(Restrictions.eq("positionLabel", posLabel));
	// criteria.add(Restrictions.eq("isFull", false));
	// List<InwarehousePosition> posList = this.inwarehousePositionDao
	// .findByCriteria(criteria);
	// if (posList == null || posList.isEmpty()) {
	// if (autoCreate) {
	// return createInwarehousePosition(inwarehouseId, posLabel,
	// amount);
	// } else {
	// throw new RuntimeException("无此库位，请选择有效库位");
	// }
	// }
	// if (posList.size() > 1) {
	// throw new RuntimeException("数据错误，存在相同的辅助入库库位记录，inwarehouseId="
	// + inwarehouseId + ",Label=" + posLabel);
	// }
	// InwarehousePosition pos = posList.get(0);
	// if ((pos.getCapacity() - pos.getCurrentAmount()) < amount) {
	// throw new RuntimeException("库位已满，请更换库位");
	// }
	// return pos.getId();
	// }

	/**
	 * 执行入库操作
	 * 
	 * @param inwarehouseId
	 *            入库单ID
	 * @param bookId
	 *            图书ID
	 * @param posLabel
	 *            库位标签 服务器会自动选择可用库位，可用库位不一定是posLabel，如果不是posLabel，返回的结果中会有提示
	 * @param amount
	 *            入库数量
	 */
	public InwarehouseDealtResult doInwarehouse(Inwarehouse in,
			final String posLabel, final Integer bookId, final Integer amount) {
		// 基本参数检查
		if (in == null) {
			throw new IllegalArgumentException("inwarehouse could not be null");
		}
		if (StringUtils.isBlank(posLabel)) {
			throw new IllegalArgumentException("posLabel could not be null");
		}
		if (bookId == null) {
			throw new IllegalArgumentException("bookId could not be null");
		}
		if (amount == null || amount < 1) {
			throw new IllegalArgumentException("入库数量必须大于等于1");
		}
		String error = checkForDoInwarehouse(in, bookId, amount);// 其他检查，由子类进行扩展
		if (StringUtils.isNotBlank(error)) {
			throw new RuntimeException(error);
		}
		InwarehouseDealtResult result = new InwarehouseDealtResult();

		// 自动选择可用库位
		InwarehousePosition pos = findAvailablePosition(in.getId(), amount);
		if (pos == null) {
			// 没有可用库位，直接返回
			result.setSuccess(false);
			result.setFailReason("所有可用库位已存满，操作结束");
			result.setTotalAmount(in.getAmount());
			return result;
		}
		result.setPrePosLabel(posLabel);
		result.setCurPosLabel(pos.getPositionLabel());
		if (!posLabel.equals(pos.getPositionLabel())) {
			// 库位发生变化，提示客户端
			result.setPositionChange(true);
		}

		// 执行入库操作
		putIntoPosition(in, pos, bookId, amount);
		// 更新采购计划
		updatePurchasePlanForStock(bookId, amount);
		// 入库完成后的后续操作，由子类进行扩展
		afterPut(in, bookId, amount);
		result.setSuccess(true);
		result.setTotalAmount(in.getAmount());
		return result;
	}

	// 更新采购计划中的库存量和在途库存量
	private void updatePurchasePlanForStock(Integer bookId,
			Integer amount) {
		PurchasePlan plan = this.purchasePlanDao.findByBookId(bookId);
		if(plan == null) {
			// 计划不存在，则不更新
			return;
		}
		plan.setStockAmount(plan.getStockAmount() + amount);
		plan.setGmtModify(ZisUtils.getTS());
		plan.setVersion(plan.getVersion() + 1);
		this.purchasePlanDao.update(plan);
	}

	/**
	 * 自动查找可用库位
	 * 
	 * @param inwarehouseId
	 *            入库单编号
	 * @param amount
	 *            入库数量
	 * @return 可用库位对象，如果没有可用库位，返回null
	 */
	private InwarehousePosition findAvailablePosition(Integer inwarehouseId,
			Integer amount) {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(InwarehousePosition.class);
		criteria.add(Restrictions.eq("inwarehouseId", inwarehouseId));
		criteria.add(Restrictions.eq("isFull", false));
		criteria.addOrder(Order.asc("gmtCreate"));
		List<InwarehousePosition> posList = this.inwarehousePositionDao
				.findByCriteria(criteria);
		if (posList == null || posList.isEmpty()) {
			return null;
		}
		List<InwarehousePosition> updatePos = new ArrayList<InwarehousePosition>();
		InwarehousePosition availablePos = null;
		// 遍历所有库位
		for (InwarehousePosition curPos : posList) {
			// 如果当前库位可用量不足存放，为了保证顺次存放，需将当前库位isFull置为true
			if (curPos.getCapacity() - curPos.getCurrentAmount() < amount) {
				updatePos.add(curPos);
				// this.inwarehousePositionDao.save(curPos);
			} else {
				availablePos = curPos;
				break;
			}
		}
		// 如果没有可用库位，则不更新当前库位的isFull标记
		if(availablePos != null) {
			for (InwarehousePosition pos : updatePos) {
				pos.setIsFull(true);
				pos.setGmtModify(ZisUtils.getTS());
				pos.setVersion(pos.getVersion() + 1);
				this.inwarehousePositionDao.save(pos);
			}
		}
		return availablePos;
	}

	/**
	 * 入库前的其他检查，由子类进行扩展
	 * 
	 * @param in
	 * @param bookId
	 * @param amount
	 * @return
	 */
	protected String checkForDoInwarehouse(Inwarehouse in, Integer bookId,
			Integer amount) {
		return StringUtils.EMPTY;
	}

	/**
	 * 尝试入库，如果库位已满，会报错
	 * 
	 * @param in
	 * @param pos
	 * @param bookId
	 * @param amount
	 */
	private void putIntoPosition(Inwarehouse in, InwarehousePosition pos,
			Integer bookId, Integer amount) {
		int amountAfterThisIn = pos.getCapacity() - pos.getCurrentAmount()
				- amount;
		// 更新入库单
		in.setAmount(in.getAmount() + amount);
		in.setGmtModify(ZisUtils.getTS());
		in.setVersion(in.getVersion() + 1);
		this.inwarehouseDao.save(in);
		// 更新库位
		pos.setCurrentAmount(pos.getCurrentAmount() + amount);
		if (amountAfterThisIn <= 0) {
			pos.setIsFull(true);
		}
		pos.setGmtModify(ZisUtils.getTS());
		pos.setVersion(pos.getVersion() + 1);
		this.inwarehousePositionDao.save(pos);
		// 新增入库明细记录
		InwarehouseDetail detail = new InwarehouseDetail();
		detail.setAmount(amount);
		detail.setBizType(in.getBizType());
		detail.setBookId(bookId);
		detail.setInwarehouseId(in.getId());
		detail.setPositionLabel(pos.getPositionLabel());
		detail.setGmtCreate(ZisUtils.getTS());
		detail.setGmtModify(ZisUtils.getTS());
		detail.setVersion(0);
		this.inwarehouseDetailDao.save(detail);
	}

	/**
	 * 入库完成后的后续操作，由子类进行扩展
	 * 
	 * @param amount
	 */
	protected void afterPut(Inwarehouse in, Integer bookId, Integer amount) {

	}

	/**
	 * 完成入库操作
	 * 
	 */
	public void terminateInwarehouse(Integer inwarehouseId) {
		Inwarehouse in = this.inwarehouseDao.findById(inwarehouseId);
		if (in == null) {
			throw new IllegalArgumentException("不存在的入库单，id=" + inwarehouseId);
		}
		if (!InwarehouseStatus.PROCESSING.equals(in.getStatus())) {
			throw new RuntimeException("入库单的状态必须是处理中, id=" + inwarehouseId);
		}
		in.setStatus(InwarehouseStatus.SUCCESS);
		in.setGmtModify(ZisUtils.getTS());
		in.setVersion(in.getVersion() + 1);
		this.inwarehouseDao.save(in);
	}
	
	/**
	 * 删除入库单(置为无效)
	 * @param inwarehouseId
	 * @return
	 */
	public void deleteInwarehouse(Integer inwarehouseId) {
		if(inwarehouseId == null) {
			throw new RuntimeException("inwarehouseId could not be null");
		}
		Inwarehouse in = this.inwarehouseDao.findById(inwarehouseId);
		if(in == null) {
			return;
		}
		if(!InwarehouseStatus.PROCESSING.equals(in.getStatus())) {
			throw new RuntimeException("待删除的入库单状态必须是“处理中”");
		}
		in.setStatus(InwarehouseStatus.CANCEL);
		in.setGmtModify(ZisUtils.getTS());
		in.setVersion(in.getVersion() + 1);
		this.inwarehouseDao.save(in);
	}
	
	/**
	 * 删除入库详细记录
	 * @param detailId
	 * @return
	 */
	public void deleteInwarehouseDetail(Integer detailId) {
		InwarehouseDetail detail = this.inwarehouseDetailDao.findById(detailId);
		if(detail == null) {
			return;
		}
		this.inwarehouseDetailDao.delete(detail);
		Inwarehouse in = this.inwarehouseDao.findById(detail.getInwarehouseId());
		in.setAmount(in.getAmount() - detail.getAmount());
		in.setGmtModify(ZisUtils.getTS());
		in.setVersion(in.getVersion() + 1);
		this.inwarehouseDao.save(in);
	}

	public void setInwarehouseDao(InwarehouseDao inwarehouseDao) {
		this.inwarehouseDao = inwarehouseDao;
	}

	public void setInwarehouseDetailDao(
			InwarehouseDetailDao inwarehouseDetailDao) {
		this.inwarehouseDetailDao = inwarehouseDetailDao;
	}

	public void setInwarehousePositionDao(
			InwarehousePositionDao inwarehousePositionDao) {
		this.inwarehousePositionDao = inwarehousePositionDao;
	}
	
	public void setPurchasePlanDao(PurchasePlanDao purchasePlanDao) {
		this.purchasePlanDao = purchasePlanDao;
	}
}
