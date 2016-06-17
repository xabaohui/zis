package com.zis.purchase.calculate;

import com.zis.common.cache.SysVarCache;
import com.zis.common.cache.SysVarConstant;

public class MixedCalculater implements BookAmountCalculateInterface {
	private SalesCalculater salesCalculater;
	private RequirementCalculater requirementCalculater;
	private SysVarCache sysVarCache;

	public Integer calculate(int bookId) {
		Integer salesPercent = sysVarCache.getSystemVar(SysVarConstant.PURCHASE_SALES_PERCENT.getKeyName());
		// List<SysVar> list1 = sysVarDao.findByDepKey("requirementPercent");
		// List<SysVar> list2 = sysVarDao.findByDepKey("salesPercent");
		int salesAmount = salesCalculater.calculate(bookId);
		int requirementAmount = requirementCalculater.calculate(bookId);
		if (salesAmount >= 0 && requirementAmount >= 0) {
			Integer part1 = salesAmount * salesPercent / 100;
			Integer part2 = requirementAmount * (1 - salesPercent / 100);
			return part1 + part2;
		} else {
			return 0;
		}
	}

	public SalesCalculater getSalesCalculater() {
		return salesCalculater;
	}

	public void setSalesCalculater(SalesCalculater salesCalculater) {
		this.salesCalculater = salesCalculater;
	}

	public RequirementCalculater getRequirementCalculater() {
		return requirementCalculater;
	}

	public void setRequirementCalculater(
			RequirementCalculater requirementCalculater) {
		this.requirementCalculater = requirementCalculater;
	}

	public SysVarCache getSysVarCache() {
		return sysVarCache;
	}

	public void setSysVarCache(SysVarCache sysVarCache) {
		this.sysVarCache = sysVarCache;
	}

}
