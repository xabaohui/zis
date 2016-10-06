package com.zis.requirement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zis.requirement.bean.BookAmount;
import com.zis.requirement.dto.RequirementCollectScheduleDTO;

public interface BookAmountDao extends
		PagingAndSortingRepository<BookAmount, Integer> {

	/**
	 * 根据bookId查询
	 * 
	 * @param bookId
	 * @return
	 */
	List<BookAmount> findByBookId(Integer bookId);

	/**
	 * 查询bookId，去重
	 * 
	 * @return
	 */
	@Query("select distinct bookId from BookAmount")
	List<Integer> distinctBookId();

	/**
	 * 根据多条件查询
	 * 
	 * @param bookId
	 * @param partId
	 * @param grade
	 * @param term
	 * @return
	 */
	@Query("from BookAmount where bookId = :bookId and partId = :partId and grade = :grade and term = :term")
	List<BookAmount> findByBookIdPartIdGradeAndTerm(
			@Param(value = "bookId") Integer bookId,
			@Param(value = "partId") Integer partId,
			@Param(value = "grade") Integer grade,
			@Param(value = "term") Integer term);

	/**
	 * 查询college ，去重
	 * 
	 * @return
	 */
	@Query("select distinct college from BookAmount")
	List<String> distinctCollege();

	/**
	 * 对已采集的数据归类汇总
	 * 
	 * 根据多条件分组后计数
	 * 
	 * @return 返回多条件的参数以及每组计数
	 */
	@Query("SELECT new com.zis.requirement.dto.RequirementCollectScheduleDTO(partId, college, institute, partName, grade, term, count(*)) FROM BookAmount GROUP BY partId, college, institute, partName, grade, term")
	List<RequirementCollectScheduleDTO> countGroupByCollegeInseitutePartNamePartIdGradeTerm();

	/**
	 * 对已采集的数据归类汇总
	 * 
	 * 根据多条件分组后计数
	 * 
	 * @return 返回多条件的参数以及每组计数
	 */
	@Query("SELECT new com.zis.requirement.dto.RequirementCollectScheduleDTO (partId, college, institute, partName, grade, term, operator, count(*)) FROM BookAmount GROUP BY partId, college, institute, partName, grade, term, operator")
	List<RequirementCollectScheduleDTO> countGroupByCollegeInseitutePartNamePartIdGradeTermOperator();

	/**
	 * 通过多个bookId查询 排除 大一 以及 院校名为A测试专用
	 * 
	 * @param bookId
	 * @return
	 */
	@Query("FROM BookAmount WHERE grade<>1 AND college<>'A测试专用' AND bookId IN (:bookId)")
	List<BookAmount> findByBookIdListGradeAndCollege(
			@Param(value = "bookId") List<Integer> bookId);

	/**
	 * 通过bookId查询 排除 大一 以及 院校名为A测试专用
	 * 
	 * @param bookId
	 * @return
	 */
	@Query("FROM BookAmount WHERE grade<>1 AND college<>'A测试专用' AND bookId = :bookId")
	List<BookAmount> findByBookIdGradeAndCollege(
			@Param(value = "bookId") Integer bookId);
}