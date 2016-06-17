package com.zis.purchase.calculate;

import java.util.List;

import com.zis.common.cache.SysVarCache;
import com.zis.common.cache.SysVarConstant;
import com.zis.purchase.bean.Storesales;
import com.zis.purchase.dao.StoreSalesDao;

public class SalesCalculater implements BookAmountCalculateInterface {

	private StoreSalesDao storeSalesDao;
	private SysVarCache sysVarCache;

	public Integer calculate(int bookId) {
		Integer portio = sysVarCache
				.getSystemVar(SysVarConstant.PURCHASE_SALES_PORTIO.getKeyName());
		// List<SysVar> slist = sysVarDao.findByDepKey("requiremenPortio");
		return getSalesAmount(bookId) * portio / 100;
	}

	private Integer getSalesAmount(int bookId) {
		if (bookId <= 0) {
			return 0;
		}
		List<Storesales> list = storeSalesDao.findByBookId(bookId);
		if (list == null || list.isEmpty()) {
			return 0;
		}
		return list.get(0).getSales();
	}

	public StoreSalesDao getStoreSalesDao() {
		return storeSalesDao;
	}

	public void setStoreSalesDao(StoreSalesDao storeSalesDao) {
		this.storeSalesDao = storeSalesDao;
	}

	public SysVarCache getSysVarCache() {
		return sysVarCache;
	}

	public void setSysVarCache(SysVarCache sysVarCache) {
		this.sysVarCache = sysVarCache;
	}

}
