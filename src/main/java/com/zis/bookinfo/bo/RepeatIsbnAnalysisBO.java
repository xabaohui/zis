package com.zis.bookinfo.bo;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.util.ConstantString;
import com.zis.common.util.ZisUtils;

/**
 * 一码多书分析器
 * @author yz
 *
 */
public class RepeatIsbnAnalysisBO extends BookInfoAnalysisBO {

	@Override
	public void processOne(Bookinfo book) {
		DetachedCriteria dc = DetachedCriteria.forClass(Bookinfo.class);
		dc.add(Restrictions.eq("isbn", book.getIsbn()));
		dc.add(Restrictions.ne("bookStatus", ConstantString.ABANDON));
		List<Bookinfo> list = bookinfoDao.findByCriteria(dc);
		if(list == null || list.isEmpty()) {
			return;
		}
		// 如果只有一条记录并且标记为一码多书，则取消该标记
		if (list.size() == 1) {
			Bookinfo bi = list.get(0);
			if (bi.getRepeatIsbn() != null && bi.getRepeatIsbn()) {
				bi.setRepeatIsbn(false);
				bi.setGmtModify(ZisUtils.getTS());
				bi.setVersion(bi.getVersion() + 1);
				bookinfoDao.update(bi);
			}
		}
		// 多条记录，一码多书标记一下
		else {
			for (Bookinfo bi : list) {
				if (bi.getRepeatIsbn() != null && !bi.getRepeatIsbn()) {
					bi.setRepeatIsbn(true);
					bi.setGmtModify(ZisUtils.getTS());
					bi.setVersion(bi.getVersion() + 1);
					bookinfoDao.update(bi);
				}
			}
			logger.info("successfuly process one ISBN reflect to multiple books, isbn="
					+ book.getIsbn());
		}
	}
}