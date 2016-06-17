package com.zis.purchase.biz;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.zis.common.util.ZisUtils;
import com.zis.purchase.bean.Inwarehouse;
import com.zis.purchase.bean.PurchaseDetail;
import com.zis.purchase.bean.PurchaseDetailStatus;
import com.zis.purchase.bean.PurchasePlan;
import com.zis.purchase.dao.PurchaseDetailDao;
import com.zis.purchase.dao.PurchasePlanDao;

/**
 * 采购入库核心业务逻辑
 * 
 * @author yz
 * 
 */
public class PurchaseInwarehouseBO extends InwarehouseBO {

	private PurchaseDetailDao purchaseDetailDao;
	
	@Override
	protected String checkForDoInwarehouse(Inwarehouse in, Integer bookId, Integer amount) {
		// 查询尚未入库的采购明细，并检查数量
		List<PurchaseDetail> purchasedList = findPurchaseDetail(in.getSource(), bookId);
		if (purchasedList == null || purchasedList.isEmpty()) {
			return "该记录已超出采购清单" + amount + "本";
		}
		Integer amountPurchased = 0; //已采购数量
		Integer amountChecked = 0; //已入库数量
		for (PurchaseDetail order : purchasedList) {
			amountPurchased += order.getPurchasedAmount();
			amountChecked += order.getInwarehouseAmount();
		}
		// 检查本次入库数量是否超过采购明细中的未入库数量
		int amountAfterThisCheck = amountPurchased - amountChecked - amount;
		if (amountAfterThisCheck < 0) {
			return "该记录已超出采购清单"
					+ Math.abs(amountAfterThisCheck) + "本";
		}
		return StringUtils.EMPTY;
	}
	
	private List<PurchaseDetail> findPurchaseDetail(String purchaseOperator, Integer bookId) {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(PurchaseDetail.class);
		criteria.add(Restrictions.eq("operator", purchaseOperator));
		criteria.add(Restrictions.eq("bookId", bookId));
		criteria.add(Restrictions.eq("status", PurchaseDetailStatus.PURCHASED));
		return this.purchaseDetailDao.findByCriteria(criteria);
	}

	@Override
	protected void afterPut(Inwarehouse in, Integer bookId, Integer amount) {
		super.afterPut(in, bookId, amount);
		// 遍历所有采购明细，逐个更新
		List<PurchaseDetail> purchasedList = findPurchaseDetail(in.getSource(), bookId);
		if(purchasedList == null || purchasedList.isEmpty()) {
			throw new RuntimeException("数据错误，不存在未入库的采购明细，采购员="+in.getSource()+"bookId=" + bookId);
		}
		Integer stillRemains = amount; // 剩余未入库数量
		int i = 0;
		do {
			PurchaseDetail pur = purchasedList.get(i);
			Integer purAmountNotIn = pur.getPurchasedAmount()
					- pur.getInwarehouseAmount(); // 当前采购明细尚未入库的数量（简称“当前明细”）
			if (stillRemains >= purAmountNotIn) {
				// 剩余数量 >= 当前明细
				pur.setInwarehouseAmount(pur.getPurchasedAmount());
				pur.setStatus(PurchaseDetailStatus.CHECKED);
				pur.setGmtModify(ZisUtils.getTS());
				pur.setVersion(pur.getVersion() + 1);
				this.purchaseDetailDao.save(pur);
				stillRemains = stillRemains - purAmountNotIn;
			} else {
				// 剩余数量<当前明细
				pur.setInwarehouseAmount(pur.getInwarehouseAmount()
						+ stillRemains);
				pur.setGmtModify(ZisUtils.getTS());
				pur.setVersion(pur.getVersion() + 1);
				this.purchaseDetailDao.save(pur);
				stillRemains = 0;
			}
			i++;
		} while (stillRemains > 0);
		// 更新在途库存量
		PurchasePlan plan = this.purchasePlanDao.findByBookId(bookId);
		if(plan == null) {
			// 计划不存在，则不更新
			return;
		}
		int remains = plan.getPurchasedAmount() - amount;
		remains = remains > 0 ? remains : 0;
		plan.setPurchasedAmount(remains);
		plan.setGmtModify(ZisUtils.getTS());
		plan.setVersion(plan.getVersion() + 1);
		this.purchasePlanDao.update(plan);
	}
	
	public void setPurchaseDetailDao(PurchaseDetailDao purchaseDetailDao) {
		this.purchaseDetailDao = purchaseDetailDao;
	}
	
	@Override
	public void setPurchasePlanDao(PurchasePlanDao purchasePlanDao) {
		super.setPurchasePlanDao(purchasePlanDao);
	}
}
