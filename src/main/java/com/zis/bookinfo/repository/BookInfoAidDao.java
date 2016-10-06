package com.zis.bookinfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.zis.bookinfo.bean.BookinfoAid;

public interface BookInfoAidDao extends CrudRepository<BookinfoAid, Integer> {

	List<BookinfoAid> findByGroupKey(String groupKey);
	
	List<BookinfoAid> findByGroupKeyAndShortBookName(String groupKey, String shortBookName);
	
	@Query(value="select max(id) from BookinfoAid")
	Integer findMaxId();
	
	@Query(value="select min(id) from BookinfoAid")
	Integer findMinId();
}
