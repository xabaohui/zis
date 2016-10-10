package com.zis.requirement.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zis.api.response.DepartmentQueryData;
import com.zis.requirement.bean.Departmentinfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:spring.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class DepartmentInfoDaoTest {

	@Autowired
	DepartmentInfoDao dao;

	@Test
	public void testFindByCollegeListOrderByCollegeInstitutePartNameAsc() {
		// perpare data
		Departmentinfo test = getDepartmentInfo();
		this.dao.save(test);
		Departmentinfo test1 = getDepartmentInfo1();
		this.dao.save(test1);
		List<String> collegeList = new ArrayList<String>();
		collegeList.add(test.getCollege());
		collegeList.add(test1.getCollege());

		// execute
		List<Departmentinfo> list = this.dao.findByCollegeListOrderByCollegeInstitutePartNameAsc(collegeList);

		// assert
		Assert.assertFalse(list.isEmpty());
		for (Departmentinfo di : list) {
			if (!collegeList.contains(di.getCollege())) {
				Assert.fail("查询结果有非collegeList中的结果");
			}
		}
	}

	@Test
	public void testFindOrderByCollegeInstitutePartNameAsc() {
		// perpare data
		Departmentinfo test = getDepartmentInfo();
		test.setYears(19902);
		this.dao.save(test);

		// execute
		List<Departmentinfo> list = this.dao.findOrderByCollegeInstitutePartNameAsc();

		// assert
		Assert.assertFalse(list.isEmpty());
		for (Departmentinfo rec : list) {
			if (test.getYears().equals(rec.getYears())) {
				return;
			}
		}
		Assert.fail("xxx");
	}

	@Test
	public void testFindByCollegeInstituteAndPartName() {
		// perpare data
		Departmentinfo test = getDepartmentInfo();
		test.setCollege("嘻嘻大学");
		test.setInstitute("猫王学院");
		test.setPartName("表演系");
		this.dao.save(test);

		// execute
		List<Departmentinfo> list = this.dao.findByCollegeInstituteAndPartName(test.getCollege(), test.getInstitute(),
				test.getPartName());

		// assert
		Assert.assertFalse(list.isEmpty());
		for (Departmentinfo rec : list) {
			Assert.assertEquals(test.getCollege(), rec.getCollege());
			Assert.assertEquals(test.getInstitute(), rec.getInstitute());
			Assert.assertEquals(test.getPartName(), rec.getPartName());
		}
	}

	@Test
	public void testFindByCollegeInstitutePartNameAndYears() {
		// perpare data
		Departmentinfo test = getDepartmentInfo();
		test.setCollege("xxxx卫华学校");
		test.setInstitute("麦哈顿专业");
		test.setPartName("ddxxF");
		test.setYears(23332);
		this.dao.save(test);

		// execute
		List<Departmentinfo> list = this.dao.findByCollegeInstitutePartNameAndYears(test.getCollege(),
				test.getInstitute(), test.getPartName(), test.getYears());

		// assert
		Assert.assertFalse(list.isEmpty());
		for (Departmentinfo rec : list) {
			Assert.assertEquals(test.getCollege(), rec.getCollege());
			Assert.assertEquals(test.getInstitute(), rec.getInstitute());
			Assert.assertEquals(test.getPartName(), rec.getPartName());
			Assert.assertEquals(test.getYears(), rec.getYears());
		}
	}

	@Test
	public void testFindByCollegeListGroupByCollegeOrderByCollege() {
		// perpare data
		Departmentinfo test = getDepartmentInfo();
		Departmentinfo test1 = getDepartmentInfo1();
		this.dao.save(test);
		this.dao.save(test1);
		List<String> collegeList = new ArrayList<String>();
		collegeList.add(test.getCollege());
		collegeList.add(test1.getCollege());

		// execute
		List<DepartmentQueryData> list = this.dao.findByCollegeListGroupByCollegeOrderByCollege(collegeList);

		// assert
		Assert.assertFalse(list.isEmpty());
		for (DepartmentQueryData rec : list) {
			if (!collegeList.contains(rec.getName())) {
				Assert.fail("查询结果有非collegeList中的结果");
			}
		}
	}

	@Test
	public void testFindByCollegeGroupByInstituteOrderByInstitute() {
		// perpare data
		Departmentinfo test = getDepartmentInfo();
		test.setCollege("牛逼大学");
		this.dao.save(test);

		// execute
		List<DepartmentQueryData> list = this.dao.findByCollegeGroupByInstituteOrderByInstitute(test.getCollege());

		// assert
		Assert.assertFalse(list.isEmpty());
		Assert.assertEquals(1, list.size());
		for (DepartmentQueryData de : list) {
			Assert.assertEquals(test.getInstitute(), de.getName());
		}
	}

	@Test
	public void testFindByCollegeAndInstituteGroupByPartNameOrderByPartName() {
		// perpare data
		Departmentinfo test = getDepartmentInfo();
		test.setCollege("呵呵大学");
		test.setInstitute("啊哈学院");
		this.dao.save(test);

		// execute
		List<DepartmentQueryData> list = this.dao.findByCollegeAndInstituteGroupByPartNameOrderByPartName(
				test.getCollege(), test.getInstitute());

		// assert
		Assert.assertFalse(list.isEmpty());
		Assert.assertEquals(1, list.size());
		for (DepartmentQueryData de : list) {
			Assert.assertEquals(test.getPartName(), de.getName());
		}
	}

	private Departmentinfo getDepartmentInfo() {
		Departmentinfo bean = new Departmentinfo();
		bean.setCollege("麻省理工");
		bean.setInstitute("超物质研究学院");
		bean.setPartName("超物质应用科学");
		bean.setYears(4);
		bean.setGmtCreate(new Timestamp(System.currentTimeMillis()));
		bean.setGmtModify(new Timestamp(System.currentTimeMillis()));
		return bean;
	}

	private Departmentinfo getDepartmentInfo1() {
		Departmentinfo bean = new Departmentinfo();
		bean.setCollege("嘉顿大学");
		bean.setInstitute("曼哈顿博士学院");
		bean.setPartName("曼哈顿应用科学");
		bean.setYears(4);
		bean.setGmtCreate(new Timestamp(System.currentTimeMillis()));
		bean.setGmtModify(new Timestamp(System.currentTimeMillis()));
		return bean;
	}
}