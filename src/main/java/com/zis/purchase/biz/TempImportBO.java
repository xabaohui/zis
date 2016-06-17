package com.zis.purchase.biz;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.service.BookService;
import com.zis.bookinfo.util.ConstantString;
import com.zis.common.util.Page;
import com.zis.common.util.PaginationQueryUtil;
import com.zis.common.util.ZisUtils;
import com.zis.purchase.bean.TempImportDetail;
import com.zis.purchase.bean.TempImportDetailStatus;
import com.zis.purchase.bean.TempImportTask;
import com.zis.purchase.bean.TempImportTaskStatus;
import com.zis.purchase.dao.TempImportDetailDao;
import com.zis.purchase.dao.TempImportTaskDao;
import com.zis.purchase.dto.TempImportDTO;
import com.zis.purchase.dto.TempImportDetailView;

/**
 * 临时导入记录处理BO
 * 
 * @author yz
 * 
 */
public class TempImportBO {

	private TempImportTaskDao tempImportTaskDao;
	private TempImportDetailDao tempImportDetailDao;
	private BookService bookService;

	private static final Logger logger = Logger.getLogger(TempImportBO.class);

	/**
	 * 添加库存或者采购记录到临时表
	 */
	public void addTempImportTask(List<TempImportDTO> list,
			String tempImportTaskBizType, String memo) {
		TempImportTask task = new TempImportTask();
		task.setBizType(tempImportTaskBizType);
		task.setMemo(memo);
		task.setStatus(TempImportTaskStatus.IMPORT_COMPLETE);
		task.setTotalCount(list.size());
		task.setGmtCreate(ZisUtils.getTS());
		task.setGmtModify(ZisUtils.getTS());
		task.setVersion(0);
		this.tempImportTaskDao.save(task);
		for (TempImportDTO tmp : list) {
			TempImportDetail detail = new TempImportDetail();
			detail.setAmount(tmp.getAmount());
			detail.setTaskId(task.getId());
			detail.setOrigIsbn(tmp.getIsbn());
			detail.setStatus(TempImportDetailStatus.NOT_MATCHED);
			detail.setAdditionalInfo(tmp.getAdditionalInfo());
			detail.setGmtCreate(ZisUtils.getTS());
			detail.setGmtModify(ZisUtils.getTS());
			detail.setVersion(0);
			this.tempImportDetailDao.save(detail);
		}
		logger.info("save tempImportTask, id=" + task.getId());
		// 批量关联到bookinfo
		this.updateTempImportDetail(task.getId());
	}

	/**
	 * 批量处理临时表中的库存或者采购记录，使之与BookInfo关联
	 */
	public void updateTempImportDetail(Integer taskId) {
		// 1. 查找出未匹配图书的记录
		TempImportDetail example = new TempImportDetail();
		example.setTaskId(taskId);
		example.setStatus(TempImportDetailStatus.NOT_MATCHED);
		List<TempImportDetail> list = this.tempImportDetailDao
				.findByExample(example);
		for (TempImportDetail record : list) {
			// 2. 如果isbn = null，则先填写isbn
			if (StringUtils.isBlank(record.getIsbn())) {
				// 2.1 isbn=null且origIsbn包含分隔符，则检查分隔符后面的bookId是否和系统匹配
				final String SPLIT = "-";
				if (record.getOrigIsbn().contains(SPLIT)) {
					String[] part = record.getOrigIsbn().split(SPLIT);
					String exactIsbn = part[0];
					Bookinfo book = this.bookService.findBookById(Integer
							.valueOf(part[1]));
					// 2.1.1 分隔符后面的bookId对应的图书存在、状态合法，且ISBN相等，匹配成功
					if (book != null
							&& book.getBookStatus().equals(
									ConstantString.USEFUL)
							&& book.getIsbn().equals(exactIsbn)) {
						record.setBookId(book.getId());
						record.setStatus(TempImportDetailStatus.MATCHED);
						logger.info("tempImportDetail associate with bookInfo, detailId="
								+ record.getId());
					}
					// 2.1.2 匹配失败，仅更新isbn
					record.setIsbn(exactIsbn);
					record.setGmtModify(ZisUtils.getTS());
					record.setVersion(record.getVersion() + 1);
					this.tempImportDetailDao.save(record);
					continue; // 处理完成，跳过后续流程
				}
				// 2.2 如果origIsbn不包含分隔符，则isbn=origIsbn，处理流程同3
				record.setIsbn(record.getOrigIsbn());
			}
			// 3. 使用isbn进行匹配（如果isbn!=null或者isbn=origIsbn）
			List<Bookinfo> bookList = this.bookService.findBookByISBN(record
					.getIsbn());
			// 3.1 当且仅当isbn只有一条记录，匹配成功
			if (bookList != null && bookList.size() == 1) {
				record.setBookId(bookList.get(0).getId());
				record.setStatus(TempImportDetailStatus.MATCHED);
				record.setVersion(record.getVersion() + 1);
				record.setGmtModify(ZisUtils.getTS());
				this.tempImportDetailDao.save(record);
				logger.info("tempImportDetail associate with bookInfo, detailId="
						+ record.getId());
			}
		}
		// 如果本次待处理的全部搞定，则修改TaskStatus
		updateTaskIfFullyMatched(taskId);
	}

	// 如果本次待处理的全部搞定，则修改TaskStatus
	private void updateTaskIfFullyMatched(Integer taskId) {
		TempImportDetail example = new TempImportDetail();
		example.setTaskId(taskId);
		example.setStatus(TempImportDetailStatus.NOT_MATCHED);
		List<TempImportDetail> notMatched = this.tempImportDetailDao
				.findByExample(example);
		if (notMatched == null || notMatched.isEmpty()) {
			TempImportTask task = this.tempImportTaskDao.findById(taskId);
			if (task == null) {
				throw new RuntimeException(
						"TempImportTask could not be null, taskId=" + taskId);
			}
			task.setStatus(TempImportTaskStatus.FULLY_MATCHED);
			task.setGmtModify(ZisUtils.getTS());
			task.setVersion(task.getVersion() + 1);
			this.tempImportTaskDao.save(task);
			logger.info("tempImportTask fully matched, taskId=" + taskId);
		}
	}

	/**
	 * 将临时表中的库存或者采购记录与BookInfo关联
	 * 
	 * @param purchaseTempRecordId
	 * @param bookId
	 * @return
	 */
	public String updateAssociateTempImportDetailWithBookInfo(
			Integer tempImportDetailId, Integer bookId) {
		TempImportDetail tmpRecord = this.tempImportDetailDao
				.findById(tempImportDetailId);
		Bookinfo book = this.bookService.findBookById(bookId);
		return this.updateAssociatePurchaseTempImportWithBookInfo(tmpRecord,
				book);
	}

	/**
	 * 将临时表中的库存或者采购记录与BookInfo关联
	 * 
	 * @param purchaseTempRecordId
	 * @param bookId
	 * @return 失败原因，成功返回null
	 */
	public String updateAssociatePurchaseTempImportWithBookInfo(
			TempImportDetail detail, Bookinfo book) {
		if (detail == null) {
			return "数据错误，PurchaseTempImport不能为空";
		}
		if (book == null) {
			return "数据错误，book不能为空";
		}
		if (!detail.getStatus().equals(TempImportDetailStatus.NOT_MATCHED)) {
			return "数据错误，进行关联的PurchaseTempImport状态必须是NOT_MATCHED, id="
					+ detail.getId();
		}
		if (!book.getBookStatus().equals(ConstantString.USEFUL)) {
			return "数据错误，图书状态必须是 正式, bookId=" + book.getId();
		}
		detail.setIsbn(book.getIsbn());
		detail.setBookId(book.getId());
		detail.setStatus(TempImportDetailStatus.MATCHED);
		detail.setGmtModify(ZisUtils.getTS());
		detail.setVersion(detail.getVersion() + 1);
		this.tempImportDetailDao.save(detail);
		logger.info("tempImportDetail associate with bookInfo, detailId="
				+ detail.getId());
		// 如果本次待处理的全部搞定，则修改TaskStatus
		updateTaskIfFullyMatched(detail.getTaskId());
		return null;
	}

	/**
	 * 查找TempImportTask
	 * 
	 * @param taskId
	 * @return
	 */
	public TempImportTask findTempImportTaskByTaskId(Integer taskId) {
		return this.tempImportTaskDao.findById(taskId);
	}

	/**
	 * 列出特定批次下特定状态的临时记录，用于页面展示或者导出
	 * 
	 * @param batchId
	 * @param tempImportDetailStatus
	 * @return
	 */
	public List<TempImportDetailView> findTempImportDetail(
			Integer taskId, String tempImportDetailStatus) {
		if (taskId == null || StringUtils.isBlank(tempImportDetailStatus)) {
			throw new RuntimeException("illegal arguments, for input null");
		}
		// 查找记录
		TempImportDetail example = new TempImportDetail();
		example.setTaskId(taskId);
		example.setStatus(tempImportDetailStatus);
		List<TempImportDetail> list = this.tempImportDetailDao
				.findByExample(example);
		// 转换结果
		List<TempImportDetailView> resultList = new ArrayList<TempImportDetailView>();
		for (TempImportDetail detail : list) {
			TempImportDetailView view = new TempImportDetailView();
			BeanUtils.copyProperties(detail, view);
			// 未匹配成功的，查找出可能相关的记录
			if (detail.getStatus().equals(TempImportDetailStatus.NOT_MATCHED)) {
				List<Bookinfo> relatedBooks = this.bookService
						.findBookByISBN(detail.getOrigIsbn());
				view.setRelatedBooks(relatedBooks);
				view.setIsbn(detail.getOrigIsbn());
			}
			// 匹配成功的，查找出匹配的记录
			else if (detail.getStatus().equals(TempImportDetailStatus.MATCHED)) {
				Bookinfo book = this.bookService.findBookById(detail
						.getBookId());
				if (book == null) {
					throw new RuntimeException("图书记录不存在,bookId="
							+ detail.getBookId());
				}
				view.setAssociateBook(book);
			} else {
				throw new RuntimeException("临时导入记录状态不正确, id=" + detail.getId());
			}
			resultList.add(view);
		}
		return resultList;
	}
	
	public List<TempImportDetail> findTempImportDetailByCritera(DetachedCriteria criteria) {
		return this.tempImportDetailDao.findByCriteria(criteria);
	}

	/**
	 * 批量更新临时导入记录，使之成功或者失效
	 * 
	 * @param taskId
	 * @return
	 */
	public void updateTempImportTaskStatus(Integer taskId,
			Integer tempImportTaskStatus) {
		if (taskId == null) {
			throw new RuntimeException("参数taskId不能为空");
		}
		TempImportTask task = this.tempImportTaskDao.findById(taskId);
		if (task == null) {
			throw new RuntimeException("tempImportTask不能为空,taskId=" + taskId);
		}
		// 更新到操作成功
		if (TempImportTaskStatus.SUCCESS.equals(tempImportTaskStatus)) {
			// task当前状态必须是FULLY_MATCHED
			if (task.getStatus() != TempImportTaskStatus.FULLY_MATCHED) {
				throw new RuntimeException(
						"更新TempImportTaskStatus失败，SUCCESS的前提必须是FULLY_MATCHED, taskId="
								+ taskId);
			}
		}
		// 更新到取消操作
		else if (TempImportTaskStatus.CANCEL == tempImportTaskStatus) {
			// task当前状态必须是FULLY_MATCHED或者IMPORT_COMPLETE
			if (task.getStatus() != TempImportTaskStatus.FULLY_MATCHED
					&& task.getStatus() != TempImportTaskStatus.IMPORT_COMPLETE) {
				throw new RuntimeException(
						"更新TempImportTaskStatus失败，CANCEL的前提必须是FULLY_MATCHED或者IMPORT_COMPLETE, taskId="
								+ taskId);
			}
		}
		task.setStatus(tempImportTaskStatus);
		task.setGmtModify(ZisUtils.getTS());
		task.setVersion(task.getVersion() + 1);
		this.tempImportTaskDao.save(task);
	}

	public void setTempImportDetailDao(TempImportDetailDao tempImportDetailDao) {
		this.tempImportDetailDao = tempImportDetailDao;
	}

	public void setTempImportTaskDao(TempImportTaskDao tempImportTaskDao) {
		this.tempImportTaskDao = tempImportTaskDao;
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}

	/**
	 * 查出所有有效状态的Task
	 * 
	 * @return
	 */
	public List<TempImportTask> findAllTempImportTask() {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(TempImportTask.class);
		criteria.add(Restrictions.ne("status", TempImportTaskStatus.CANCEL));
		criteria.addOrder(Order.asc("status").desc("gmtCreate"));
		return this.tempImportTaskDao.findByCriteria(criteria);
	}
}
