package com.zis.requirement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.common.util.ZisUtils;
import com.zis.requirement.bean.BookAmount;
import com.zis.requirement.bean.Departmentinfo;
import com.zis.requirement.biz.BookAmountService;
import com.zis.requirement.biz.SchoolBiz;

/**
 * 添加教材使用量
 * 
 * @author lenovo
 * 
 */
@Controller
@RequestMapping(value = "/requirement")
public class BookAmountAddController {

	private static Logger logger = Logger.getLogger(BookAmountAddController.class);
	@Autowired
	private BookAmountService addAmountBiz;
	@Autowired
	private SchoolBiz schoolBiz;

	// private Integer grade;
	// private Integer term;
	// private Integer amount;
	// private String operator;
	// private Integer did;

	// TODO 验证框架
	// @Validations(
	// // 学年学期不能为空
	// requiredFields = { @RequiredFieldValidator(fieldName = "term", key =
	// "学期不能为空") },
	// // 学年学期区间
	// intRangeFields = { @IntRangeFieldValidator(fieldName = "term", min = "1",
	// max = "2", key = "学期在1和2之间") },
	// // 学年学期只能填数字
	// conversionErrorFields = {
	// @ConversionErrorFieldValidator(fieldName = "amount", key = "数量只能填数字",
	// shortCircuit = true),
	// @ConversionErrorFieldValidator(fieldName = "term", key = "学期只能填数字",
	// shortCircuit = true) })
	// 添加教材使用量
	@RequestMapping(value = "/addAmount")
	public String addAmount(Integer did, Integer grade, Integer term, Integer amount, String operator) {
		// 参数检查
		if (did <= 0) {
			// TODO 验证框架
			// this.addFieldError("schoolNotExist", "院校信息不存在");
			return "error";
		}
		if (grade == null) {
			// TODO 验证框架
			// this.addFieldError("Error", "学年不能为空");
			return "error";
		}
		Departmentinfo di = schoolBiz.findDepartmentInfoById(did);
		if (di == null) {
			String errMsg = "后台添加教材使用量失败，院校不存在id=" + did;
			logger.error(errMsg);
			// TODO 验证框架
			// this.addActionError(errMsg);
			return "error";
		}
		// 从session中提取数据
		HttpSession session = ServletActionContext.getRequest().getSession();
		@SuppressWarnings("unchecked")
		Map<Integer, Bookinfo> map = (Map<Integer, Bookinfo>) session.getAttribute("BookMap");
		if (map == null) {
			String errMsg = "未添加图书";
			logger.error(errMsg);
			// TODO 验证框架
			// this.addActionError(errMsg);
			return "error";
		}

		// 准备要保存到数据库的数据
		List<BookAmount> list = buildBookAmountList(di, map, did, grade, term, amount, operator);

		try {
			// 写入数据库
			this.addAmountBiz.saveBookAmountList(list);
			// 清理session中记录的数据
			session.setAttribute("BookMap", null);
		} catch (Exception e) {
			logger.error("后台添加教材使用量失败", e);
			// TODO 验证框架
			// this.addActionError(e.getMessage());
			return "error";
		}
		return "success";
	}

	private List<BookAmount> buildBookAmountList(Departmentinfo di, Map<Integer, Bookinfo> map, Integer did,
			Integer grade, Integer term, Integer amount, String operator) {
		List<BookAmount> list = new ArrayList<BookAmount>();
		for (Entry<Integer, Bookinfo> entry : map.entrySet()) {
			BookAmount ba = new BookAmount();
			// 设置院校信息
			ba.setPartId(did);
			ba.setCollege(di.getCollege());
			ba.setInstitute(di.getInstitute());
			ba.setPartName(di.getPartName());
			ba.setGrade(grade);
			ba.setTerm(term);
			// 设置教材信息
			ba.setBookId(entry.getKey());
			ba.setBookAuthor(entry.getValue().getBookAuthor());
			ba.setBookName(entry.getValue().getBookName());
			ba.setBookPublisher(entry.getValue().getBookPublisher());
			ba.setIsbn(entry.getValue().getIsbn());
			ba.setGmtCreate(ZisUtils.getTS());
			ba.setGmtModify(ZisUtils.getTS());
			ba.setAmount(amount);
			ba.setOperator(operator);
			list.add(ba);
		}
		return list;
	}
}
