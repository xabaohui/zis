package com.zis.bookinfo.dao;

import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.bean.BookinfoStatus;
import com.zis.bookinfo.repository.BookInfoDao;
import com.zis.common.util.TestUtil;
import com.zis.common.util.ZisUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:spring.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class BookInfoDaoTest {

	@Autowired
	BookInfoDao bookInfoDao;
	
	@Test
	public void testSave() {
		this.bookInfoDao.save(generateRandomBookInfo());
	}

	@Test
	public void testSaveForUpdate() {
		Bookinfo bi = this.bookInfoDao.save(generateRandomBookInfo());
		String bookNameExpect = bi.getBookName() + "update";
		Integer versionExpect = bi.getVersion() + 1;
		bi.setBookName(bookNameExpect);
		this.bookInfoDao.save(bi);

		Bookinfo bi2 = this.bookInfoDao.findOne(bi.getId());
		Assert.assertEquals(bookNameExpect, bi2.getBookName());
		Assert.assertEquals(versionExpect, bi2.getVersion());
	}

	@Test
	public void testFindMaxId() {
		Integer maxId = this.bookInfoDao.findMaxBookId();
		System.out.println(maxId);
		Assert.assertNotNull(maxId);
	}

	@Test
	public void testFindByIsbn() {
		// prepare data
		Bookinfo book = generateRandomBookInfo();
		this.bookInfoDao.save(book);

		// execute
		String isbn = book.getIsbn();
		List<Bookinfo> list = this.bookInfoDao.findByIsbn(isbn);

		// assert
		Assert.assertFalse(list.isEmpty());
	}

	@Test
	public void testFindByBookNameAuthorPublisher() {
		// prepare data
		Bookinfo book = generateRandomBookInfo();
		this.bookInfoDao.save(book);

		// execute
		List<Bookinfo> list = this.bookInfoDao.findByBookNameAuthorPublisher(
				book.getBookName(), book.getBookAuthor(),
				book.getBookPublisher());

		// assert
		Assert.assertFalse(list.isEmpty());
	}

	@Test
	public void testFindByGroupIdAndPublisher() {
		// prepare data
		Bookinfo book = generateRandomBookInfo();
		String groupId = TestUtil.randomStr(5);
		String publisher = book.getBookPublisher();
		book.setGroupId(groupId);
		this.bookInfoDao.save(book);

		// execute
		List<Bookinfo> list = this.bookInfoDao.findByGroupIdAndPublisher(
				groupId, publisher);

		// assert
		Assert.assertFalse(list.isEmpty());
	}

	@Test
	public void testFindByRelateId() {
		// prepare data
		Bookinfo book = generateRandomBookInfo();
		String relateId = TestUtil.randomStr(5);
		book.setRelateId(relateId);
		this.bookInfoDao.save(book);

		// execute
		List<Bookinfo> list = this.bookInfoDao.findByRelateId(relateId);

		// assert
		Assert.assertFalse(list.isEmpty());
	}

	@Test
	public void testFindByGroupId() {
		// prepare data
		Bookinfo book = generateRandomBookInfo();
		String groupId = TestUtil.randomStr(5);
		book.setGroupId(groupId);
		System.out.println(groupId);
		this.bookInfoDao.save(book);

		// execute
		List<Bookinfo> list = this.bookInfoDao.findByGroupId(groupId);

		// assert
		Assert.assertFalse(list.isEmpty());
	}

	@Test
	public void testFindByOutId() {
		// prepare data
		Bookinfo book = generateRandomBookInfo();
		Integer outId = new Random().nextInt(1000);
		this.bookInfoDao.save(book);
		
		// execute
		List<Bookinfo> list = this.bookInfoDao.findByOutId(outId);
		
		// assert
		Assert.assertFalse(list.isEmpty());
	}
	
	@Test
	public void testFindByBookStatus() {
		Pageable page = new PageRequest(0, 5);
		Page<Bookinfo> list = this.bookInfoDao.findByBookStatus(BookinfoStatus.NORMAL, page);
		for (Bookinfo book : list) {
			System.out.println(book.getId());
		}
		Assert.assertNotNull(list);
	}
	
	@Test
	public void testCountWaitingBooks() {
		Integer count = this.bookInfoDao.countWaitingBooks();
		System.out.println(count);
		Assert.assertNotNull(count);
	}

	private Bookinfo generateRandomBookInfo() {
		Bookinfo book = new Bookinfo();
		book.setBookAuthor("程序元");
		book.setBookEdition("第一版");
		book.setBookName("Java从入门到精通");
		book.setBookPrice(49.80);
		book.setBookPublisher("程序员毁灭之路出版社");
		book.setBookStatus(BookinfoStatus.NORMAL);
		book.setGmtCreate(ZisUtils.getTS());
		book.setGmtModify(ZisUtils.getTS());
		book.setIsbn("9787" + TestUtil.randomStr(9));
		book.setPublishDate(ZisUtils.getTS());
		return book;
	}
}
