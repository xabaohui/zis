package com.zis.shop.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zis.common.mvc.ext.Token;
import com.zis.shiro.dto.ActiveUser;
import com.zis.shop.bean.ShopInfo;
import com.zis.shop.bean.ShopInfo.ShopInfoStatus;
import com.zis.shop.service.impl.ShopServiceImpl;

//@Controller
//@RequestMapping(value = "/shop")
@Deprecated
public class ShopWaitUpLoadController {

//	@Autowired
	private ShopServiceImpl shopService;

	/**
	 * 获取批次Id 集合作为页面展示
	 * 
	 * @param map
	 * @param shopId
	 * @return
	 */
//	@Token(generate = true)
//	@RequestMapping(value = "/getShopWaitUpLoad")
//	public String getShopWaitUpLoad(ModelMap map, Integer shopId, HttpServletRequest request) {
//		ShopInfo shop = null;
//		try {
//			// 获取状态，并检查此shopId是否合法
//			shop = this.shopService.verifyShopId(shopId);
//			map.put("shopId", shop.getShopId());
//			map.put("shopStatus", shop.getStatus());
//			List<ShopInfo> shopList = getCompanyAllShop();
//			map.put("shopList", shopList);
//			// 如果是正常店铺
//			if (ShopInfoStatus.NORMAL.getValue().equals(shop.getStatus())) {
//				pageInfo(request, map, shop.getShopId());
//				return "shop/shop/shopWaitUpload/shop-wait-upload";//
//			} else if (ShopInfoStatus.NORMAL.getValue().equals(shop.getStatus())) {
//				// 如果是新店铺
//				return "shop/shop/shopWaitUpload/shop-wait-upload";
//			} else {
//				// 店铺异常
//				map.put("actionError", "店铺状态异常");
//				return "error";
//			}
//		} catch (Exception e) {
//			map.put("actionError", e.getMessage());
//			return "error";
//		}
//	}

	/**
	 * 根据店铺Id 和批次Id 批量添加映射表
	 * 
	 * @param shopId
	 * @param batchId
	 * @param map
	 * @return
	 */
//	@Token(checking = true)
//	@RequestMapping(value = "/addShopItemMapping")
	public String addShopItemMapping(Integer shopId, Integer[] batchIds, ModelMap map) {
		ShopInfo shop = null;
		try {
			// 获取状态，并检查此shopId是否合法
			shop = this.shopService.verifyShopId(shopId);
			if (batchIds == null && ShopInfoStatus.NORMAL.getValue().equals(shop.getStatus())) {
				map.put("actionError", "批次选择不能为空");
				return "forward:/shop/getShopWaitUpLoad?shopId=" + shopId;
			}
			map.put("shopId", shop.getShopId());
			// 如果是正常店铺
			if (ShopInfoStatus.NORMAL.getValue().equals(shop.getStatus())) {
				// 设置页面信息查询wait 跳转
				return "";// 到店铺商品查询页面
			} else if (ShopInfoStatus.NORMAL.getValue().equals(shop.getStatus())) {
				// 如果是新店铺 新起线程添加
//				this.shopService.asynchronousAddAllBooksToShopItemMapping(shop);
				// 跳转 等待页面提示成功
				map.put("actionMessage", "导入进行中。。。,您可以先处理其他店铺。");
				map.put("shopStatus", shop.getStatus());
				List<ShopInfo> shopList = getCompanyAllShop();
				map.put("shopList", shopList);
				return "shop/shop/shopWaitUpload/shop-wait-upload";
			} else {
				// 店铺异常
				map.put("actionError", "店铺状态异常");
				return "error";
			}
		} catch (Exception e) {
			map.put("actionError", e.getMessage());
			return "error";
		}
	}

	/**
	 * 获取公司所有店铺
	 * 
	 * @return
	 */
	private List<ShopInfo> getCompanyAllShop() {
		return this.shopService.findCompanyShop(getCompanyId());
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

//	/**
//	 * 待处理批次分页
//	 * 
//	 * @param request
//	 * @param map
//	 * @param shopId
//	 */
//	private void pageInfo(HttpServletRequest request, ModelMap map, Integer shopId) {
//		Pageable page = WebHelper.buildPageRequest(request);
//		Page<ShopWaitUpload> batchList = this.shopService.findShopWaitUploadByShopId(getCompanyId(), shopId, page);
//		if (!batchList.getContent().isEmpty()) {
//			List<ShopWaitUpload> list = batchList.getContent();
//			map.put("batchList", list);
//			map.put("page", page.getPageNumber() + 1);
//			setQueryConditionToPage(shopId, map);
//			if (batchList.hasPrevious()) {
//				map.put("prePage", page.previousOrFirst().getPageNumber());
//			}
//			if (batchList.hasNext()) {
//				map.put("nextPage", page.next().getPageNumber());
//			}
//		} else {
//			map.put("notResult", "未找到需要处理的批次");
//		}
//	}

	/**
	 * 上下页查询get提交组装
	 * 
	 * @param shopId
	 * @param map
	 */
	private void setQueryConditionToPage(Integer shopId, ModelMap map) {
		StringBuilder condition = new StringBuilder();
		if (shopId != null) {
			condition.append("shopId=" + shopId + "&");
		}
		map.put("queryCondition", condition.toString());
	}
}
