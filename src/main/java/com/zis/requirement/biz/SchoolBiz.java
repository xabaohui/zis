package com.zis.requirement.biz;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zis.api.response.DepartmentQueryData;
import com.zis.requirement.bean.Departmentinfo;
import com.zis.requirement.repository.DepartmentInfoDao;

@Service
public class SchoolBiz {

	private static Logger logger = Logger.getLogger(SchoolBiz.class);

	@Autowired
	private DepartmentInfoDao departmentInfoDao;

	/**
	 * 检查院校信息是否存在
	 * 
	 * @param school
	 * @param institute
	 * @param partName
	 * @return
	 */
	public int ifDepartmentInfoExist(String school, String institute,
			String partName) {
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
		// Departmentinfo di = new Departmentinfo();
		// di.setCollege(dmi.getCollege());
		// di.setInstitute(dmi.getInstitute());
		// di.setPartName(dmi.getPartName());
		List<Departmentinfo> list = departmentInfoDao
				.findByCollegeInstituteAndPartName(dmi.getCollege(),
						dmi.getInstitute(), dmi.getPartName());
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
		// Departmentinfo ex = new Departmentinfo();
		// BeanUtils.copyProperties(dmi, ex);
		// ex.setGmtCreate(null);
		// ex.setGmtModify(null);
		List<Departmentinfo> list = this.departmentInfoDao
				.findByCollegeInstitutePartNameAndYears(dmi.getCollege(),
						dmi.getInstitute(), dmi.getPartName(), dmi.getYears());
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
//		DetachedCriteria dc = DetachedCriteria.forClass(Departmentinfo.class);
//		dc.addOrder(Order.asc("college")).addOrder(Order.asc("institute"))
//				.addOrder(Order.asc("partName"));
		List<Departmentinfo> list = departmentInfoDao.findOrderByCollegeInstitutePartNameAsc();
		logger.info("requirement.biz.GetInfoBiz.getAllInfo--查找院校信息成功");
		return list;
	}

	/**
	 * 通过id查询对象信息
	 * 
	 * @param id
	 * @return
	 */
	public Departmentinfo findDepartmentInfoById(Integer id) {
		Departmentinfo di = departmentInfoDao.findOne(id);
		return di;
	}

	/**
	 * 修改院校信息
	 * 
	 * @param dmi
	 */
	public void updateDepartmentInfo(Departmentinfo dmi) {
		departmentInfoDao.save(dmi);
	}

//	public List<Departmentinfo> findDepartmentInfoByCriteria(DetachedCriteria dc) {
//		return departmentInfoDao.findByCriteria(dc);
//	}
	
	/**
	 * 根据 college, institute, partName 查询
	 * 
	 * @param college
	 * @param institute
	 * @param partName
	 * @return
	 */
	public List<Departmentinfo> findByCollegeInstituteAndPartName(String college, String institute, String partName) {
		return departmentInfoDao.findByCollegeInstituteAndPartName(college, institute, partName);
	}
	
	/**
	 * 根据 collegeLis 条件以及 college 分组及升序排序 后查询 did和college
	 * 
	 * @param collegeList
	 * @return
	 */
	public List<DepartmentQueryData> findByCollegeListGroupByCollegeOrderByCollege(List<String> collegeList) {
		return departmentInfoDao.findByCollegeListGroupByCollegeOrderByCollege(collegeList);
	}
	
	/**
	 * 根据 collegeLis 条件以及 college 分组及升序排序 后查询 did和college
	 * 
	 * @param collegeList
	 * @return
	 */
	public List<DepartmentQueryData> findByCollegeGroupByInstituteOrderByInstitute(String college) {
		return departmentInfoDao.findByCollegeGroupByInstituteOrderByInstitute(college);
	}
	
	/**
	 * 根据 college,  institute 条件以及 partName 分组及升序排序 后查询 did和partName
	 * @param college
	 * @param institute
	 * @return
	 */
	public List<DepartmentQueryData> findByCollegeAndInstituteGroupByPartNameOrderByPartName(String college, String institute) {
		return departmentInfoDao.findByCollegeAndInstituteGroupByPartNameOrderByPartName(college, institute);
	}
	
	/**
	 * 分页查询 Departmentinfo
	 * @param page
	 * @return
	 */
	public Page<Departmentinfo> findAll(Pageable page){
		return this.departmentInfoDao.findAll(page);
	}

}
