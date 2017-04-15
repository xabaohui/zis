package com.zis.shop.api.impl;

import com.jsb.rest.client.JSBClient;
import com.jsb.rest.comm.JSBRestException;
import com.taobao.api.request.ItemUpdateListingRequest;
import com.taobao.api.request.ItemUpdateRequest;
import com.taobao.api.response.ItemUpdateListingResponse;
import com.taobao.api.response.ItemUpdateResponse;
import com.zis.shop.bean.ShopInfo;
import com.zis.shop.dto.ApiAddItemDto;
import com.zis.shop.dto.ApiQueryItemsDto;
import com.zis.shop.dto.ApiUpdateItemDto;

public class JiShiBaoApiTransfer extends AbstractApiTransfer {

	@Override
	public Long addItem(ApiAddItemDto apiAddItemDto, ShopInfo shop) {
		return null;
	}
	
	@Override
	public boolean updateItem(ApiUpdateItemDto apiItemDto, ShopInfo shop) {
		JSBClient client = getClient(shop);
		ItemUpdateRequest req = new ItemUpdateRequest();
		req.setNumIid(apiItemDto.getpItemId());
		req.setNum((long) apiItemDto.getAmount());
		ItemUpdateResponse rsp = null;
		try {
			rsp = client.execute(req);
			if (rsp.isSuccess()) {
				// 成功后强制上架
				uploadItem(apiItemDto, shop);
				return true;
			} else {
				throw new RuntimeException(rsp.getSubMsg());
			}
		} catch (JSBRestException e) {
			throw new RuntimeException("更新商品失败：" + e.getMessage());
		}
	}

	@Override
	public ApiQueryItemsDto queryItemsOnsale(ShopInfo shop, Long page) {
		return null;
	}

	@Override
	public ApiQueryItemsDto queryItemsInventory(ShopInfo shop, String type, Long page) {
		return null;
	}

	private boolean uploadItem(ApiUpdateItemDto apiItemDto, ShopInfo shop) {
		JSBClient client = getClient(shop);
		ItemUpdateListingRequest req = new ItemUpdateListingRequest();
		req.setNumIid(apiItemDto.getpItemId());
		req.setNum((long) apiItemDto.getAmount());
		ItemUpdateListingResponse rsp = null;
		try {
			rsp = client.execute(req);
			if (rsp.isSuccess()) {
				return true;
			} else {
				throw new RuntimeException("商品上传失败：" + rsp.getMsg() + rsp.getSubMsg());
			}
		} catch (JSBRestException e) {
			throw new RuntimeException("商品上传失败：" + e.getMessage());
		}

	}

	/**
	 * 获取集市宝的client
	 * 
	 * @param shop
	 * @return
	 */
	private JSBClient getClient(ShopInfo shop) {
		return new JSBClient(shop.getAppId(), shop.getAppSecret());
	}
}
