package com.zis.shop.api;

import java.util.Date;
import java.util.List;

import com.zis.shop.bean.ShopInfo;
import com.zis.shop.dto.ApiAddItemDto;
import com.zis.shop.dto.ApiQueryItemsDto;
import com.zis.shop.dto.ApiUpdateItemDto;
import com.zis.shop.dto.ApplyRefundDTO;
import com.zis.shop.dto.LogisticsOfflineSendDTO;
import com.zis.trade.dto.CreateTradeOrderDTO;

/**
 * 
 * api调用接口
 * 
 * @author think
 * 
 */
public interface ApiTransfer {

	/**
	 * 添加商品
	 * 
	 * @param shopItem
	 * @return
	 */
	public Long addItem(ApiAddItemDto apiAddItemDto, ShopInfo shop);

	/**
	 * 更新商品
	 * 
	 * @return
	 */
	public boolean updateItem(ApiUpdateItemDto apiUpdateItemDto, ShopInfo shop);

	// /**
	// * 根据商品编号获取商品
	// *
	// * @param ItemOutNum
	// * @return
	// */
	// public List<ApiItemDto> queryItemByItemOutNum(String ItemOutNum, ShopInfo
	// shop);

	// /**
	// * 根据平台商品Id获取商品
	// *
	// * @param ItemOutNum
	// * @return
	// */
	// public ApiItemDto queryItemByNumId(String numId, ShopInfo shop);

	/**
	 * 获取在售商品每50个1次
	 * 
	 * @param ItemOutNum
	 * @return
	 */
	public ApiQueryItemsDto queryItemsOnsale(ShopInfo shop, Long page);

	/**
	 * 获取仓库或售罄商品每50个1次
	 * 
	 * @param shop
	 * @param type
	 *            可选值：for_shelved（已下架的）/ sold_out（已售罄的）
	 * @return
	 */
	public ApiQueryItemsDto queryItemsInventory(ShopInfo shop, String type, Long page);

	/**
	 * 根据开始和结束时间查询订单
	 * 
	 * @param shop
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<CreateTradeOrderDTO> queryTradeForDate(ShopInfo shop,
			Date startTime, Date endTime);

	/**
	 * 根据开始和结束时间查询退款
	 * 
	 * @param shop
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<ApplyRefundDTO> queryApplyRefundForDate(ShopInfo shop, Date startTime, Date endTime);

	/**
	 * 确认发货(回填单号使用)
	 * 
	 * @param shop
	 * @param logisticsOfflineSendDTO
	 * @return
	 */
	public boolean logisticsOfflineSend(ShopInfo shop, LogisticsOfflineSendDTO logisticsOfflineSendDTO);

	// public ApiQuerytTradeDto taobao.trades.sold.increment.get

	// /**
	// * 根据平台商品Id 上架商品
	// *
	// * @param NumId
	// * @return
	// */
	// public boolean upLoadItem(ShopItemMapping shopItemMapping, ShopInfo
	// shop);

	/**
	 * 网店无法找到商品的 ErrorResponse 信息
	 * 
	 * @author think
	 * 
	 */
	public enum NotItem {

		YOU_ZAN("商品不存在", "ErrorResponse{code='50000', msg='商品不存在'}"), TAO_BAO("商品不存在", "商品id对应的商品不存在");

		private String value;
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		private NotItem(String value, String name) {
			this.value = value;
			this.name = name;
		}
	}
}
