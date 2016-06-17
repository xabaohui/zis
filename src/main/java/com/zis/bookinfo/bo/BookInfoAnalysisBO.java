package com.zis.bookinfo.bo;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.dao.BookinfoDao;
import com.zis.bookinfo.util.ConstantString;

/**
 * 图书信息分析抽象类
 * 
 * @author yz
 * 
 */
public abstract class BookInfoAnalysisBO {

	protected BookinfoDao bookinfoDao;
	protected Logger logger = Logger.getLogger(BookInfoAnalysisBO.class);

	/**
	 * 分析整个表格中的数据
	 */
	public void analysis() {
		Integer maxId = getMaxBookId();
		// 按照ID顺序遍历整个bookinfo表
		for (int i = 1; i <= maxId; i++) {
			Bookinfo book = this.bookinfoDao.findById(i);
			// 只处理状态不为“废弃”的记录
			if (book != null
					&& !book.getBookStatus().equals(ConstantString.ABANDON)) {
				try {
					processOne(book);
				} catch (Exception e) {
					logger.error("处理图书信息过程出错，" + e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * 处理某一条特定记录
	 * @param book
	 */
	public abstract void processOne(Bookinfo book);

	private Integer getMaxBookId() {
		// 查出最大ID 和 最小ID
		DetachedCriteria dc = DetachedCriteria.forClass(Bookinfo.class);
		ProjectionList pList = Projections.projectionList();
		pList.add(Projections.min("id")).add(Projections.max("id"));
		dc.setProjection(pList);
		List list = this.bookinfoDao.findByCriteria(dc);
		Object[] ids = (Object[]) list.get(0);
		Integer minId = (Integer) ids[0];
		Integer maxId = (Integer) ids[1];
		return maxId;
	}

	public BookinfoDao getBookinfoDao() {
		return bookinfoDao;
	}

	public void setBookinfoDao(BookinfoDao bookinfoDao) {
		this.bookinfoDao = bookinfoDao;
	}
}
