package com.zis.requirement.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.bean.BookinfoStatus;
import com.zis.bookinfo.dao.BookinfoDao;
import com.zis.common.util.ZisUtils;
import com.zis.requirement.bean.Bookamount;
import com.zis.requirement.bean.Departmentinfo;
import com.zis.requirement.dao.BookAmountDao;
import com.zis.requirement.dao.DepartmentInfoDao;
import com.zis.requirement.dto.AddBookToDepartmentResult;
import com.zis.requirement.dto.BookAmountAddApiRequestDTO;
import com.zis.requirement.dto.RequirementCollectScheduleDTO;

public class BookAmountService {

	private static Logger logger = Logger.getLogger(BookAmountService.class);

	private BookAmountDao bookAmountDao;
	private BookinfoDao bookinfoDao;
	private DepartmentInfoDao departmentInfoDao;
	
//	private BookRequireImportBO bookRequireImportBO;

	/**
	 * 添加教材使用量
	 * 
	 * @param ba
	 * @throws RuntimeException
	 *             如果出现重复记录，会抛出异常。注意：BookId、专业、年纪、学期完全相同，才可定义为重复
	 */
	public void saveBookAmount(Bookamount ba) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Bookamount.class);
		criteria.add(Restrictions.eq("bookId", ba.getBookId()));
		criteria.add(Restrictions.eq("partId", ba.getPartId()));
		criteria.add(Restrictions.eq("grade", ba.getGrade()));
		criteria.add(Restrictions.eq("term", ba.getTerm()));
		List<Bookamount> list = this.bookAmountDao.findByCriteria(criteria);
//		Bookamount ex = new Bookamount();
//		ex.setBookId(ba.getBookId());
//		ex.setPartId(ba.getPartId());
//		ex.setGrade(ba.getGrade());
//		ex.setTerm(ba.getTerm());
//		List<Bookamount> list = bookAmountDao.findByExample(ex);
		if (list != null && !list.isEmpty()) {
			Bookinfo book = this.bookinfoDao.findById(ba.getBookId());
			throw new RuntimeException("专业" + ba.getPartId() + "已经登记过图书:"
					+ book.getBookName() + ", bookId=" + ba.getBookId());
		}
		bookAmountDao.save(ba);
	}

	/**
	 * 将书存入session中,返回书本信息
	 * 
	 * @param bookId
	 * @param bookName
	 * @param ISBN
	 * @param bookPublisher
	 * @param bookAuthor
	 * @param version
	 * @param session
	 * @return
	 */
	public AddBookToDepartmentResult addSelectedBookToDwrSession(Integer bookId, Integer departId, Integer grade, Integer term, HttpSession session) {
		if(bookId == null) {
			return new AddBookToDepartmentResult(false, "图书ID不能为空");
		}
		if(departId == null || departId <= 0) {
			return new AddBookToDepartmentResult(false, "专业必须选择");
		}
		if(grade == null || grade <= 0) {
			return new AddBookToDepartmentResult(false, "年级不能为空");
		}
		if(term == null || term <= 0) {
			return new AddBookToDepartmentResult(false, "学期不能为空");
		}
		// 从session中取得bookMap，如果没有，则创建
		@SuppressWarnings("unchecked")
		Map<Integer, Bookinfo> bookMap = (Map<Integer, Bookinfo>) session.getAttribute("BookMap");
		if (bookMap == null) {
			bookMap = new HashMap<Integer, Bookinfo>();
			session.setAttribute("BookMap", bookMap);
		}
		// 检查图书状态
		Bookinfo bi = this.bookinfoDao.findById(bookId);
		if(bi == null || BookinfoStatus.DISCARD.equals(bi.getBookStatus())) {
			return new AddBookToDepartmentResult(false, "图书不存在或者已删除");
		}
		// 检查该图书是否已添加
		DetachedCriteria criteria = DetachedCriteria.forClass(Bookamount.class);
		criteria.add(Restrictions.eq("bookId", bookId));
		criteria.add(Restrictions.eq("partId", departId));
		criteria.add(Restrictions.eq("grade", grade));
		criteria.add(Restrictions.eq("term", term));
		List<Bookamount> baList = this.bookAmountDao.findByCriteria(criteria);
		if(baList != null && !baList.isEmpty()) {
			return new AddBookToDepartmentResult(false, "该专业已使用该图书，无需再次录入");
		}
		// 将书添加到bookMap中
		bookMap.put(bookId, bi);
		// 组装结果
		return buildSuccessAddBookToDepartmentResult(bookMap);
	}
	
	/**
	 * 从session中移除已添加的图书记录
	 * @param bookId
	 * @param session
	 * @return
	 */
	public AddBookToDepartmentResult removeSelectedBookToDwrSession(Integer bookId, HttpSession session) {
		// 从session中取得bookMap，如果没有，直接返回
		@SuppressWarnings("unchecked")
		Map<Integer, Bookinfo> bookMap = (Map<Integer, Bookinfo>) session.getAttribute("BookMap");
		if (bookMap == null) {
			return new AddBookToDepartmentResult(false, "系统错误：session中没有已保存的图书记录");
		}
		// 从bookMap中移除记录
		if(bookMap.containsKey(bookId)) {
			bookMap.remove(bookId);
		}
		// 组装结果
		return buildSuccessAddBookToDepartmentResult(bookMap);
	}
	
	private AddBookToDepartmentResult buildSuccessAddBookToDepartmentResult(Map<Integer, Bookinfo> bookMap) {
		AddBookToDepartmentResult result = new AddBookToDepartmentResult(true, "");
		for (Bookinfo book : bookMap.values()) {
			result.add(book);
		}
		return result;
	}

	/**
	 * 添加教材使用量
	 * 
	 * @param requestDTO
	 */
	public void addBookAmount(BookAmountAddApiRequestDTO requestDTO) {
		Departmentinfo di = departmentInfoDao
				.findById(requestDTO.getDepartId());
		for (Integer bookId : requestDTO.getBookIdList()) {
			Bookamount ba = new Bookamount();
			BeanUtils.copyProperties(requestDTO, ba);
			
			ba.setBookId(bookId);
			ba.setCollege(di.getCollege());
			ba.setInstitute(di.getInstitute());
			ba.setPartName(di.getPartName());
			ba.setPartId(di.getDid());
			Bookinfo bi = bookinfoDao.findById(bookId);
			if (bi == null) {
				throw new RuntimeException("bookInfo is null, for id=" + bookId);
			}
			BeanUtils.copyProperties(bi, ba);
			ba.setId(null);//copyProperties会把ID复制过来
			ba.setGmtCreate(ZisUtils.getTS());
			ba.setGmtModify(ZisUtils.getTS());
			ba.setVersion(0);
			this.saveBookAmount(ba);
		}
	}

	/**
	 * 批量保存教材使用量
	 * 
	 * @param list
	 */
	public void saveBookAmountList(List<Bookamount> list) {
		for (Bookamount bookamount : list) {
			this.saveBookAmount(bookamount);
		}
	}

	/**
	 * 查询图书需求数据收集进度
	 * 
	 * @return
	 */
	public List<RequirementCollectScheduleDTO> findRequirementCollectSchedule(
			boolean groupByOperator) {
		// 查询所有采集过数据的 学校
		DetachedCriteria dc = DetachedCriteria.forClass(Bookamount.class);
		dc.setProjection(Projections.distinct(Projections.property("college")));
		List collegeList = this.bookAmountDao.findByCriteria(dc);
		// 列出数据涉及到的专业
		DetachedCriteria dcDepartmentInfo = DetachedCriteria
				.forClass(Departmentinfo.class);
		dcDepartmentInfo.add(Restrictions.in("college", collegeList));
		dcDepartmentInfo.addOrder(Order.asc("college"))
				.addOrder(Order.asc("institute"))
				.addOrder(Order.asc("partName"));
		List<Departmentinfo> departmentInfos = this.departmentInfoDao
				.findByCriteria(dcDepartmentInfo);
		// 将所有专业、学年、学期记录下来，供采集信息查漏用
		List<String> departNotDeal = new ArrayList<String>();
		for (Departmentinfo depart : departmentInfos) {
			for (int grade = 1; grade <= depart.getYears(); grade++) {
				departNotDeal.add(getRequirementCollectKey(depart.getDid(),
						grade, 1));
				departNotDeal.add(getRequirementCollectKey(depart.getDid(),
						grade, 2));
			}
		}

		// 对已采集的数据归类汇总
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.rowCount());
		projectionList.add(Projections.groupProperty("college"));
		projectionList.add(Projections.groupProperty("institute"));
		projectionList.add(Projections.groupProperty("partName"));
		projectionList.add(Projections.groupProperty("partId"));
		projectionList.add(Projections.groupProperty("grade"));
		projectionList.add(Projections.groupProperty("term"));
		if (groupByOperator) {
			projectionList.add(Projections.groupProperty("operator"));
		}
		DetachedCriteria dcGetGroupCount = DetachedCriteria
				.forClass(Bookamount.class);
		dcGetGroupCount.setProjection(projectionList);
		List countList = this.bookAmountDao.findByCriteria(dcGetGroupCount);

		// 遍历统计出的数据
		List<RequirementCollectScheduleDTO> resultList = new ArrayList<RequirementCollectScheduleDTO>();
		for (Object record : countList) {
			Object[] dataArr = (Object[]) record;
			RequirementCollectScheduleDTO collectDTO = new RequirementCollectScheduleDTO();
			collectDTO.setCount(Integer.valueOf(dataArr[0].toString()));
			collectDTO.setCollege(dataArr[1].toString());
			collectDTO.setInstitute(dataArr[2].toString());
			collectDTO.setPartName(dataArr[3].toString());
			collectDTO.setDepartId(Integer.valueOf(dataArr[4].toString()));
			collectDTO.setGrade(Integer.valueOf(dataArr[5].toString()));
			collectDTO.setTerm(Integer.valueOf(dataArr[6].toString()));
			if (groupByOperator) {
				collectDTO.setOperator(dataArr[7].toString());
			}
			// 数据添加到结果集中
			resultList.add(collectDTO);
			// 把这个专业和学期的记录从departNotDeal中移除
			String key = getRequirementCollectKey(collectDTO.getDepartId(),
					collectDTO.getGrade(), collectDTO.getTerm());
			if (departNotDeal.contains(key)) {
				departNotDeal.remove(key);
			}
		}
		
		// 经过上一轮处理，departNotDeal中的记录都是未处理的，这些记录追加到结果集最后
		for (String notDeal : departNotDeal) {
			String[] elem = notDeal.split("_");
			Departmentinfo di = this.departmentInfoDao.findById(Integer
					.valueOf(elem[0]));
			RequirementCollectScheduleDTO collectDTO = new RequirementCollectScheduleDTO();
			BeanUtils.copyProperties(di, collectDTO);
			collectDTO.setDepartId(di.getDid());
			collectDTO.setGrade(Integer.valueOf(elem[1]));
			collectDTO.setTerm(Integer.valueOf(elem[2]));
			collectDTO.setOperator("未操作");
			collectDTO.setCount(0);
			resultList.add(collectDTO);
		}
		return resultList;
	}

	private String getRequirementCollectKey(Integer departId, Integer grade,
			Integer term) {
		return departId + "_" + grade + "_" + term;
	}

	public List<Bookamount> findBookAmountByBookId(Integer bookId) {
		return this.bookAmountDao.findByBookId(bookId);
	}
	
	/**
	 * 把图书使用量从一个id迁移到另一个id
	 * @param bookIdFrom
	 * @param bookIdTo
	 * @return
	 */
	public String updateForImmigrateBookRequirement(Integer bookIdFrom, Integer bookIdTo) {
		if(bookIdFrom.equals(bookIdTo)) {
			return "迁移后的对象与原对象相同";
		}
		List<Bookamount> list = this.bookAmountDao.findByBookId(bookIdFrom);
		if(list == null || list.isEmpty()) {
			return "没有查到"+bookIdFrom+"对应的记录";
		}
		Bookinfo bookFrom = this.bookinfoDao.findById(bookIdFrom);
		Bookinfo bookTo = this.bookinfoDao.findById(bookIdTo);
		if(bookFrom == null || bookTo == null) {
			return "图书对象不存在，请检查输入";
		}
		if(!bookFrom.getIsbn().equals(bookTo.getIsbn())) {
			return "图书不相符，迁移失败";
		}
		for (Bookamount record : list) {
			record.setBookId(bookIdTo);
			record.setGmtModify(ZisUtils.getTS());
			record.setVersion(record.getVersion() + 1);
			this.bookAmountDao.save(record);
		}
		return null;
	}
	
//	/**
//	 * 导入书单，保存到临时表中
//	 * @param list
//	 */
//	public void saveTempBookRequireImportDetails(List<BookRequireUploadDTO> list, String college, String operator, String memo) {
//		this.bookRequireImportBO.saveTempBookRequireImportDetails(list, college, operator, memo);
//	}

	public void setBookAmountDao(BookAmountDao bookAmountDao) {
		this.bookAmountDao = bookAmountDao;
	}

	public void setBookinfoDao(BookinfoDao bookinfoDao) {
		this.bookinfoDao = bookinfoDao;
	}

	public void setDepartmentInfoDao(DepartmentInfoDao departmentInfoDao) {
		this.departmentInfoDao = departmentInfoDao;
	}
	
//	public void setBookRequireImportBO(BookRequireImportBO bookRequireImportBO) {
//		this.bookRequireImportBO = bookRequireImportBO;
//	}
}
