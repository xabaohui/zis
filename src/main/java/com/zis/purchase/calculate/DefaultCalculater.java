package com.zis.purchase.calculate;

import com.zis.common.cache.SysVarCache;
import com.zis.common.cache.SysVarConstant;

public class DefaultCalculater implements BookAmountCalculateInterface {

	private SysVarCache sysVarCache;

	public Integer calculate(int bookId) {
		return sysVarCache.getSystemVar(SysVarConstant.PURCHASE_DEFAULT_REQURIE_AMT.getKeyName());
	}

	public SysVarCache getSysVarCache() {
		return sysVarCache;
	}

	public void setSysVarCache(SysVarCache sysVarCache) {
		this.sysVarCache = sysVarCache;
	}

}
