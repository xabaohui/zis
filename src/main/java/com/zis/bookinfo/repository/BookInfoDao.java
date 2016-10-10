package com.zis.bookinfo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.bean.BookinfoStatus;

public interface BookInfoDao  extends
		PagingAndSortingRepository<Bookinfo, Integer>, JpaSpecificationExecutor<Bookinfo>, JpaRepository<Bookinfo, Integer>{

	@Query("select max(id) from Bookinfo")
	Integer findMaxBookId();
	
	/**
	 * 按照outId查询图书
	 * @param outId
	 * @return
	 */
	List<Bookinfo> findByOutId(Integer outId);

	/**
	 * 按照relateId查询图书，限定状态为NORMAL
	 * 
	 * @param relateId
	 * @return
	 */
	@Query("from Bookinfo where bookStatus = '" + BookinfoStatus.NORMAL
			+ "' and relateId = :relateId")
	List<Bookinfo> findByRelateId(@Param(value = "relateId") String relateId);

	/**
	 * 按照groupId查询图书，限定状态为NORMAL
	 * 
	 * @param groupId
	 * @return
	 */
	@Query("from Bookinfo where bookStatus = '" + BookinfoStatus.NORMAL
			+ "' and groupId = :groupId")
	List<Bookinfo> findByGroupId(@Param(value = "groupId") String groupId);

	/**
	 * 按照Id查询图书，限定状态为NORMAL
	 * 
	 * @param bookId
	 * @return
	 */
	@Query("from Bookinfo where bookStatus = '" + BookinfoStatus.NORMAL
			+ "' and id = :id")
	Bookinfo findNormalBook(@Param(value = "id") Integer bookId);

	/**
	 * 按照ISBN查找图书，排除status=DISCARD的记录
	 * 
	 * @return
	 */
	@Query("from Bookinfo where bookStatus != '" + BookinfoStatus.DISCARD
			+ "' and isbn = :isbn")
	List<Bookinfo> findByIsbn(@Param(value = "isbn") String isbn);

	/**
	 * 按照书名、作者、出版社查询图书，排除status=DISCARD的记录
	 * 
	 * @param bookName
	 * @param author
	 * @param publisher
	 * @return
	 */
	@Query("from Bookinfo where bookStatus != '"
			+ BookinfoStatus.DISCARD
			+ "' and bookName = :bookName and bookAuthor = :author and bookPublisher = :publisher")
	List<Bookinfo> findByBookNameAuthorPublisher(
			@Param(value = "bookName") String bookName,
			@Param(value = "author") String author,
			@Param(value = "publisher") String publisher);

	/**
	 * 按照groupId和出版社查询图书，排除status=DISCARD的记录
	 * 
	 * @param groupId
	 * @param publisher
	 * @return
	 */
	@Query("from Bookinfo where bookStatus != '" + BookinfoStatus.DISCARD
			+ "' and groupId = :groupId and bookPublisher = :publisher")
	List<Bookinfo> findByGroupIdAndPublisher(
			@Param(value = "groupId") String groupId,
			@Param(value = "publisher") String publisher);
	
	/**
	 * 按照状态查询图书列表 
	 * @param status
	 * @return
	 */
	Page<Bookinfo> findByBookStatus(String status, Pageable page);
	
	/**
	 * 查询待审核的记录数
	 * @return
	 */
	@Query("select count(*) from Bookinfo where bookStatus != '" + BookinfoStatus.WAITCHECK + "'")
	Integer countWaitingBooks();
	
}
