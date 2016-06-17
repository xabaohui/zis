package com.zis.requirement.biz;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.beans.BeanUtils;

import com.zis.requirement.bean.Bookamount;
import com.zis.requirement.bean.Departmentinfo;
import com.zis.requirement.dao.BookAmountDao;
import com.zis.requirement.dao.DepartmentInfoDao;

public class SchoolBiz {

	private static Logger logger = Logger.getLogger(SchoolBiz.class);

	private DepartmentInfoDao departmentInfoDao;
	private BookAmountDao bookamountDao;

	/**
	 * 通过院校对象查询院校信息
	 * 
	 * @param di
	 * @return
	 */
	public List<Departmentinfo> getDepartmentInfoByExample(Departmentinfo di) {
		return departmentInfoDao.findByExample(di);
	}

	/**
	 * 检查院校信息是否存在
	 * 
	 * @param school
	 * @param institute
	 * @param partName
	 * @return
	 */
	public int ifDepartmentInfoExist(String school, String institute, String partName) {
		Departmentinfo di = new Departmentinfo();
		di.setCollege(school);
		di.setInstitute(institute);
		di.setPartName(partName);
		return ifDepartmentInfoExist(di);
	}

	/**
	 * 检查院校信息是否存在
	 * 
	 * @param dmi
	 * @return
	 */
	public int ifDepartmentInfoExist(Departmentinfo dmi) {
		Departmentinfo di = new Departmentinfo();
		di.setCollege(dmi.getCollege());
		di.setInstitute(dmi.getInstitute());
		di.setPartName(dmi.getPartName());
		List<Departmentinfo> list = departmentInfoDao.findByExample(di);
		logger.info("requirement.biz.AddSchoolBiz.existSchool--院校信息已存在");
		if (list.size() > 0) {
			return list.get(0).getDid();
		} else {
			return 0;
		}
	}

	/**
	 * 添加院校信息
	 * 
	 * @param dmi
	 */
	public void addDepartmentInfo(Departmentinfo dmi) {
		Departmentinfo ex = new Departmentinfo();
		BeanUtils.copyProperties(dmi, ex);
		ex.setVersion(null);
		ex.setGmtCreate(null);
		ex.setGmtModify(null);
		List<Departmentinfo> list = this.departmentInfoDao.findByExample(ex);
		if (!list.isEmpty()) {
			String fmt = "重复添加院校信息, %s, %s, %s";
			throw new RuntimeException(String.format(fmt, dmi.getCollege(),
					dmi.getInstitute(), dmi.getPartName()));
		}
		departmentInfoDao.save(dmi);
	}

	/**
	 * 查找所有院校信息
	 * 
	 * @return
	 */
	public List<Departmentinfo> getAllDepartmentInfo() {
		DetachedCriteria dc = DetachedCriteria.forClass(Departmentinfo.class);
		dc.addOrder(Order.asc("college")).addOrder(Order.asc("institute"))
				.addOrder(Order.asc("partName"));
		List<Departmentinfo> list = departmentInfoDao.findByCriteria(dc);
		logger.info("requirement.biz.GetInfoBiz.getAllInfo--查找院校信息成功");
		return list;
	}

	/**
	 * 查询教材使用量
	 */
	public List<Bookamount> findBookAmountByCriteria(DetachedCriteria dc) {
		return bookamountDao.findByCriteria(dc);
	}

	/**
	 * 通过id查询对象信息
	 * 
	 * @param id
	 * @return
	 */
	public Departmentinfo findDepartmentInfoById(Integer id) {
		Departmentinfo di = departmentInfoDao.findById(id);
		return di;
	}

	/**
	 * 修改院校信息
	 * 
	 * @param dmi
	 */
	public void updateDepartmentInfo(Departmentinfo dmi) {
		departmentInfoDao.update(dmi);
	}

	public DepartmentInfoDao getDepartmentInfoDao() {
		return departmentInfoDao;
	}

	public void setDepartmentInfoDao(DepartmentInfoDao departmentInfoDao) {
		this.departmentInfoDao = departmentInfoDao;
	}

	public BookAmountDao getBookamountDao() {
		return bookamountDao;
	}

	public void setBookamountDao(BookAmountDao bookamountDao) {
		this.bookamountDao = bookamountDao;
	}

	public List<Departmentinfo> findDepartmentInfoByCriteria(DetachedCriteria dc) {
		return departmentInfoDao.findByCriteria(dc);
	}

}
