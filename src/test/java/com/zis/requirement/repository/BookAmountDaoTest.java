package com.zis.requirement.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zis.requirement.bean.BookAmount;
import com.zis.requirement.dto.RequirementCollectScheduleDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:spring.xml" })
@TransactionConfiguration(defaultRollback = true, transactionManager = "transactionManager")
public class BookAmountDaoTest {

	@Autowired
	BookAmountDao bookAmountDao;

	@Test
	public void testFindByBookId() {
		// prepare data
		BookAmount test = getBookAmount();
		this.bookAmountDao.save(test);

		// execute
		List<BookAmount> list = this.bookAmountDao.findByBookId(test
				.getBookId());
		// assert
		Assert.assertFalse(list.isEmpty());
	}

	@Test
	public void testDistinctBookId() {
		// execute
		List<Integer> list = this.bookAmountDao.distinctBookId();

		// assert
		Assert.assertFalse(list.isEmpty());
		Integer[] bookIds = getListToIntegerArray(list);
		Assert.assertFalse(getDistinctBookIdWhetherSuccess(bookIds));

	}

	@Test
	public void testFindByBookIdPartIdGradeAndTerm() {
		// prepare data
		BookAmount test = getBookAmount();
		this.bookAmountDao.save(test);

		// execute
		List<BookAmount> list = this.bookAmountDao
				.findByBookIdPartIdGradeAndTerm(test.getBookId(),
						test.getPartId(), test.getGrade(), test.getTerm());

		// assert
		Assert.assertFalse(list.isEmpty());
	}

	@Test
	public void testDistinctCollege() {
		// execute
		List<String> list = this.bookAmountDao.distinctCollege();

		// assert
		Assert.assertFalse(list.isEmpty());
		Assert.assertFalse(getDistinctCollegeWhetherSuccess(list));
	}

	@Test
	public void testCountGroupByCollegeInseitutePartNamePartIdGradeTerm() {
		// execute
		List<RequirementCollectScheduleDTO> list = this.bookAmountDao
				.countGroupByCollegeInseitutePartNamePartIdGradeTerm();

		// assert
		Assert.assertFalse(list.isEmpty());
	}

	@Test
	public void testCountGroupByCollegeInseitutePartNamePartIdGradeTermOperator() {
		// execute
		List<RequirementCollectScheduleDTO> list = this.bookAmountDao
				.countGroupByCollegeInseitutePartNamePartIdGradeTermOperator();

		// assert
		Assert.assertFalse(list.isEmpty());
	}

	@Test
	public void testFindByBookIdListGradeAndCollege() {
		// prepare data
		BookAmount test = getBookAmount();
		BookAmount test1 = getBookAmount1();
		this.bookAmountDao.save(test);
		this.bookAmountDao.save(test1);
		List<Integer> listTest = new ArrayList<Integer>();
		listTest.add(test.getBookId());
		listTest.add(test1.getBookId());

		// execute
		List<BookAmount> list = this.bookAmountDao
				.findByBookIdListGradeAndCollege(listTest);

		// assert
		Assert.assertFalse(list.isEmpty());
		for (BookAmount rcd : list) {
			Assert.assertNotEquals(test1.getBookId(), rcd.getBookId());
			Assert.assertEquals(test.getBookId(), rcd.getBookId());
			Assert.assertNotEquals(new Integer(1), rcd.getGrade());
			Assert.assertNotEquals("A测试专用", rcd.getCollege());
		}
	}

	@Test
	public void testFindByBookIdGradeAndCollege() {
		// prepare data
		BookAmount test = getBookAmount();
		this.bookAmountDao.save(test);

		// execute
		List<BookAmount> list = this.bookAmountDao
				.findByBookIdGradeAndCollege(test.getBookId());

		// assert
		Assert.assertFalse(list.isEmpty());
		for (BookAmount rcd : list) {
			Assert.assertEquals(test.getBookId(), rcd.getBookId());
			Assert.assertNotEquals(new Integer(1), rcd.getGrade());
			Assert.assertNotEquals("A测试专用", rcd.getCollege());
		}
	}
	
	private BookAmount getBookAmount() {
		BookAmount bookAmount = new BookAmount();
		bookAmount.setBookId(2500);
		bookAmount.setIsbn("9527");
		bookAmount.setBookName("华安");
		bookAmount.setBookAuthor("唐伯虎");
		bookAmount.setBookPublisher("未来战士出版社");
		bookAmount.setPartId(29);
		bookAmount.setAmount(50);
		bookAmount.setGmtCreate(new Timestamp(System.currentTimeMillis()));
		bookAmount.setGmtModify(new Timestamp(System.currentTimeMillis()));
		bookAmount.setOperator("吕老师");
		bookAmount.setCollege("魔法大学");
		bookAmount.setInstitute("恶魔猎手学院");
		bookAmount.setPartName("屠夫专业");
		bookAmount.setGrade(2);
		bookAmount.setTerm(3);
		return bookAmount;
	}
	
	private BookAmount getBookAmount1() {
		BookAmount bookAmount = new BookAmount();
		bookAmount.setBookId(25679081);
		bookAmount.setIsbn("9222333");
		bookAmount.setBookName("华安1");
		bookAmount.setBookAuthor("唐伯虎1");
		bookAmount.setBookPublisher("未来战士出版社1");
		bookAmount.setPartId(29);
		bookAmount.setAmount(50);
		bookAmount.setGmtCreate(new Timestamp(System.currentTimeMillis()));
		bookAmount.setGmtModify(new Timestamp(System.currentTimeMillis()));
		bookAmount.setOperator("吕老师2");
		bookAmount.setCollege("魔法大学2");
		bookAmount.setInstitute("恶魔猎手学院2");
		bookAmount.setPartName("屠夫专业2");
		bookAmount.setGrade(1);
		bookAmount.setTerm(3);
		return bookAmount;
	}

	/**
	 * 判断是否去重成功 返回值为false 表明去重成功
	 * 
	 * @param input
	 * @return
	 */
	private boolean getDistinctBookIdWhetherSuccess(Integer[] input) {
		for (int i = 0; i < input.length - 1; i++) {
			for (int j = 0; j < input.length - i - 1; j++) {
				if (input[i] < input[j] - 1) {
					Integer temp = input[j];
					input[j] = input[j + 1];
					input[j + 1] = temp;
				}
			}
		}
		for (int i = 0; i < input.length - 1; i++) {
			if (input[i] == input[i + 1]) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 将List转化为integer类型的数组
	 * 
	 * @param input
	 * @return
	 */
	private Integer[] getListToIntegerArray(List<Integer> input) {
		Integer[] result = new Integer[input.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = input.get(i);
		}
		return result;
	}

	/**
	 * 判断是否去重成功 返回值为false 表明去重成功
	 * 
	 * @param input
	 * @return
	 */
	private boolean getDistinctCollegeWhetherSuccess(List<String> input) {
		Set<String> set = new HashSet<String>();
		for (int i = 0; i < input.size(); i++) {
			set.add(input.get(i));
		}
		return input.size() != set.size();
	}
}