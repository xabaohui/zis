package com.zis.purchase.calculate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zis.purchase.bean.PurchasePlan;
import com.zis.purchase.dao.PurchasePlanDao;

@Component(value="manualCalculater")
public class ManualCalculater implements BookAmountCalculateInterface {
	
	@Autowired
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
}
