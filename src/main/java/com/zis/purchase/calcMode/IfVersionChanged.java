package com.zis.purchase.calcMode;

import java.util.List;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.dao.BookinfoDao;
import com.zis.bookinfo.util.ConstantString;
import com.zis.common.cache.SysVarCache;
import com.zis.common.cache.SysVarConstant;
import com.zis.common.util.AlterEditionPurchaseStrategyEnum;
import com.zis.purchase.bean.Bookblacklist;
import com.zis.purchase.bean.Bookwhitelist;
import com.zis.purchase.dao.BookblacklistDao;
import com.zis.purchase.dao.BookwhitelistDao;

@Deprecated
public class IfVersionChanged {
	private BookinfoDao bookinfoDao;
	private BookblacklistDao bookblacklistDao;
	private BookwhitelistDao bookwhitelistDao;
	private SysVarCache sysVarCache;

	/**
	 * 判断图书是否有用
	 * 
	 * @param bookId
	 * @return true可用，false不可用
	 */
	public boolean isUsefulBook(Bookinfo bi) {
		// 状态不是“正式”，false
		if(!bi.getBookStatus().equals(ConstantString.USEFUL)) {
			return false;
		}
		// 黑名单，false
		if(isBookInBlackList(bi.getId())) {
			return false;
		}
		// 如果是最新版，true
		if (bi.getIsNewEdition()) {
			return true;
		}
		// 如果不是最新版，则根据过期策略来定
		Integer strategy = sysVarCache
				.getSystemVar(SysVarConstant.PURCHASE_STRATEGY_ALTER_EDITION_ALLOW
						.getKeyName());
		AlterEditionPurchaseStrategyEnum st = AlterEditionPurchaseStrategyEnum
				.getEnumByValue(strategy);
		switch (st) {
		case GET_ALL:// 全要
			return true;
		case GET_NONE:// 全不要
			return false;
		case GET_WHITE_LIST:// 只要白名单里的
			return isBookInWhiteList(bi.getId());
		default:
			return false;
		}
	}

	private boolean isBookInBlackList(int bookId) {
		Bookblacklist example = new Bookblacklist();
		example.setBookId(bookId);
		example.setStatus(ConstantString.STATUS_VALID);
		List<Bookblacklist> blackList = bookblacklistDao.findByExample(example);
		return blackList != null && !blackList.isEmpty();
	}

	private boolean isBookInWhiteList(int bookId) {
		Bookwhitelist example = new Bookwhitelist();
		example.setBookId(bookId);
		example.setStatus(ConstantString.STATUS_VALID);
		List<Bookwhitelist> whiteList = bookwhitelistDao.findByExample(example);
		return whiteList != null && !whiteList.isEmpty();
	}

	public BookinfoDao getBookinfoDao() {
		return bookinfoDao;
	}

	public void setBookinfoDao(BookinfoDao bookinfoDao) {
		this.bookinfoDao = bookinfoDao;
	}

	public SysVarCache getSysVarCache() {
		return sysVarCache;
	}

	public void setSysVarCache(SysVarCache sysVarCache) {
		this.sysVarCache = sysVarCache;
	}

	public BookblacklistDao getBookblacklistDao() {
		return bookblacklistDao;
	}

	public void setBookblacklistDao(BookblacklistDao bookblacklistDao) {
		this.bookblacklistDao = bookblacklistDao;
	}

	public BookwhitelistDao getBookwhitelistDao() {
		return bookwhitelistDao;
	}

	public void setBookwhitelistDao(BookwhitelistDao bookwhitelistDao) {
		this.bookwhitelistDao = bookwhitelistDao;
	}

}
