package com.zis.purchase.calculate;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.service.BookService;
import com.zis.bookinfo.util.ConstantString;
import com.zis.common.cache.SysVarCache;
import com.zis.common.cache.SysVarConstant;
import com.zis.requirement.bean.Bookamount;
import com.zis.requirement.dao.BookAmountDao;

/**
 * 根据教材使用量计算（大一数据不统计，如果存在不同版本的图书，最新版本的使用量是之前所有版本之和）
 * @author yz
 *
 */
public class RequirementCalculater implements BookAmountCalculateInterface {

	private BookAmountDao bookAmountDao;
	private SysVarCache sysVarCache;
	private BookService bookService;

	public Integer calculate(int bookId) {
		Integer portio = sysVarCache
				.getSystemVar(SysVarConstant.PURCHASE_REQUIREMENT_PORTIO
						.getKeyName());
		return getRequirementAmount(bookId) * portio / 100;
	}

	private Integer getRequirementAmount(int bookId) {
		if (bookId <= 0) {
			return 0;
		}
		Bookinfo book = bookService.findBookById(bookId);
		// 图书不存在或者状态不是正式，直接返回0
		if(book == null || !ConstantString.USEFUL.equals(book.getBookStatus())) {
			return 0;
		}
		DetachedCriteria dc = DetachedCriteria.forClass(Bookamount.class);
		//如果存在不同版本的图书，最新版本的使用量是之前所有版本之和
		if(book.getIsNewEdition() && StringUtils.isNotBlank(book.getGroupId())) {
			DetachedCriteria criteria = DetachedCriteria.forClass(Bookinfo.class);
			criteria.setProjection(Projections.property("id"));
			criteria.add(Restrictions.eq("groupId", book.getGroupId()));
			criteria.add(Restrictions.eq("bookStatus", ConstantString.USEFUL));
			List ids = this.bookService.findBookByCriteria(criteria);
			dc.add(Restrictions.in("bookId", ids));
		} else {
			dc.add(Restrictions.eq("bookId", bookId));
		}
		dc.add(Restrictions.ne("grade", 1));//大一数据不统计
		dc.add(Restrictions.ne("college", "A测试专用"));//测试专业不统计
		List<Bookamount> list = this.bookAmountDao.findByCriteria(dc);
		if (list == null || list.isEmpty()) {
			return 0;
		}
		int total = 0;
		for (Bookamount ba : list) {
			total += ba.getAmount();
		}
		return total;
	}

	public BookAmountDao getBookAmountDao() {
		return bookAmountDao;
	}

	public void setBookAmountDao(BookAmountDao bookAmountDao) {
		this.bookAmountDao = bookAmountDao;
	}

	public SysVarCache getSysVarCache() {
		return sysVarCache;
	}

	public void setSysVarCache(SysVarCache sysVarCache) {
		this.sysVarCache = sysVarCache;
	}
	
	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}
}
