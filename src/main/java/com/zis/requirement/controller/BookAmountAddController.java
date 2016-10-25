package com.zis.requirement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.common.util.ZisUtils;
import com.zis.requirement.bean.BookAmount;
import com.zis.requirement.bean.Departmentinfo;
import com.zis.requirement.biz.BookAmountService;
import com.zis.requirement.biz.SchoolBiz;
import com.zis.requirement.dto.AddAmountDTO;

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

	// 添加教材使用量
	@RequestMapping(value = "/addAmount")
	public String addAmount(@Valid @ModelAttribute("dto") AddAmountDTO dto, BindingResult br, ModelMap context,
			HttpSession session) {
		if(br.hasErrors()){
			return "requirement/amountInfo";
		}
		Integer grade = dto.getGrade();
		Integer term = dto.getTerm();
		Integer amount = dto.getAmount();
		String operator = dto.getOperator();
		Integer did = dto.getDid();
		// 参数检查
		if (did <= 0) {
			// TODO 验证框架(处理方式使用modelMap,成功信息未actionMessage,错误信息用actionError)
			context.put("actionError", "院校信息不存在");
			return "error";
		}
		if (grade == null) {
			context.put("actionError", "学年不能为空");
			return "error";
		}
		Departmentinfo di = schoolBiz.findDepartmentInfoById(did);
		if (di == null) {
			String errMsg = "后台添加教材使用量失败，院校不存在id=" + did;
			logger.error(errMsg);
			context.put("actionError", errMsg);
			return "error";
		}
		// 从session中提取数据
		@SuppressWarnings("unchecked")
		Map<Integer, Bookinfo> map = (Map<Integer, Bookinfo>) session.getAttribute("BookMap");
		if (map == null) {
			String errMsg = "未添加图书";
			logger.error(errMsg);
			context.put("actionError", errMsg);
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
			context.put("actionError", e.getMessage());
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
