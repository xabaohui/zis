package com.zis.shop.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.zis.common.mvc.ext.Token;
import com.zis.common.mvc.ext.WebHelper;
import com.zis.shop.bean.ShopInfo;
import com.zis.shop.bean.ShopItemMapping;
import com.zis.shop.bean.ShopItemMapping.ShopItemMappingSystemStatus;
import com.zis.shop.service.ShopService;
import com.zis.shop.util.ShopUtil;

@Controller
@RequestMapping(value = "/shop")
public class ShopItemMappingController {

	private static Logger logger = Logger.getLogger(ShopItemMappingController.class);

	private final String MAPPING_WAIT = ShopItemMappingSystemStatus.WAIT.getValue();

	@Autowired
	private ShopService shopService;

	/**
	 * 单个发布
	 * 
	 * @param shopId
	 * @param mId
	 * @return
	 */
	@Token(checking = true)
	@RequestMapping(value = "/addOneItem2Shop")
	public String addOneItem2Shop(Integer shopId, Integer mId, ModelMap map, String mappingStatus) {
		try {
			ShopInfo shop = this.shopService.verifyShopId(shopId);
			Integer i = this.shopService.addItem2Shop(mId, shop);
			setSuccessMsg(map, "发布成功 共发布了" + i + "条数据");
			setPageParam(map, shop, mappingStatus);
			return getSuccessForWardPage();
		} catch (Exception e) {
			setErrorMsg(map, e.getMessage());
			logger.error(e.getMessage(), e);
			return getErrorPage();
		}
	}

	/**
	 * 失败单个发布
	 * 
	 * @param shopId
	 * @param mId
	 * @return
	 */
	@Token(checking = true)
	@RequestMapping(value = "/failAddOneItem2Shop")
	public String failAddOneItem2Shop(Integer shopId, Integer mId, ModelMap map, String mappingStatus) {
		try {
			ShopInfo shop = this.shopService.verifyShopId(shopId);
			Integer i = this.shopService.failAddItem2Shop(mId, shop);
			setSuccessMsg(map, "发布成功 共发布了" + i + "条数据");
			return getSuccessForWardPage();
		} catch (Exception e) {
			setErrorMsg(map, e.getMessage());
			logger.error(e.getMessage(), e);
			return getErrorPage();
		}
	}

	/**
	 * 全部发布
	 * 
	 * @param shopId
	 * @param mId
	 * @return
	 */
	@Token(checking = true)
	@RequestMapping(value = "/addItemAll2Shop")
	public String addItemAll2Shop(Integer shopId, ModelMap map, String mappingStatus) {
		try {
			ShopInfo shop = this.shopService.verifyShopId(shopId);
			Integer i = this.shopService.addItem2Shop(shop);
			setSuccessMsg(map, "正在尝试" + i + "条数据发布，具体根据实际成功查询");
			return getSuccessForWardPage();
		} catch (Exception e) {
			setErrorMsg(map, e.getMessage());
			logger.error(e.getMessage(), e);
			return getErrorPage();
		}
	}

	/**
	 * 失败全部发布
	 * 
	 * @param shopId
	 * @param mId
	 * @return
	 */
	@Token(checking = true)
	@RequestMapping(value = "/failAddItemAll2Shop")
	public String failAddItemAll2Shop(Integer shopId, ModelMap map, String mappingStatus) {
		try {
			ShopInfo shop = this.shopService.verifyShopId(shopId);
			Integer i = this.shopService.failAddItem2Shop(shop);
			setSuccessMsg(map, "正在尝试" + i + "条数据发布，具体根据实际成功查询");
			return getSuccessForWardPage();
		} catch (Exception e) {
			setErrorMsg(map, e.getMessage());
			logger.error(e.getMessage(), e);
			return getErrorPage();
		}
	}

	/**
	 * 批量发布
	 * 
	 * @param shopId
	 * @param mId
	 * @return
	 */
	@Token(checking = true)
	@RequestMapping(value = "/addItems2Shop")
	public String addItems2Shop(Integer shopId, Integer[] mId, ModelMap map, String mappingStatus) {
		try {
			ShopInfo shop = this.shopService.verifyShopId(shopId);
			List<Integer> list = Arrays.asList(mId);
			Integer i = this.shopService.addItem2Shop(list, shop);
			setSuccessMsg(map, "发布成功 共发布了" + i + "条数据");
			return getSuccessForWardPage();
		} catch (Exception e) {
			setErrorMsg(map, e.getMessage());
			logger.error(e.getMessage(), e);
			return getErrorPage();
		}
	}

	/**
	 * 失败批量发布
	 * 
	 * @param shopId
	 * @param mId
	 * @return
	 */
	@Token(checking = true)
	@RequestMapping(value = "/failAddItems2Shop")
	public String failAddItems2Shop(Integer shopId, Integer[] mId, ModelMap map, String mappingStatus) {
		try {
			ShopInfo shop = this.shopService.verifyShopId(shopId);
			List<Integer> list = Arrays.asList(mId);
			Integer i = this.shopService.failAddItem2Shop(list, shop);
			setSuccessMsg(map, "发布成功 共发布了" + i + "条数据");
			return getSuccessForWardPage();
		} catch (Exception e) {
			setErrorMsg(map, e.getMessage());
			logger.error(e.getMessage(), e);
			return getErrorPage();
		}
	}

	// /**
	// * 有赞下载数据
	// *
	// * @param shopId
	// * @return
	// */
	// //TODO 有赞暂时不做
	// @Token(checking = true)
	// @RequestMapping(value = "/youZanDownloadItems2Mapping")
	// public String youZanDownloadItems2Mapping(Integer shopId, ModelMap map) {
	// try {
	// ShopInfo shop = this.shopService.verifyShopId(shopId);
	// if(!"youzan".equals(shop.getpName())){
	// setErrorMsg(map, "店铺非有赞店铺");
	// return getErrorPage();
	// }
	// this.shopService.asynchronousDownloadYouZan2ShopItemMapping(shop);
	// setSuccessMsg(map, "数据正在努力下载中。。。您可以先处理其他店铺.");
	// return getSuccessForWardPage();
	// } catch (Exception e) {
	// setErrorMsg(map, e.getMessage());
	// logger.error(e.getMessage(), e);
	// return getErrorPage();
	// }
	// }

	/**
	 * 淘宝下载数据
	 * 
	 * @param excelFile
	 * @param shopId
	 * @param map
	 * @return
	 */
	@Token(checking = true)
	@RequestMapping(value = "/taobaoDownloadItems2Mapping")
	public String taobaoDownloadItems2Mapping(@RequestParam MultipartFile excelFile, Integer shopId, ModelMap map) {
		ShopInfo shop = null;
		try {
			shop = this.shopService.verifyShopId(shopId);
			if (!"taobao".equals(shop.getpName())) {
				setErrorMsg(map, "店铺非淘宝店铺");
				return getErrorPage();
			}
			List<ShopItemMapping> list = this.shopService.taobaoExeclToMapping(excelFile.getInputStream(), shop);
			this.shopService.asynchronousPrcessDownLoadMappingDataAndSendEmail(list, shop);
			setSuccessMsg(map, "数据正在努力下载中。。。您可以先处理其他店铺.");
			return getSuccessForWardPage();
		} catch (Exception e) {
			setErrorMsg(map, e.getMessage());
			logger.error(e.getMessage(), e);
			return getErrorPage();
		}
	}

	@Token(generate = true)
	@RequestMapping(value = "/gotoTaobaoDownLoadJsp")
	public String gotoTaobaoDownLoadJsp(Integer shopId, ModelMap map) {
		map.put("shopId", shopId);
		return "shop/shop/shopItemMapping/taobao-download-xls";
	}

	/**
	 * 查询映射表
	 * 
	 * @param shopId
	 *            店铺Id
	 * @param status
	 *            系统状态
	 * @param isbn
	 *            条形码
	 * @return
	 */
	@Token(generate = true)
	@RequestMapping(value = "/queryShopItemMapping")
	public String queryShopItemMapping(HttpServletRequest request, Integer shopId, String status, String isbn,
			ModelMap map) {
		ShopInfo shop = null;
		try {
			shop = this.shopService.verifyShopId(shopId);
			setPageParam(map, shop, status);
			pageInfo(request, map, shopId, status, isbn);
			return getSuccessPage();
		} catch (Exception e) {
			setErrorMsg(map, e.getMessage());
			logger.error(e.getMessage(), e);
			return getErrorPage();
		}
	}

	/**
	 * 成功转发路径
	 * 
	 * @return
	 */
	private String getSuccessForWardPage() {
		return "forward:/shop/queryShopItemMapping";
	}

	/**
	 * 成功跳转路径
	 * 
	 * @return
	 */
	private String getSuccessPage() {
		return "shop/shop/shopItemMapping/shop-item-mapping";
	}

	// /**
	// * 失败路径
	// *
	// * @return
	// */
	// private String getFailPage() {
	// return "forward:/shop/queryShopItemMapping";
	// }

	/**
	 * 错误跳转
	 * 
	 * @return
	 */
	private String getErrorPage() {
		return "error";
	}

	/**
	 * 跳转后页面显示参数
	 * 
	 * @param map
	 * @param msg
	 * @param shop
	 * @param mappingStatus
	 */
	private void setPageParam(ModelMap map, ShopInfo shop, String mappingStatus) {
		List<String> statusList = this.shopService.getStatusList();
		if (!statusList.contains(mappingStatus)) {
			mappingStatus = MAPPING_WAIT;
		}
		List<ShopInfo> shopList = this.shopService.findCompanyShop(ShopUtil.getCompanyId());
		map.put("shopId", shop.getShopId());
		map.put("shopList", shopList);
		map.put("mappingStatus", mappingStatus);
		map.put("shopPName", shop.getpName());
	}

	/**
	 * 设置失败信息
	 * 
	 * @param map
	 * @param msg
	 */
	private void setErrorMsg(ModelMap map, String msg) {
		map.put("actionError", msg);
	}

	/**
	 * 设置成功信息
	 * 
	 * @param map
	 * @param msg
	 */
	private void setSuccessMsg(ModelMap map, String msg) {
		map.put("actionMessage", msg);
	}

	/**
	 * 分页查询
	 * 
	 * @param request
	 * @param map
	 * @param shopId
	 * @param status
	 * @param isbn
	 */
	private void pageInfo(HttpServletRequest request, ModelMap map, Integer shopId, String status, String isbn) {
		Pageable page = WebHelper.buildPageRequest(request);
		Page<ShopItemMapping> mappingList = this.shopService.queryShopItemMapping(shopId, status, isbn, page);
		if (!mappingList.getContent().isEmpty()) {
			List<ShopItemMapping> list = mappingList.getContent();
			map.put("mappingList", list);
			map.put("page", page.getPageNumber() + 1);
			setQueryConditionToPage(shopId, status, map);
			if (mappingList.hasPrevious()) {
				map.put("prePage", page.previousOrFirst().getPageNumber());
			}
			if (mappingList.hasNext()) {
				map.put("nextPage", page.next().getPageNumber());
			}
		} else {
			map.put("notResult", "未找到需要的结果");
		}
	}

	/**
	 * 上下页查询get提交组装
	 * 
	 * @param shopId
	 * @param map
	 */
	private void setQueryConditionToPage(Integer shopId, String shopStatus, ModelMap map) {
		StringBuilder condition = new StringBuilder();
		if (shopId != null) {
			condition.append("shopId=" + shopId + "&");
		}
		if (StringUtils.isNotBlank(shopStatus)) {
			condition.append("status=" + shopStatus + "&");
		}
		map.put("queryCondition", condition.toString());
	}
}
