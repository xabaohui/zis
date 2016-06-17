package com.zis.bookinfo.bo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.bean.BookinfoAid;
import com.zis.bookinfo.dao.BookinfoAidDao;
import com.zis.bookinfo.util.ConstantString;
import com.zis.common.util.TextClearUtils;
import com.zis.requirement.bean.Bookamount;
import com.zis.requirement.dao.BookAmountDao;

/**
 * 相似图书分析
 * 
 * @author yz
 * 
 */
public class SimilarityBookAnalysisBO extends BookInfoAnalysisBO {

	private BookinfoAidDao bookinfoAidDao;
	private BookAmountDao bookAmountDao;
	private List<Integer> booksInUse;

	@Override
	public void processOne(Bookinfo book) {
		if (book == null) {
			return;
		}
		// 获取图书名称（去除多余的符号和空格）
		String pureName = getPureName(book.getBookName());
		boolean isAppend = false;
		// 获取教材主分组
		String trunkKey = getTrunkKey(book);
		List<BookinfoAid> trunk = getTrunk(trunkKey);
		// 遍历主分组（trunk）下的所有分支（branch）
		for (BookinfoAid branch : trunk) {
			// 书名包含branchKey，该条记录添加到对应分支中
			if (pureName.contains(branch.getShortBookName())) {
				branch.setIds(branch.getIds() + "," + book.getId());
				branch.setTotalCount(branch.getTotalCount() + 1);
				this.bookinfoAidDao.update(branch);
				isAppend = true;
			}
			// branchKey包含书名，该条记录添加到对应分支中
			else if (branch.getShortBookName().contains(pureName)) {
				branch.setIds(branch.getIds() + "," + book.getId());
				branch.setTotalCount(branch.getTotalCount() + 1);
				if (isPureNameMoreShorter(branch.getShortBookName(), pureName,
						trunkKey)) {
					// ShortBookName不是最小子集，则更新最小子集
					branch.setShortBookName(pureName);
				}
				this.bookinfoAidDao.update(branch);
				isAppend = true;
			}
		}
		// 遍历完成之后，如果isAppend = false，说明没有找到对应的分支（branch），则需要添加新分支
		if (!isAppend) {
			BookinfoAid biAid = new BookinfoAid();
			biAid.setGroupKey(trunkKey);
			biAid.setShortBookName(pureName);
			biAid.setTotalCount(1);
			biAid.setIds(book.getId() + "");
			biAid.setCheckLevel(0);
			this.bookinfoAidDao.save(biAid);
		}
	}

	// 检查pureName是否是ShortBookName的子集
	private boolean isPureNameMoreShorter(String shortBookName,
			String pureName, String trunkKey) {
		if (shortBookName.length() <= pureName.length()) {
			return false;
		}
		// 仅当pureName是ShortBookName的子集并且没有 分支名为pureName的记录，才允许更新分支名称
		if (shortBookName.contains(pureName) && !shortBookName.equals(pureName)) {
			BookinfoAid example = new BookinfoAid();
			example.setGroupKey(trunkKey);
			example.setShortBookName(pureName);
			List<BookinfoAid> list = this.bookinfoAidDao.findByExample(example);
			if (list.isEmpty()) {
				return true;
			}
		}
		return false;
	}

	// 取得主分组
	private List<BookinfoAid> getTrunk(String trunkKey) {
		return this.bookinfoAidDao.findByGroupKey(trunkKey);
	}

	// 取得主分组key
	private String getTrunkKey(Bookinfo book) {
		String[] author = book.getBookAuthor().split(" ");
		return book.getBookPublisher() + "_" + author[0];
	}

	private String getPureName(String bookName) {
		bookName = TextClearUtils.clearSpecialChar(bookName);
		bookName = StringUtils.deleteWhitespace(bookName);
		return bookName;
	}

	/**
	 * 相似度分析完成后的工作: <br/>
	 * 1. 删除无相似图书的结果<br/>
	 * 2. 定义检查级别：<br/>
	 * 0 默认值（其他）<br/>
	 * 1 相同或相关图书（部分或全部记录的关联ID为空，表示有部分数据未完成关联操作）<br/>
	 * 2 存在相同条码（该分组下的记录中部分或全部ISBN相同，很可能是重复记录） <br/>
	 * 3 包含正在使用的教材
	 */
	public void afterAnalysis() {
		// 查出最大ID 和 最小ID
		DetachedCriteria dc = DetachedCriteria.forClass(BookinfoAid.class);
		ProjectionList pList = Projections.projectionList();
		pList.add(Projections.min("id")).add(Projections.max("id"));
		dc.setProjection(pList);
		List list = this.bookinfoAidDao.findByCriteria(dc);
		Object[] ids = (Object[]) list.get(0);
		Integer minId = (Integer) ids[0];
		Integer maxId = (Integer) ids[1];
		// 统计正在使用的ISBN
		initBooksInUse();
		// 逐条遍历，定义检查级别
		for (int i = minId; i <= maxId; i++) {
			BookinfoAid aid = this.bookinfoAidDao.findById(i);
			if (aid == null) {
				continue;
			}
			// 删除无相似图书的结果
			if (aid.getTotalCount() == 1) {
				this.bookinfoAidDao.delete(aid);
			} else {
				// 定义检查级别
				updateCheckLevel(aid);
			}
		}
	}

	private void initBooksInUse() {
		if (booksInUse == null) {
			DetachedCriteria dc = DetachedCriteria.forClass(Bookamount.class);
			dc.setProjection(Projections.distinct(Projections
					.property("bookId")));
			List list = this.bookAmountDao.findByCriteria(dc);
			booksInUse = new ArrayList<Integer>();
			for (Object record : list) {
				Integer bookId = (Integer) record;
				booksInUse.add(bookId);
			}
		}

	}

	private void updateCheckLevel(BookinfoAid aid) {
		String[] ids = aid.getIds().split(",");
		Set<String> isbnSet = new HashSet<String>();
		Integer totalCount = aid.getTotalCount();
		boolean isUsed = false;
		boolean notRelated = false;
		for (String id : ids) {
			try {
				Integer bookId = Integer.parseInt(id.trim());
				Bookinfo book = bookinfoDao.findById(bookId);
				// 不存在的记录不计
				if (book == null) {
					totalCount--;
					continue;
				}
				// 废弃的记录不计
				if (ConstantString.ABANDON.equals(book.getBookStatus())) {
					totalCount--;
					continue;
				}
				// isbn添加到set中（自动去除重复项），如果最后totalCount != isbnSet.size()，则说明有条码重复的记录
				isbnSet.add(book.getIsbn());
				// 包含正在使用的图书，检查级别为3
				if (booksInUse.contains(bookId)) {
					isUsed = true;
				}
				if (StringUtils.isBlank(book.getGroupId()) &&StringUtils.isBlank(book.getRelateId())) {
					// 部分或全部记录的关联ID为空，表示有部分数据未完成关联操作，检查级别为1
					notRelated = true;
				}
			} catch (NumberFormatException e) {
				logger.error("数据错误，bookinfoAid.ids=" + aid.getIds());
				continue;
			}
		}
		Integer checkLevel = 0;// 检查级别默认为0
		// 已使用过的图书
		if(isUsed) {
			checkLevel = 3;
		}
		// 条形码数量与记录数不同，则说明存在相同条码，检查级别为2
		else if(isbnSet.size() != totalCount) {
			checkLevel = 2;
		}
		// 未完成关联操作
		else if(notRelated) {
			checkLevel = 1;
		}
		// 剩余记录
		else {
			checkLevel = 0;
		}
		aid.setCheckLevel(checkLevel);
		try {
			this.bookinfoAidDao.update(aid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BookinfoAidDao getBookinfoAidDao() {
		return bookinfoAidDao;
	}

	public void setBookinfoAidDao(BookinfoAidDao bookinfoAidDao) {
		this.bookinfoAidDao = bookinfoAidDao;
	}

	public BookAmountDao getBookAmountDao() {
		return bookAmountDao;
	}

	public void setBookAmountDao(BookAmountDao bookAmountDao) {
		this.bookAmountDao = bookAmountDao;
	}
}
