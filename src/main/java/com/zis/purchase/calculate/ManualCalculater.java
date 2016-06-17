package com.zis.purchase.calculate;

import com.zis.purchase.bean.PurchasePlan;
import com.zis.purchase.dao.PurchasePlanDao;

public class ManualCalculater implements BookAmountCalculateInterface {

	private PurchasePlanDao purchasePlanDao;

	public Integer calculate(int bookId) {
		PurchasePlan plan = this.purchasePlanDao.findByBookId(bookId);
		if(plan == null) {
			throw new RuntimeException("没有采购计划，bookId=" + bookId);
		}
		return plan.getManualDecision();
		// List<Manualdecision> list = null;
		// if (bookId > 0) {
		// list = manualdecisionDao.findByBookId(bookId);
		// if (list != null && !list.isEmpty()) {
		// return list.get(0).getAmount();
		// } else {
		// return null;
		// }
		// } else {
		// return null;
		// }
	}

	public void setPurchasePlanDao(PurchasePlanDao purchasePlanDao) {
		this.purchasePlanDao = purchasePlanDao;
	}
}
