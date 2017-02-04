package com.zis.shop.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.zis.shop.bean.Company;
import com.zis.shop.bean.ShopInfo;
import com.zis.shop.bean.ShopWaitUpload;
import com.zis.shop.dto.SaveOrUpdateCompanyDto;
import com.zis.shop.dto.SaveOrUpdateShopDto;

public interface ShopService {

	/**
	 * 查询公司
	 * 
	 * @param companyName
	 * @param contacts
	 * @param page
	 * @return
	 */
	public Page<Company> queryCompany(String companyName, String contacts, Pageable page);

	/**
	 * 查询单个公司
	 * 
	 * @param companyId
	 * @return
	 */
	public Company findCompanyOne(Integer companyId);

	/**
	 * 新增及修改公司
	 * 
	 * @param dto
	 */
	public void saveCompany(SaveOrUpdateCompanyDto dto);

	/**
	 * 修改公司
	 * 
	 * @param dto
	 */
	public void updateCompany(SaveOrUpdateCompanyDto dto);

	/**
	 * 查找 公司下的店铺
	 * 
	 * @param companyId
	 * @return
	 */
	public List<ShopInfo> findCompanyShop(Integer companyId);

	/**
	 * 新增店铺
	 * 
	 * @param dto
	 */
	public void saveShop(SaveOrUpdateShopDto dto);

	/**
	 * 修改店铺
	 * 
	 * @param dto
	 */
	public void updateShop(SaveOrUpdateShopDto dto);

	/**
	 * 删除店铺
	 * 
	 * @param companyId
	 * @param shopId
	 * @return
	 */
	public String deleteShop(Integer companyId, Integer shopId);

	/**
	 * 根据公司ID和店铺ID查询店铺
	 * 
	 * @param companyId
	 * @param shopId
	 * @return
	 */
	public ShopInfo findShopByShopIdAndCompanyId(Integer companyId, Integer shopId);

	/**
	 * 根据shopId查询所有批次处理情况
	 * 
	 * @param shopId
	 * @return
	 */
	public Page<ShopWaitUpload> findShopWaitUploadByShopId(Integer companyId, Integer shopId, Pageable page);
}
