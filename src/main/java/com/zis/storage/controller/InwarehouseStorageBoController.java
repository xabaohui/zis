package com.zis.storage.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.directwebremoting.annotations.RemoteMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.zis.common.util.ZisUtils;
import com.zis.purchase.bean.InwarehousePosition;
import com.zis.purchase.biz.DoPurchaseService;
import com.zis.purchase.dto.InwarehouseDealtResult;
import com.zis.purchase.repository.InwarehousePositionDao;
import com.zis.storage.entity.StorageIoBatch;
import com.zis.storage.repository.StorageIoBatchDao;
import com.zis.storage.service.StorageService;
import com.zis.storage.util.StorageUtil;

/**
 * 入库核心业务逻辑处理Action
 * <p/>
 * service层由于要借用RuntimeException控制Transaction，<br/>
 * 而dwr在Exception方面的处理相对不太灵活，<br/>
 * 因此使用这个类来进行桥接dwr和service
 * 
 * 
 */
@Controller
public class InwarehouseStorageBoController {

	@Autowired
	private StorageIoBatchDao storageIoBatchDao;

	@Autowired
	private InwarehousePositionDao inwarehousePositionDao;

	@Autowired
	private StorageService storageService;

	@Autowired
	private DoPurchaseService doPurchaseService;

	private final String PURCHASE_TYPE = "purchase";// 采购入库
	private final String BATCH_TYPE = "batch";// 批量入库

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
	@RemoteMethod
	public InwarehouseDealtResult doStorageInwarehouse(Integer ioBatchId, String posLabel, Integer bookId,
			Integer amount, String purchaseOperator, String bizType) {
		try {
			StorageIoBatch ioBatch = this.storageIoBatchDao.findOne(ioBatchId);
			if (ioBatch == null) {
				throw new RuntimeException("批次Id有误" + ioBatchId);
			}
			return this.applyInwarehouse(ioBatch, posLabel, bookId, amount, purchaseOperator, bizType);
		} catch (Exception e) {
			InwarehouseDealtResult result = new InwarehouseDealtResult();
			result.setSuccess(false);
			result.setFailReason(e.getMessage());
			e.printStackTrace();
			return result;
		}
	}

	/**
	 * 快速入库
	 * 
	 * @param ioBatchId
	 * @param posLabel
	 * @param bookId
	 * @param amount
	 * @return
	 */
	public InwarehouseDealtResult fastStorageInwarehouse(Integer oldAmount, String posLabel, Integer bookId,
			Integer amount) {
		if (oldAmount == null) {
			oldAmount = 0;
		}
		InwarehouseDealtResult result = new InwarehouseDealtResult();
		try {
			result.setCurPosLabel(posLabel);
			result.setPrePosLabel(posLabel);
			result.setSuccess(true);
			result.setTotalAmount(oldAmount + amount);
			result.setPositionChange(false);
			this.storageService.directInStorage(StorageUtil.getRepoId(), bookId, amount, posLabel,
					StorageUtil.getUserId());
			return result;
		} catch (Exception e) {
			result.setTotalAmount(oldAmount + amount);
			result.setFailReason(e.getMessage());
			result.setSuccess(false);
			return result;
		}
	}

	/**
	 * 执行入库操作检查
	 * 
	 * @param ioBatchId
	 *            批次ID
	 * @param bookId
	 *            图书ID
	 * @param posLabel
	 *            库位标签 服务器会自动选择可用库位，可用库位不一定是posLabel，如果不是posLabel，返回的结果中会有提示
	 * @param amount
	 *            入库数量
	 */
	private InwarehouseDealtResult applyInwarehouse(StorageIoBatch ioBatch, final String posLabel,
			final Integer bookId, final Integer amount, final String purchaseOperator, final String bizType) {
		// 基本参数检查
		if (ioBatch == null) {
			throw new IllegalArgumentException("ioBatch could not be null");
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
		boolean purchaseInwarehouse = StringUtils.isNotBlank(purchaseOperator) && PURCHASE_TYPE.equals(bizType);
		// TODO　采购入库调用此方法
		if (purchaseInwarehouse) {
			String error = this.doPurchaseService.checkForDoInwarehouse(purchaseOperator, bookId, amount);// 其他检查，由子类进行扩展
			if (StringUtils.isNotBlank(error)) {
				throw new RuntimeException(error);
			}
		}
		InwarehouseDealtResult result = new InwarehouseDealtResult();

		// 自动选择可用库位
		InwarehousePosition pos = findAvailablePosition(ioBatch.getBatchId(), amount);
		if (pos == null) {
			// 没有可用库位，直接返回
			result.setSuccess(false);
			result.setFailReason("所有可用库位已存满，操作结束");
			result.setTotalAmount(ioBatch.getAmount());
			return result;
		}
		result.setPrePosLabel(posLabel);
		result.setCurPosLabel(pos.getPositionLabel());
		if (!posLabel.equals(pos.getPositionLabel())) {
			// 库位发生变化，提示客户端
			result.setPositionChange(true);
		}

		// 执行入库操作
		putIntoPosition(ioBatch, pos, bookId, amount);
		// 入库后操作
		// TODO　采购入库调用此方法
		if (purchaseInwarehouse) {
			this.doPurchaseService.afterPut(purchaseOperator, bookId, amount, StorageUtil.getRepoId(),
					ioBatch.getBatchId());
		}
		result.setSuccess(true);
		result.setTotalAmount(ioBatch.getAmount());
		return result;
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
	private InwarehousePosition findAvailablePosition(Integer ioBatchId, Integer amount) {
		// 按照ID排序，由于是同一时间创建的，如果按照时间排序会导致库位顺序错乱的bug
		List<InwarehousePosition> posList = this.inwarehousePositionDao.findAvailablePosition(ioBatchId);
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
		if (availablePos != null) {
			for (InwarehousePosition pos : updatePos) {
				pos.setIsFull(true);
				pos.setGmtModify(ZisUtils.getTS());
				this.inwarehousePositionDao.save(pos);
			}
		}
		return availablePos;
	}

	/**
	 * 尝试入库，如果库位已满，会报错
	 * 
	 * @param in
	 * @param pos
	 * @param bookId
	 * @param amount
	 */
	private void putIntoPosition(StorageIoBatch ioBatch, InwarehousePosition pos, Integer bookId, Integer amount) {
		int amountAfterThisIn = pos.getCapacity() - pos.getCurrentAmount() - amount;
		// 更新库位
		pos.setCurrentAmount(pos.getCurrentAmount() + amount);
		// 添加数量
		ioBatch.setAmount(ioBatch.getAmount() + amount);
		if (amountAfterThisIn <= 0) {
			pos.setIsFull(true);
		}
		pos.setGmtModify(ZisUtils.getTS());
		this.inwarehousePositionDao.save(pos);
		// 新增入库明细记录及更新入库单
		this.storageService.addInStorageDetail(ioBatch.getBatchId(), bookId, amount, pos.getPositionLabel(),
				StorageUtil.getUserId());
	}
}
