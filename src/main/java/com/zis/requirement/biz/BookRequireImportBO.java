package com.zis.requirement.biz;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.service.BookService;
import com.zis.common.util.ZisUtils;
import com.zis.requirement.bean.BookRequireImportTask;
import com.zis.requirement.bean.BookRequireImportDetail;
import com.zis.requirement.bean.BookRequireImportDetailStatus;
import com.zis.requirement.bean.BookRequireImportTaskStatus;
import com.zis.requirement.dao.BookRequireImportTaskDao;
import com.zis.requirement.dao.BookRequireImportDetailDao;
import com.zis.requirement.dto.BookRequireUploadDTO;

/**
 * 导入书单相关业务逻辑
 * 
 * @author yz
 * 
 */
public class BookRequireImportBO {

	private BookRequireImportTaskDao bookRequireImportTaskDao;
	private BookRequireImportDetailDao bookRequireImportDetailDao;
	private BookService bookService;

	/**
	 * 导入书单，保存到临时表中
	 * 
	 * @param list
	 */
	public void saveTempBookRequireImportDetails(
			List<BookRequireUploadDTO> list, String college, String operator, String memo) {
		if (list == null || list.isEmpty()) {
			throw new RuntimeException("导入失败，list不能为空");
		}
		if (StringUtils.isBlank(college)) {
			throw new RuntimeException("导入失败，学校不能为空");
		}
		if (StringUtils.isBlank(operator)) {
			throw new RuntimeException("导入失败，操作员不能为空");
		}
		if (StringUtils.isBlank(memo)) {
			throw new RuntimeException("导入失败，操作备注不能为空");
		}
		// 新增TempBookRequireImport记录
		BookRequireImportTask record = saveTempBookRequireImport(operator, college, memo, list.size());
		// 批量保存明细
		List<BookRequireImportDetail> detailList = new ArrayList<BookRequireImportDetail>();
		for (BookRequireUploadDTO dto : list) {
			BookRequireImportDetail detail = new BookRequireImportDetail();
			BeanUtils.copyProperties(dto, detail);
			detail.setBatchId(record.getId());
			detail.setStatus(BookRequireImportDetailStatus.BOOK_NOT_MATCHED);
			detail.setGmtCreate(ZisUtils.getTS());
			detail.setGmtModify(ZisUtils.getTS());
			detail.setVersion(0);
			this.bookRequireImportDetailDao.save(detail);
			detailList.add(detail);
		}
		// 匹配图书
		doMatchBook(detailList);
		// 匹配专业
//		doMatchDepartment(record.getId());
	}
	
	private BookRequireImportTask saveTempBookRequireImport(String operator,
			String college, String memo, int totalCount) {
		BookRequireImportTask record = new BookRequireImportTask();
		record.setCollege(college);
		record.setMemo(memo);
		record.setOperator(operator);
		record.setStatus(BookRequireImportTaskStatus.NOT_MATCHED);
		record.setTotalCount(totalCount);
		record.setGmtCreate(ZisUtils.getTS());
		record.setGmtModify(ZisUtils.getTS());
		record.setVersion(0);
		this.bookRequireImportTaskDao.save(record);
		return record;
	}
	
	/**
	 * 尝试匹配一个批次下未匹配成功的图书
	 * @param batchId
	 */
	public void doMatchBook(Integer batchId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BookRequireImportDetail.class);
		criteria.add(Restrictions.eq("batchId", batchId));
		criteria.add(Restrictions.eq("status", BookRequireImportDetailStatus.BOOK_NOT_MATCHED));
		List<BookRequireImportDetail> list = this.bookRequireImportDetailDao.findByCriteria(criteria);
		if(list == null || list.isEmpty()) {
			return;
		}
		this.doMatchBook(list);
	}

	private void doMatchBook(List<BookRequireImportDetail> detailList) {
		for (BookRequireImportDetail detail : detailList) {
			// 尝试匹配图书：书单中的书名、作者、版次、出版社与系统里的记录完全一致
			// 1. 书单中的书名、作者、版次、出版社为空，匹配失败
			// 2. 满足上述条件，系统中的记录仅有一条，匹配成功；否则匹配失败
			if (StringUtils.isBlank(detail.getBookName())
					|| StringUtils.isBlank(detail.getBookAuthor())
					|| StringUtils.isBlank(detail.getBookEdition())
					|| StringUtils.isBlank(detail.getBookPublisher())) {
				continue;
			}
			DetachedCriteria criteria = DetachedCriteria.forClass(Bookinfo.class);
			criteria.add(Restrictions.eq("bookName", detail.getBookName()));
			criteria.add(Restrictions.eq("bookAuthor", detail.getBookAuthor()));
			criteria.add(Restrictions.eq("bookEdition", detail.getBookEdition()));
			criteria.add(Restrictions.eq("bookPublisher", detail.getBookPublisher()));
			if (StringUtils.isNotBlank(detail.getIsbn())) {
				criteria.add(Restrictions.eq("isbn", detail.getIsbn()));
			}
			List<Bookinfo> bookList = this.bookService.findBookByCriteria(criteria);
			if(bookList != null && bookList.size() == 1) {
				doMatchBook(detail, bookList.get(0).getId());
			}
		}
	}
	
	/**
	 * 匹配图书
	 * @param detail
	 * @param bookId
	 */
	public void doMatchBook(BookRequireImportDetail detail, Integer bookId) {
		detail.setBookid(bookId);
		detail.setStatus(BookRequireImportDetailStatus.DEPARTMENT_NOT_MATCHED);
		detail.setGmtModify(ZisUtils.getTS());
		detail.setVersion(detail.getVersion()+1);
		this.bookRequireImportDetailDao.update(detail);
	}

	public void setBookRequireImportDetailDao(
			BookRequireImportDetailDao bookRequireImportDetailDao) {
		this.bookRequireImportDetailDao = bookRequireImportDetailDao;
	}

	public void setBookRequireImportTaskDao(
			BookRequireImportTaskDao bookRequireImportTaskDao) {
		this.bookRequireImportTaskDao = bookRequireImportTaskDao;
	}
	
	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}
}
