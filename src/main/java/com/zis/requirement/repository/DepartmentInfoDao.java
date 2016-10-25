package com.zis.requirement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zis.api.response.DepartmentQueryData;
import com.zis.requirement.bean.Departmentinfo;

/**
 * @author think
 * 
 */
public interface DepartmentInfoDao extends PagingAndSortingRepository<Departmentinfo, Integer>,
		JpaSpecificationExecutor<Departmentinfo> {

	/**
	 * 根据 college的集合 查询 排序通过 college, institute, partName 三个字段 以升序排列
	 * 
	 * @param collegeList
	 * @return
	 */
	@Query(value = "from Departmentinfo where college IN(:collegeList) order by college, institute, partName asc")
	public List<Departmentinfo> findByCollegeListOrderByCollegeInstitutePartNameAsc(
			@Param(value = "collegeList") List<String> collegeList);

	/**
	 * 根据 college, institute, partName 三个字段 以升序排列 查询
	 * 
	 * @param collegeList
	 * @return
	 */
	@Query(value = "from Departmentinfo order by college, institute, partName asc")
	public List<Departmentinfo> findOrderByCollegeInstitutePartNameAsc();

	/**
	 * 根据 college, institute, partName 查询
	 * 
	 * @param college
	 * @param institute
	 * @param partName
	 * @return
	 */
	@Query(value = "from Departmentinfo where college = :college and institute = :institute and partName = :partName")
	public List<Departmentinfo> findByCollegeInstituteAndPartName(@Param(value = "college") String college,
			@Param(value = "institute") String institute, @Param(value = "partName") String partName);

	/**
	 * 根据 college, institute, partName years 查询
	 * 
	 * @param college
	 * @param institute
	 * @param partName
	 * @return
	 */
	@Query(value = "from Departmentinfo where college = :college and institute = :institute and partName = :partName and years = :years")
	public List<Departmentinfo> findByCollegeInstitutePartNameAndYears(@Param(value = "college") String college,
			@Param(value = "institute") String institute, @Param(value = "partName") String partName,
			@Param(value = "years") Integer years);

	/**
	 * 根据 collegeLis 条件以及 college 分组及升序排序 后查询 did和college
	 * 
	 * @param collegeList
	 * @return
	 */
	@Query(value = "SELECT new com.zis.api.response.DepartmentQueryData(did, college) FROM Departmentinfo WHERE college IN (:collegeList) GROUP BY college ORDER BY college ASC")
	public List<DepartmentQueryData> findByCollegeListGroupByCollegeOrderByCollege(
			@Param(value = "collegeList") List<String> collegeList);

	/**
	 * 根据 college 条件以及 institute 分组及升序排序 后查询 did和institute
	 * 
	 * @param college
	 * @return
	 */
	@Query(value = "SELECT new com.zis.api.response.DepartmentQueryData(did, institute) FROM Departmentinfo WHERE college = :college GROUP BY institute ORDER BY institute ASC")
	public List<DepartmentQueryData> findByCollegeGroupByInstituteOrderByInstitute(
			@Param(value = "college") String college);

	/**
	 * 根据 college, institute 条件以及 partName 分组及升序排序 后查询 did和partName
	 * 
	 * @param college
	 * @param institute
	 * @return
	 */
	@Query(value = "SELECT new com.zis.api.response.DepartmentQueryData(did, partName) FROM Departmentinfo WHERE college = :college and institute = :institute GROUP BY partName ORDER BY partName ASC")
	public List<DepartmentQueryData> findByCollegeAndInstituteGroupByPartNameOrderByPartName(
			@Param(value = "college") String college, @Param(value = "institute") String institute);
}