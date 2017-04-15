package com.zis.shop.api;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.bean.BookinfoDetail;
import com.zis.bookinfo.service.BookService;
import com.zis.shop.api.impl.ApiTransferFactory;
import com.zis.shop.bean.ShopInfo;
import com.zis.shop.bean.ShopItemMapping;
import com.zis.shop.bo.ShopAddItemsBo;
import com.zis.shop.bo.impl.ShopAddItemsFactoryBo;
import com.zis.shop.bo.impl.ShopBoImpl;
import com.zis.shop.dto.ApiAddItemDto;
import com.zis.shop.repository.ShopInfoDao;
import com.zis.shop.repository.ShopItemMappingDao;
import com.zis.shop.service.impl.ShopServiceImpl;

/**
 * 
 * api调用接口
 * 
 * @author think
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:spring/spring.xml", "classpath:spring/shiro.xml" })
@TransactionConfiguration(defaultRollback = true)
public class ApiTransferTest {

	final String FOR_SHELVED = "for_shelved";// 下架
	final String SOLD_OUT = "sold_out";// 售罄

	@Autowired
	private ShopItemMappingDao shopItemMappingDao;

	@Autowired
	private ShopInfoDao shopInfoDao;

	@Autowired
	private BookService bookService;

	@Autowired
	private ApiTransferFactory addFactory;

	@Autowired
	private ShopAddItemsFactoryBo addItemsFactoryBo;

	@Autowired
	private ShopBoImpl shopBoImpl;

	@Autowired
	private ShopServiceImpl shopServiceImpl;

	// @Test
	// public void queryItemsOnsale() {
	// ShopInfo shop = getShopInfo();
	// ApiTransfer api = addFactory.getInstance(shop.getpName());
	// this.shopServiceImpl.queryItemsOnsale(api, shop);
	// }
	@Test
	public void queryItemsInventoryForShelved() {
		ShopInfo shop = getShopInfo();
		ApiTransfer api = addFactory.getInstance(shop.getpName());
		this.shopServiceImpl.queryItemsInventory(api, shop, FOR_SHELVED);
	}

	@Test
	public void queryItemsInventorySoldOut() {
		ShopInfo shop = getShopInfo();
		ApiTransfer api = addFactory.getInstance(shop.getpName());
		this.shopServiceImpl.queryItemsInventory(api, shop, SOLD_OUT);
	}

	@Test
	public void addItem2Shop() {
		ShopInfo shop = getShopInfo();
		// List<Integer> list = new ArrayList<Integer>();
		// list.add(19);
		this.shopServiceImpl.addItem2Shop(467, shop);
	}

	@Test
	public void addProcessingItems() {
		ShopInfo shop = getShopInfo();
		List<Integer> list = new ArrayList<Integer>();
		list.add(19);
		this.shopBoImpl.addProcessingItems(list, shop);
	}

	@Test
	public void ShopAdd() {
		ShopInfo shop = getShopInfo();
		ApiAddItemDto apiAddItemDto = getApiAddItemDto(shop);
		List<ApiAddItemDto> list = new ArrayList<ApiAddItemDto>();
		list.add(apiAddItemDto);
		ShopAddItemsBo bo = addItemsFactoryBo.getInstance(shop.getpName());
		bo.AddItems2Shop(list, shop);
	}

	@Test
	public void addItem() {
		ShopInfo shop = getShopInfo();
		ApiAddItemDto apiAddItemDto = getApiAddItemDto(shop);
		ApiTransfer api = addFactory.getInstance(shop.getpName());
		api.addItem(apiAddItemDto, shop);
	}

	private ApiAddItemDto getApiAddItemDto(ShopInfo shop) {
		ShopItemMapping mapping = this.shopItemMappingDao.findOne(19);
		ApiAddItemDto dto = getDto2DBorNet(mapping, shop);
		dto.setStockBalance(1);
		return dto;
	}

	private ShopInfo getShopInfo() {
		return this.shopInfoDao.findOne(1);
	}

	private ApiAddItemDto getDto2DBorNet(ShopItemMapping mapping, ShopInfo shop) {
		ApiAddItemDto dto = new ApiAddItemDto();
		dto.setShopItemMapping(mapping);
		dto.setDeliveryTemplateId(shop.getDeliveryTemplateId());
		Bookinfo book = this.bookService.findBookById(mapping.getBookId());
		BookinfoDetail detail = bookService.findBookInfoDetailByBookId(book.getId());
		// 如果没有图书详情，则从网上采集
		if (detail == null) {
			try {
				detail = bookService.captureBookInfoDetailFromNet(book.getId());
			} catch (Exception e) {
				// 单条错误不能影响全部记录
				String errorMsg = "[数据采集] 采集过程中发生错误，bookId=" + book.getId();
				e.printStackTrace();
			}
		}
		// 如果没有采集到图书详情，则跳过此条记录
		if (detail == null) {
			return null;
		}
		BeanUtils.copyProperties(book, dto);
		BeanUtils.copyProperties(detail, dto);
		return dto;
	}
	// /**
	// * 更新商品
	// *
	// * @return
	// */
	// public boolean updateItem(ApiUpdateItemDto apiUpdateItemDto, ShopInfo
	// shop);
	//
	// /**
	// * 获取在售商品每50个1次
	// *
	// * @param ItemOutNum
	// * @return
	// */
	// public ApiQueryItemsDto queryItemsOnsale(ShopInfo shop, Long page);
	//
	// /**
	// * 获取仓库或售罄商品每50个1次
	// *
	// * @param shop
	// * @param type
	// * 可选值：for_shelved（已下架的）/ sold_out（已售罄的）
	// * @return
	// */
	// public ApiQueryItemsDto queryItemsInventory(ShopInfo shop, String type,
	// Long page);
}
