package com.zis.bookinfo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zis.bookinfo.bean.BookinfoAid;

public interface BookInfoAidDao extends PagingAndSortingRepository<BookinfoAid, Integer> {

	List<BookinfoAid> findByGroupKey(String groupKey);

	List<BookinfoAid> findByGroupKeyAndShortBookName(String groupKey, String shortBookName);

	@Query(value = "select max(id) from BookinfoAid")
	Integer findMaxId();

	@Query(value = "select min(id) from BookinfoAid")
	Integer findMinId();

	@Query(value = "select b from BookinfoAid b where totalCount > 1 and checkLevel = :checkLevel")
	Page<BookinfoAid> findByCheckLevelAndTotalCountGtOne(@Param(value = "checkLevel") Integer checkLevel, Pageable page);
}
