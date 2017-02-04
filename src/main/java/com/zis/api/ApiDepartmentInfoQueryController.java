package com.zis.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zis.api.response.BaseApiResponse;
import com.zis.api.response.DepartmentQueryData;
import com.zis.api.response.DepartmentQueryResponse;
import com.zis.requirement.bean.Departmentinfo;
import com.zis.requirement.biz.SchoolBiz;

/**
 * API查询院校信息action
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = "/api")
public class ApiDepartmentInfoQueryController extends BaseApiController {

	private static final Logger logger = Logger.getLogger(ApiDepartmentInfoQueryController.class);

	// private String id;
	@Autowired
	private SchoolBiz schoolBiz;

	/**
	 * 查询所有学校
	 * 
	 * @return
	 */
	@RequestMapping(value = "/queryAllCollege", produces = "text/plain; charset=utf-8")
	public String queryCollege(String id, HttpServletResponse response) {
		logger.info("api.queryCollege invoke");
		List<String> coveredCollege = new ArrayList<String>();
		coveredCollege.add("北方信息工程学院");
		coveredCollege.add("华清学院");
		coveredCollege.add("西北农林科技大学");
		coveredCollege.add("西北大学");
		coveredCollege.add("西北工业大学");
		coveredCollege.add("西北政法大学");
		coveredCollege.add("西安交通大学");
		coveredCollege.add("西安交通大学城市学院");
		coveredCollege.add("西安外国语大学");
		coveredCollege.add("西安工业大学");
		coveredCollege.add("西安工程大学");
		coveredCollege.add("西安建筑科技大学");
		coveredCollege.add("西安理工大学");
		coveredCollege.add("西安电子科技大学");
		coveredCollege.add("西安石油大学");
		coveredCollege.add("西安科技大学");
		coveredCollege.add("西安财经学院");
		coveredCollege.add("西安邮电大学");
		coveredCollege.add("长安大学");
		coveredCollege.add("陕西师范大学");
		coveredCollege.add("陕西科技大学");
		coveredCollege.add("A测试专用");
		// 查询学校名称和ID，按照学校名称去重
//		DetachedCriteria dc = DetachedCriteria.forClass(Departmentinfo.class);
//		dc.setProjection(Projections.projectionList().add(Projections.groupProperty("college"))
//				.add(Projections.property("did")));
//		dc.add(Restrictions.in("college", coveredCollege));
//		dc.addOrder(Order.asc("college"));
		List<DepartmentQueryData> list = schoolBiz.findByCollegeListGroupByCollegeOrderByCollege(coveredCollege);

		// 准备返回结果
//		List<DepartmentQueryData> result = fillResultDataToMap(list);
		// 渲染结果
		renderSuccessResult(list, response);
		return "";
	}

	/**
	 * 查询学校下的学院
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/queryInstitute")
	public String queryInstitute(String id, HttpServletResponse response) {
		logger.info("api.queryInstitute invoke, id=" + id);
		// 检查参数
		String errMsg = validateParams(id);
		if (StringUtils.isNotBlank(errMsg)) {
			renderErrResult(errMsg, response);
			return "";
		}

		// 查询学校下的所有学院
		List list = querySpecifiedInstitute(id);

		// 准备返回结果
//		List<DepartmentQueryData> result = fillResultDataToMap(list);
		// 渲染结果
		renderSuccessResult(list, response);
		return "";
	}

	// 查找学校下面的所有学院
	private List<DepartmentQueryData> querySpecifiedInstitute(String id) {
		Integer departId = Integer.parseInt(id);
		Departmentinfo di = this.schoolBiz.findDepartmentInfoById(departId);
		if (di == null) {
			// 如果查不到院校信息，则返回空list
			logger.warn("api invoke warn, no such departmentInfo where id = " + departId);
			return new ArrayList<DepartmentQueryData>();
		}
		// DetachedCriteria dc =
		// DetachedCriteria.forClass(Departmentinfo.class);
		// dc.add(Restrictions.eq("college", di.getCollege()));
		// dc.setProjection(Projections.projectionList()
		// .add(Projections.groupProperty("institute"))
		// .add(Projections.property("did")));
		// dc.addOrder(Order.asc("college")).addOrder(Order.asc("institute"));
		return schoolBiz.findByCollegeGroupByInstituteOrderByInstitute(di.getCollege());
	}

	/**
	 * 查询指定学校、学院下的所有专业
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/queryDepartment")
	public String queryDepartmentInfo(String id, HttpServletResponse response) {
		logger.info("api.queryDepartmentInfo invoke, id=" + id);
		// 检查参数
		String errMsg = validateParams(id);
		if (StringUtils.isNotBlank(errMsg)) {
			renderErrResult(errMsg, response);
			return "";
		}

		// 查询指定学校、学院下的所有专业
		List<DepartmentQueryData> list = querySpecifiedDepartment(id);

		// 准备返回结果
//		List<DepartmentQueryData> result = fillResultDataToMap(list);
		// 渲染结果
		renderSuccessResult(list, response);
		return "";
	}

	// 查询指定学校、学院下的所有专业
	@SuppressWarnings("rawtypes")
	private List querySpecifiedDepartment(String id) {
		Integer departId = Integer.parseInt(id);
		Departmentinfo di = this.schoolBiz.findDepartmentInfoById(departId);
		if (di == null) {
			// 如果查不到院校信息，则返回空list
			logger.warn("api invoke warn, no such departmentInfo where id = " + departId);
			return new ArrayList<Departmentinfo>();
		}
		// DetachedCriteria dc =
		// DetachedCriteria.forClass(Departmentinfo.class);
		// dc.add(Restrictions.eq("college", di.getCollege()));
		// dc.add(Restrictions.eq("institute", di.getInstitute()));
		// dc.setProjection(Projections.projectionList()
		// .add(Projections.groupProperty("partName"))
		// .add(Projections.property("did")));
		// dc.addOrder(Order.asc("college")).addOrder(Order.asc("institute"))
		// .addOrder(Order.asc("partName"));
		return schoolBiz.findByCollegeAndInstituteGroupByPartNameOrderByPartName(di.getCollege(), di.getInstitute());
	}

//	// 查询结果封装到map中
//	private List<DepartmentQueryData> fillResultDataToMap(List<DepartmentQueryData> list) {
//		List<DepartmentQueryData> result = new ArrayList<DepartmentQueryData>();
//		for (Object entry : list) {
//			Object[] data = (Object[]) entry;
//			// result.put(data[0].toString(),
//			// Integer.parseInt(data[1].toString()));
//			result.add(new DepartmentQueryData(Integer.parseInt(data[1].toString()), data[0].toString()));
//		}
//		return result;
//	}

	private String validateParams(String id) {
		if (StringUtils.isBlank(id)) {
			return "参数错误，id不能为空";
		}
		try {
			int did = Integer.parseInt(id);
			if (did <= 0) {
				return "参数错误，id必須大于0";
			}
		} catch (NumberFormatException e) {
			return "参数错误，id必须为数字";
		}
		return "";
	}

	protected void renderErrResult(String errMsg, HttpServletResponse resp) {
		DepartmentQueryResponse response = new DepartmentQueryResponse();
		response.setCode(BaseApiResponse.CODE_INNER_ERROR);
		response.setMsg("系统内部错误：" + errMsg);
		renderResult(response, resp);
	}

	protected void renderSuccessResult(List<DepartmentQueryData> result, HttpServletResponse resp) {
		DepartmentQueryResponse response = new DepartmentQueryResponse();
		response.setCode(BaseApiResponse.CODE_SUCCESS);
		response.setResultList(result);
		renderResult(response, resp);
	}
}
