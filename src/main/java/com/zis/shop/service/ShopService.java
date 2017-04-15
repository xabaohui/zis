package com.zis.shop.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.zis.shop.bean.Company;
import com.zis.shop.bean.ShopInfo;
import com.zis.shop.bean.ShopItemMapping;
import com.zis.shop.dto.SaveOrUpdateCompanyDto;
import com.zis.shop.dto.SaveOrUpdateShopDto;

public interface ShopService {
	/**
	 * 库存变动更新商品（单个更新）
	 * 
	 * @param shop
	 * @param bookId
	 * @param amount
	 */
	// TODO 库存变动使用
	public void stockChange2ShopUPdateItem(ShopInfo shop, Integer bookId, Integer amount);

	/**
	 * 分页查找shopItemMapping
	 * 
	 * @param shopId
	 * @param status
	 * @param isbn
	 * @param page
	 * @return
	 */
	public Page<ShopItemMapping> queryShopItemMapping(Integer shopId, String status, String isbn, Pageable page);

	/**
	 * 获取所有shopItemMapping所有状态
	 * 
	 * @return
	 */
	public List<String> getStatusList();

	/**
	 * 获取所有店铺
	 * 
	 * @return
	 */
	// TODO 库存初始化数据使用
	public List<ShopInfo> findAllShop();

	/**
	 * 初始化店铺数据
	 * 
	 * @param bookId
	 * @param shop
	 */
	// TODO 库存初始化数据使用
	public void initShopMapping(Integer bookId, ShopInfo shop);

	/**
	 * 全部发布商品
	 * 
	 * @param shop
	 */
	public Integer addItem2Shop(ShopInfo shop);

	/**
	 * 批量发布商品
	 * 
	 * @param shop
	 */
	public Integer addItem2Shop(List<Integer> mappingIds, ShopInfo shop);

	/**
	 * 发布单个商品
	 * 
	 * @param mappingId
	 * @param shop
	 * @return
	 */
	public Integer addItem2Shop(Integer mappingId, ShopInfo shop);

	/**
	 * 异步下载有赞店铺信息，进行更新
	 * 
	 * @param shop
	 */
	public void asynchronousDownloadYouZan2ShopItemMapping(final ShopInfo shop);

	/**
	 * 异步下载淘宝数据
	 * 
	 * @param input
	 * @param shop
	 */
	public String asynchronousDownloadTaoBao2ShopItemMapping(final InputStream input, final ShopInfo shop);

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
	 * 新增公司
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
	 * 检查shopId
	 * 
	 * @param shopId
	 * @return 当前店铺或者公司的第一个店铺
	 */
	public ShopInfo verifyShopId(Integer shopId);
}
