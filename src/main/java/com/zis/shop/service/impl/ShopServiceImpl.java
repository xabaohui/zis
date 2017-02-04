package com.zis.shop.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zis.shiro.realm.CustomRealm;
import com.zis.shop.bean.Company;
import com.zis.shop.bean.ShopInfo;
import com.zis.shop.bean.ShopWaitUpload;
import com.zis.shop.dto.SaveOrUpdateCompanyDto;
import com.zis.shop.dto.SaveOrUpdateShopDto;
import com.zis.shop.repository.CompanyDao;
import com.zis.shop.repository.ShopInfoDao;
import com.zis.shop.repository.ShopWaitUploadDao;
import com.zis.shop.service.ShopService;

@Service
public class ShopServiceImpl implements ShopService {

	final String NORMAL = "normal";
	final String DELETE = "delete";
	final String NEW = "new";

	private static Logger logger = Logger.getLogger(ShopServiceImpl.class);

	@Autowired
	private ShopInfoDao shopInfoDao;

	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private ShopWaitUploadDao shopWaitUploadDao;

	/**
	 * 查询公司
	 * 
	 * @param companyName
	 * @param contacts
	 * @param page
	 * @return
	 */
	@Override
	public Page<Company> queryCompany(String companyName, String contacts, Pageable page) {
		if (!StringUtils.isBlank(companyName)) {
			return this.companyDao.findByLikeCompanyName(companyName.trim(), page);
		} else if (!StringUtils.isBlank(contacts)) {
			return this.companyDao.findByContacts(contacts.trim(), page);
		} else {
			return this.companyDao.findAllCompany(page);
		}
	}

	/**
	 * 查询单个公司
	 * 
	 * @param companyId
	 * @return
	 */
	@Override
	public Company findCompanyOne(Integer companyId) {
		return this.companyDao.findByCompanyId(companyId);
	}

	/**
	 * 新增公司
	 * 
	 * @param dto
	 */
	@Override
	@Transactional
	public void saveCompany(SaveOrUpdateCompanyDto dto) {
		if (dto.getCompanyId() != null) {
			throw new RuntimeException("新增公司，companyId应为空");
		}
		Company verifyCompany = this.companyDao.findByCompanyName(dto.getCompanyName());
		// 如果存在记录，防止重复提交
		if (verifyCompany != null) {
			return;
		}
		Company company = new Company();
		company.setCreateTime(new Date());
		company.setStatus(NORMAL);
		BeanUtils.copyProperties(company, dto);
		company.setUpdateTime(new Date());
		this.companyDao.save(company);
		clearAllCached();
	}

	/**
	 * 修改公司
	 * 
	 * @param dto
	 */
	@Override
	@Transactional
	public void updateCompany(SaveOrUpdateCompanyDto dto) {
		if (dto.getCompanyId() == null) {
			throw new RuntimeException("companyId为空");
		}
		Company company = this.companyDao.findByCompanyId(dto.getCompanyId());
		if (company != null) {
			BeanUtils.copyProperties(company, dto);
			company.setUpdateTime(new Date());
			this.companyDao.save(company);
			clearAllCached();
		} else {
			String message = "修改用户出错，请联系管理员" + "修改的公司为:[" + company + "]";
			logger.error(message);
			throw new RuntimeException(message);
		}
	}

	/**
	 * 查找 公司下的店铺
	 * 
	 * @param companyId
	 * @return
	 */
	@Override
	public List<ShopInfo> findCompanyShop(Integer companyId) {
		List<ShopInfo> list = this.shopInfoDao.findByCompanyId(companyId);
		return list;
	}

	/**
	 * 新增店铺
	 * 
	 * @param dto
	 */
	@Override
	@Transactional
	public void saveShop(SaveOrUpdateShopDto dto) {
		if (dto.getShopId() != null) {
			throw new RuntimeException("新增店铺，shopId应为空");
		}
		ShopInfo verifyShop = this.shopInfoDao.findByShopNameAndPNameAndCompanyId(dto.getShopName(), dto.getpName(),
				dto.getCompanyId());
		// 如果存在记录，防止重复提交
		if (verifyShop != null) {
			return;
		}
		ShopInfo shop = new ShopInfo();
		BeanUtils.copyProperties(dto, shop);
		shop.setDiscount(Double.parseDouble(dto.getDiscount()));
		shop.setUpdateTime(new Date());
		shop.setStatus(NEW);
		shop.setCreateTime(new Date());
		this.shopInfoDao.save(shop);
	}

	/**
	 * 修改店铺
	 * 
	 * @param dto
	 */
	@Override
	@Transactional
	public void updateShop(SaveOrUpdateShopDto dto) {
		if (dto.getShopId() == null) {
			throw new RuntimeException("shopId为空");
		}
		ShopInfo shop = this.shopInfoDao.findByCompanyIdAndShopId(dto.getCompanyId(), dto.getShopId());
		if (shop == null) {
			throw new RuntimeException("错误的shopId " + dto.getShopId());
		}
		BeanUtils.copyProperties(dto, shop);
		Double double1  = Double.parseDouble(dto.getDiscount());
		shop.setDiscount(double1);
		shop.setUpdateTime(new Date());
		this.shopInfoDao.save(shop);
	}

	/**
	 * 删除店铺
	 * 
	 * @param companyId
	 * @param shopId
	 * @return
	 */
	@Override
	public String deleteShop(Integer companyId, Integer shopId) {
		ShopInfo shop = this.shopInfoDao.findByCompanyIdAndShopId(companyId, shopId);
		if (shop != null) {
			shop.setStatus(DELETE);
			this.shopInfoDao.save(shop);
			return shop.getShopName();
		} else {
			throw new RuntimeException("您需要删除的店铺不存在，请联系管理员 店铺ID：" + shopId);
		}
	}

	/**
	 * 根据公司ID和店铺ID查询店铺
	 * 
	 * @param companyId
	 * @param shopId
	 * @return
	 */
	@Override
	public ShopInfo findShopByShopIdAndCompanyId(Integer companyId, Integer shopId) {
		return this.shopInfoDao.findByCompanyIdAndShopId(companyId, shopId);
	}

	/**
	 * 清除缓存
	 */
	private void clearAllCached() {
		RealmSecurityManager securityManager = (RealmSecurityManager) SecurityUtils.getSecurityManager();
		CustomRealm userRealm = (CustomRealm) securityManager.getRealms().iterator().next();
		userRealm.clearAllCachedAuthorizationInfo();
		userRealm.clearAllCachedAuthenticationInfo();
	}

	/**
	 * 查看店铺下待处理批次
	 */
	@Override
	public Page<ShopWaitUpload> findShopWaitUploadByShopId(Integer companyId, Integer shopId, Pageable page) {
		ShopInfo shop = this.shopInfoDao.findByCompanyIdAndShopId(companyId, shopId);
		if (shop == null) {
			throw new RuntimeException("您无法查看非本公司的店铺");
		}
		return this.shopWaitUploadDao.findByShopId(shopId, page);
	}
}
