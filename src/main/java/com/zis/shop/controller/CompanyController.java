package com.zis.shop.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zis.common.mvc.ext.WebHelper;
import com.zis.shiro.dto.ActiveUser;
import com.zis.shiro.dto.RegistUserDto;
import com.zis.shiro.dto.UpdateUserInfo;
import com.zis.shiro.service.RegistAndUpdateService;
import com.zis.shop.bean.Company;
import com.zis.shop.bean.ShopInfo;
import com.zis.shop.dto.SaveOrUpdateCompanyDto;
import com.zis.shop.service.ShopService;

@Controller
@RequestMapping(value = "/shop")
public class CompanyController {

	private static Logger logger = Logger.getLogger(CompanyController.class);

	private final String OPERATE_TYPE_UPDATE = "updateCompany";
	private final String OPERATE_TYPE_ADD = "addCompany";

	@Autowired
	private ShopService shopService;

	@Autowired
	private RegistAndUpdateService registAndUpdateService;

	/**
	 * 查询公司
	 * 
	 * @param companyName
	 * @param contacts
	 * @param request
	 * @param map
	 * @return
	 */
	@RequiresPermissions(value = { "shiro:shiro" })
	@RequestMapping(value = "/showCompanys")
	public String showCompanys(String companyName, String contacts, HttpServletRequest request, ModelMap map) {
		Pageable page = WebHelper.buildPageRequest(request);
		Page<Company> companyList = this.shopService.queryCompany(companyName, contacts, page);
		if (!companyList.getContent().isEmpty()) {
			List<Company> list = companyList.getContent();
			map.put("companyList", list);
			map.put("page", page.getPageNumber() + 1);
			setQueryConditionToPage(companyName, contacts, map);
			if (companyList.hasPrevious()) {
				map.put("prePage", page.previousOrFirst().getPageNumber());
			}
			if (companyList.hasNext()) {
				map.put("nextPage", page.next().getPageNumber());
			}
			return "shop/company/show/show-update-company-list";
		}
		map.put("notResult", "未找到结果,您输入的公司名称或者联系人不在服务区");
		return "shop/company/show/show-update-company-list";
	}

	/**
	 * 管理员新增公司信息辅助跳转action
	 * 
	 * @param map
	 * @return
	 */
	@RequiresPermissions(value = { "shiro:shiro" })
	@RequestMapping(value = "/gotoSaveCompany")
	public String gotoSaveCompany(ModelMap map) {
		SaveOrUpdateCompanyDto dto = new SaveOrUpdateCompanyDto();
		dto.setTypeStatus(OPERATE_TYPE_ADD);
		map.put("company", dto);
		return "shop/company/saveOrUpdate/save-or-update-company";
	}

	/**
	 * 管理员更新公司信息辅助跳转action
	 * 
	 * @param map
	 * @param companyId
	 * @return
	 */
	@RequiresPermissions(value = { "shiro:shiro" })
	@RequestMapping(value = "/gotoUpdateCompany")
	public String gotoUpdateCompany(ModelMap map, Integer companyId) {
		SaveOrUpdateCompanyDto dto = new SaveOrUpdateCompanyDto();
		Company company = this.shopService.findCompanyOne(companyId);
		if (company == null) {
			String ms = "公司ID有误，请联系管理员 公司ID：" + companyId;
			map.put("actionError", ms);
			logger.error(ms + " 此JS可能被篡改");
			return "error";
		}
		dto.setCompanyId(company.getCompanyId());
		dto.setAddress(company.getAddress());
		dto.setCompanyName(company.getCompanyName());
		dto.setMobile(company.getMobile());
		dto.setContacts(company.getContacts());
		dto.setTypeStatus(OPERATE_TYPE_UPDATE);
		map.put("company", dto);
		return "shop/company/saveOrUpdate/save-or-update-company";
	}

	/**
	 * 管理员更新公司信息
	 * 
	 * @param dto
	 * @param br
	 * @param map
	 * @return
	 */
	@RequiresPermissions(value = { "shiro:shiro" })
	@RequestMapping(value = "/saveOrUpdateCompany")
	public String saveOrUpdateCompany(@Valid @ModelAttribute("dto") SaveOrUpdateCompanyDto dto, BindingResult br,
			ModelMap map) {
		map.put("company", dto);
		if (br.hasErrors()) {
			return "shop/company/saveOrUpdate/save-or-update-company";
		}
		try {
			if (OPERATE_TYPE_ADD.equals(dto.getTypeStatus())) {
				trimAndDeleteEnterDto(dto);
				this.shopService.saveCompany(dto);
				map.put("actionMessage", "[" + dto.getCompanyName() + "]" + "操作成功");
				return "shop/company/show/show-update-company-list";
			} else if (OPERATE_TYPE_UPDATE.equals(dto.getTypeStatus())) {
				trimAndDeleteEnterDto(dto);
				this.shopService.updateCompany(dto);
				map.put("actionMessage", "[" + dto.getCompanyName() + "]" + "操作成功");
				return "shop/company/show/show-update-company-list";
			} else {
				throw new RuntimeException("不支持的操作类型: " + dto.getTypeStatus());
			}
		} catch (Exception e) {
			map.put("actionError", e.getMessage());
			logger.error(e.getMessage(), e);
			return "error";
		}
	}

	/**
	 * 使用者查看公司信息
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/showCompanyInfo")
	public String showCompanyInfo(ModelMap map) {
		Integer companyId = getCompanyId();
		Company company = this.shopService.findCompanyOne(companyId);
		if (company == null) {
			map.put("errorAction", "您没有公司请联系管理员添加公司");
			logger.error("您没有公司请联系管理员添加公司");
			return "error";
		}
		map.put("company", company);
		setCompanyInfoForJsp(map, companyId);
		return "shop/company/show/show-company-info";
	}

	/**
	 * 使用者更新公司信息辅助跳转action
	 * 
	 * @return
	 */
	@RequestMapping(value = "/gotoUpdateUserCompany")
	public String gotoUpdateUserCompany(ModelMap map) {
		Integer companyId = getCompanyId();
		Company company = this.shopService.findCompanyOne(companyId);
		if (company == null) {
			map.put("errorAction", "您没有公司请联系管理员添加公司");
			logger.error("您没有公司请联系管理员添加公司");
			return "error";
		}
		map.put("company", company);
		return "shop/company/saveOrUpdate/update-user-company";
	}

	/**
	 * 使用者更新公司信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updateUserCompany")
	public String updateUserCompany(@Valid @ModelAttribute("dto") SaveOrUpdateCompanyDto dto, BindingResult br,
			ModelMap map) {
		try {
			map.put("company", dto);
			if (br.hasErrors()) {
				return "shop/company/saveOrUpdate/update-user-company";
			}
			dto.setCompanyId(getCompanyId());
			this.shopService.updateCompany(dto);
			map.put("actionMessage", dto.getCompanyName() + " 修改成功");
			return "forward:/shop/showCompanyInfo";
		} catch (Exception e) {
			map.put("errorAction", e.getMessage());
			logger.error(e.getMessage(), e);
			return "error";
		}
	}

	/**
	 * 公司新建员工帮助跳转类
	 * 
	 * @return
	 */
	public String registCompanyUser() {

		return "shop/company/regist-company-user";
	}

	/**
	 * 公司新建员工
	 * 
	 * @param registUserDto
	 * @param br
	 * @return
	 */
	public String registCompanyUser(@Valid @ModelAttribute("registUserDto") RegistUserDto registUserDto,
			BindingResult br, ModelMap map, String typeName) {
		if (br.hasErrors()) {
			return "shop/company/regist-company-user";
		}
		String password = registUserDto.getPassword();
		String passwordAgain = registUserDto.getPasswordAgain();
		if (!password.equals(passwordAgain)) {
			map.put("passwordError", "两次密码不一致");
			return "shop/company/regist-company-user";
		}
		registUserDto.setCompanyId(getCompanyId());
		try {
			this.registAndUpdateService.saveOrUpdateCompanyUser(registUserDto);
		} catch (Exception e) {
			map.put("errorAction", e.getMessage());
			logger.error(e.getMessage(), e);
			return "shop/company/regist-company-user";
		}
		if (registUserDto.getId() == null) {
			map.put("actionMessage", "[" + registUserDto.getUserName() + "] 创建成功");
		} else {
			map.put("actionMessage", "[" + registUserDto.getUserName() + "] 修改成功");
		}
		return "shop/company/update-company";
	}

	private void setQueryConditionToPage(String companyName, String contacts, ModelMap map) {
		StringBuilder condition = new StringBuilder();
		if (StringUtils.isNotBlank(companyName)) {
			condition.append("companyName=" + companyName.trim() + "&");
		}
		if (StringUtils.isNotBlank(contacts)) {
			condition.append("contacts=" + contacts.trim() + "&");
		}
		map.put("queryCondition", condition.toString());
	}

	/**
	 * 去掉空格和回车
	 * 
	 * @param dto
	 */
	private void trimAndDeleteEnterDto(SaveOrUpdateCompanyDto dto) {
		dto.setCompanyName(DeleteEnter(dto.getCompanyName()));
		dto.setContacts(DeleteEnter(dto.getContacts()));
		dto.setMobile(DeleteEnter(dto.getMobile()));
	}

	private String DeleteEnter(String str) {
		String trimStr = str.trim();
		StringBuilder sb = new StringBuilder();
		if (trimStr.contains("\n")) {
			String[] s = trimStr.split("\n");
			for (String s1 : s) {
				sb.append(s1);
			}
		} else {
			sb.append(trimStr);
		}
		String s2 = sb.toString();
		if (s2.contains("\r")) {
			String[] s = s2.split("\r");
			sb = new StringBuilder();
			for (String s1 : s) {
				sb.append(s1);
			}
		}
		return sb.toString();
	}

	/**
	 * 将查询出仓储，店铺，用户，相关信息添加进页面展示
	 * 
	 * @param map
	 * @param dto
	 */
	private void setCompanyInfoForJsp(ModelMap map, Integer companyId) {
		// TODO 后续需要添加仓储 需在此添加仓储的查询方式
		List<ShopInfo> shopList = this.shopService.findCompanyShop(companyId);
		map.put("shopList", shopList);
		List<UpdateUserInfo> userList = this.registAndUpdateService.findAllUserByCompanyId(companyId);
		map.put("userList", userList);
	}

	/**
	 * 获取用户的公司ID
	 * 
	 * @return
	 */
	private Integer getCompanyId() {
		Subject user = SecurityUtils.getSubject();
		ActiveUser au = (ActiveUser) user.getPrincipals().getPrimaryPrincipal();
		return au.getCompanyId();
	}
}
