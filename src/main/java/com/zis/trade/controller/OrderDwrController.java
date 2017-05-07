package com.zis.trade.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.service.BookService;
import com.zis.common.mvc.ext.QueryUtil;
import com.zis.common.util.SequenceCreator;
import com.zis.common.util.ZisUtils;
import com.zis.shop.bean.ShopInfo;
import com.zis.shop.service.ShopService;
import com.zis.storage.entity.StorageProduct;
import com.zis.storage.entity.StorageRepoInfo;
import com.zis.storage.service.StorageService;
import com.zis.storage.util.StorageUtil;
import com.zis.trade.dto.ArrangeOrderToRepoDTO;
import com.zis.trade.dto.BlockOrderDTO;
import com.zis.trade.dto.ChangeAddressDTO;
import com.zis.trade.dto.ChangeOrderAddressDTO;
import com.zis.trade.dto.CreateOrderQuerySkuInfoViewDTO;
import com.zis.trade.dto.CreateOrderQuerySkuInfoViewDTO.SkuInfo;
import com.zis.trade.dto.CreateOrderViewDTO;
import com.zis.trade.dto.CreateOrderViewDTO.SkuViewInfo;
import com.zis.trade.dto.FillExpressNumberDTO;
import com.zis.trade.dto.OrderVO;
import com.zis.trade.dto.RefundMemoDTO;
import com.zis.trade.dto.RemarkDTO;
import com.zis.trade.dto.SendOutViewDTO;
import com.zis.trade.dto.SkuInfoViewDTO;
import com.zis.trade.entity.Order.OrderType;
import com.zis.trade.service.OrderService;

@Controller
public class OrderDwrController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private StorageService storageService;

	@Autowired
	private BookService bookService;

	@Autowired
	private ShopService shopsService;

	private final String CREATE_OREDER_VIEW_MAP = "createOrderViewMap";

	/**
	 * 根据订单Id获取订单
	 * 
	 * @param orderId
	 * @return
	 */
	public ChangeOrderAddressDTO queryOrderByOrderId(Integer orderId) {
		// ChangeOrderAddressDTO dto = new ChangeOrderAddressDTO();
		// if (orderId == null) {
		// dto.setSuccess(false);
		// dto.setFailMessage("orderId不能为空");
		// return dto;
		// }
		// TODO
		// Order order = this.orderService.findByOrderIdAndCompanyId(orderId,
		// StorageUtil.getCompanyId());
		return getOrder();
	}

	/**
	 * 获取公司所有仓库单个订单
	 * 
	 * @return
	 */
	public ArrangeOrderToRepoDTO queryStorageRepoInfoByOnlyOrderId(Integer orderId, String forwardUrl) {
		ArrangeOrderToRepoDTO dto = new ArrangeOrderToRepoDTO();
		if (orderId == null) {
			dto.setSuccess(false);
			dto.setFailReason("orderId不能为空");
			return dto;
		}

		if (StringUtils.isBlank(forwardUrl)) {
			dto.setSuccess(false);
			dto.setFailReason("跳转地址不能为空");
			return dto;
		}
		List<StorageRepoInfo> list = this.storageService.findStorageRepoInfoByCompanyId(StorageUtil.getCompanyId());
		if (list.isEmpty()) {
			dto.setSuccess(false);
			dto.setFailReason("查询无仓库");
			return dto;
		}
		dto.setForwardUrl(forwardUrl);
		dto.setOrderId(orderId);
		dto.setRepoList(list);
		dto.setSuccess(true);
		return dto;
	}

	/**
	 * 获取公司所有仓库
	 * 
	 * @return
	 */
	public List<StorageRepoInfo> queryStorageRepoInfo() {
		return this.storageService.findStorageRepoInfoByCompanyId(StorageUtil.getCompanyId());
	}

	/**
	 * 拦截订单
	 * 
	 * @param orderId
	 * @param blockReason
	 * @return
	 */
	public BlockOrderDTO blockOrder(Integer orderId, String blockReason) {
		BlockOrderDTO dto = new BlockOrderDTO();
		if (StringUtils.isBlank(blockReason)) {
			dto.setSuccess(false);
			dto.setFailReason("拦截原因不能为空");
			return dto;
		}
		if (orderId == null) {
			dto.setSuccess(false);
			dto.setFailReason("orderId不能为空");
			return dto;
		}
		try {
			// TODO
			OrderVO vo = this.orderService.blockOrder(orderId, StorageUtil.getUserId(), blockReason);
			// BeanUtils.copyProperties(vo, dto);
			dto.setSuccess(true);
			dto.setBlockReason(blockReason);
			dto.setOrderId(orderId);
			return dto;
		} catch (Exception e) {
			dto.setSuccess(false);
			dto.setFailReason(e.getMessage());
			return dto;
		}
	}

	/**
	 * 申请退款
	 * 
	 * @param orderId
	 * @param refundMemo
	 * @return
	 */
	public RefundMemoDTO applyRefund(Integer orderId, String refundMemo) {
		RefundMemoDTO dto = new RefundMemoDTO();
		if (StringUtils.isBlank(refundMemo)) {
			dto.setSuccess(false);
			dto.setFailReason("退款原因不能为空");
			return dto;
		}
		if (orderId == null) {
			dto.setSuccess(false);
			dto.setFailReason("orderId不能为空");
			return dto;
		}
		try {
			// TODO
			// OrderVO vo = this.orderService.applyRefund(orderId,
			// StorageUtil.getUserId(), new Date(), refundMemo);
			// BeanUtils.copyProperties(vo, dto);
			dto.setSuccess(true);
			dto.setBuyerMessage(refundMemo);
			dto.setOrderId(orderId);
			return dto;
		} catch (Exception e) {
			dto.setSuccess(false);
			dto.setFailReason(e.getMessage());
			return dto;
		}
	}

	/**
	 * 备注
	 * 
	 * @param orderId
	 * @param remark
	 * @return
	 */
	public RemarkDTO appendSellerRemark(Integer orderId, String remark) {
		RemarkDTO dto = new RemarkDTO();
		if (StringUtils.isBlank(remark)) {
			dto.setSuccess(false);
			dto.setFailReason("拦截原因不能为空");
			return dto;
		}
		if (orderId == null) {
			dto.setSuccess(false);
			dto.setFailReason("orderId不能为空");
			return dto;
		}
		try {
			// TODO
			// String salerRemark =
			// this.orderService.appendSellerRemark(orderId,
			// StorageUtil.getUserId(), remark);
			dto.setSuccess(true);
			dto.setOrderId(orderId);
			// dto.setSalerRemark(salerRemark);
			dto.setSalerRemark(remark);
			return dto;
		} catch (Exception e) {
			dto.setSuccess(false);
			dto.setFailReason(e.getMessage());
			return dto;
		}
	}

	/**
	 * 改地址Dwrcontroller
	 * 
	 * @param orderId
	 * @return
	 */
	public ChangeOrderAddressDTO changeOrderAddress(Integer orderId, String receiverName, String receiverPhone,
			String receiverAddr) {
		ChangeOrderAddressDTO dto = new ChangeOrderAddressDTO();
		if (StringUtils.isBlank(receiverName)) {
			dto.setSuccess(false);
			dto.setFailReason("收件人不能为空");
			return dto;
		}
		if (StringUtils.isBlank(receiverPhone)) {
			dto.setSuccess(false);
			dto.setFailReason("收件人电话不能为空");
			return dto;
		}
		if (StringUtils.isBlank(receiverAddr)) {
			dto.setSuccess(false);
			dto.setFailReason("收件人地址不能为空");
			return dto;
		}
		if (orderId == null) {
			dto.setSuccess(false);
			dto.setFailReason("orderId不能为空");
			return dto;
		}
		// TODO 测试使用
		ChangeOrderAddressDTO test = getOrder();
		try {
			ChangeAddressDTO newAddress = new ChangeAddressDTO();
			newAddress.setReceiverAddr(receiverAddr);
			newAddress.setReceiverName(receiverName);
			newAddress.setReceiverPhone(receiverPhone);
			OrderVO vo = this.orderService.changeOrderAddress(orderId, StorageUtil.getUserId(), newAddress);
			BeanUtils.copyProperties(test, dto);
			// BeanUtils.copyProperties(vo, dto);
			dto.setReceiverAddr(receiverAddr);
			dto.setReceiverPhone(receiverPhone);
			dto.setReceiverName(receiverName);
			dto.setSuccess(true);
			return dto;
		} catch (Exception e) {
			dto.setSuccess(false);
			dto.setFailReason(e.getMessage());
			return dto;
		}
	}

	/**
	 * 回填单号
	 * 
	 * @param orderId
	 * @param expressNumber
	 * @param expressCompany
	 * @return
	 */
	public FillExpressNumberDTO fillExpressNumber(Integer orderId, String expressNumber, String expressCompany) {
		FillExpressNumberDTO dto = new FillExpressNumberDTO();
		if (StringUtils.isBlank(expressNumber)) {
			dto.setSuccess(false);
			dto.setFailReason("快递单号不能为空");
			return dto;
		}
		if (StringUtils.isBlank(expressCompany)) {
			dto.setSuccess(false);
			dto.setFailReason("快递公司不能为空");
			return dto;
		}
		if (orderId == null) {
			dto.setSuccess(false);
			dto.setFailReason("orderId不能为空");
			return dto;
		}
		// TODO 测试使用
		try {
			this.orderService.fillExpressNumber(orderId, expressNumber, expressCompany, StorageUtil.getUserId());
			// BeanUtils.copyProperties(vo, dto);
			dto.setExpressNumber(expressNumber);
			dto.setExpressCompany(expressCompany);
			dto.setOrderId(orderId);
			dto.setSuccess(true);
			return dto;
		} catch (Exception e) {
			dto.setSuccess(false);
			dto.setFailReason(e.getMessage());
			return dto;
		}
	}

	private ChangeOrderAddressDTO getOrder() {
		ChangeOrderAddressDTO o = new ChangeOrderAddressDTO();
		o.setOrderId(49213);
		o.setReceiverAddr("北京市海淀区定慧东里14号楼");
		o.setReceiverName("帅哥");
		o.setReceiverPhone("17777777777");
		return o;
	}

	// ----------------------创建订单---------------------------------

	/**
	 * 获取手动下单订单号
	 * 
	 * @param session
	 * @return
	 */
	public CreateOrderViewDTO getManualOutOrderNumber(String manualOrderType, HttpSession session) {
		CreateOrderViewDTO dto = sessionHasDTO(session);
		try {
			String orderNumber = dto.getOutOrderNumber();
			if (StringUtils.isBlank(orderNumber)) {
				orderNumber = generateManualOrderNumber();
			}
			dto.setSuccess(true);
			dto.setOutOrderNumber(orderNumber);
			dto.setManualOrderType(manualOrderType);
			session.setAttribute(CREATE_OREDER_VIEW_MAP, dto);
			return dto;
		} catch (Exception e) {
			dto.setSuccess(false);
			dto.setFailReason("创建订单号失败" + e.getMessage());
			return dto;
		}
	}

	/**
	 * 获取手动淘宝下单类型订单
	 * 
	 * @param manualOrderType
	 * @param outOrderNumber
	 * @param session
	 * @return
	 */
	public CreateOrderViewDTO getManualTaobaoOrderOutOrderNumber(String manualOrderType,Integer shopId, String outOrderNumber,
			HttpSession session) {
		CreateOrderViewDTO dto = sessionHasDTO(session);
		if (StringUtils.isBlank(outOrderNumber)) {
			dto.setSuccess(false);
			dto.setFailReason("订单号不能为空");
			return dto;
		}
		ShopInfo shop = this.shopsService.findShopByShopIdAndCompanyId(StorageUtil.getCompanyId(), shopId);
		if (shop == null) {
			dto.setSuccess(false);
			dto.setFailReason("店铺选择有误");
			return dto;
		}
		// TODO 此处要调用service 判断订单号是否重复
		try {
			dto.setSuccess(true);
			dto.setOutOrderNumber(outOrderNumber);
			dto.setManualOrderType(manualOrderType);
			session.setAttribute(CREATE_OREDER_VIEW_MAP, dto);
			return dto;
		} catch (Exception e) {
			dto.setSuccess(false);
			dto.setFailReason("创建订单号失败" + e.getMessage());
			return dto;
		}
	}

	/**
	 * 写入订单类型
	 * 
	 * @param orderType
	 * @param session
	 * @return
	 */
	public CreateOrderViewDTO setOrderType(String orderType, HttpSession session) {
		CreateOrderViewDTO dto = sessionHasDTO(session);
		if (!OrderType.isDefined(orderType)) {
			dto.setSuccess(false);
			dto.setFailReason("错误的订单类型");
		}
		try {
			dto.setSuccess(true);
			dto.setOrderType(orderType);
			session.setAttribute(CREATE_OREDER_VIEW_MAP, dto);
			return dto;
		} catch (Exception e) {
			dto.setSuccess(false);
			dto.setFailReason("写入订单类型失败" + e.getMessage());
			return dto;
		}
	}

	/**
	 * 将shop信息写入session
	 * 
	 * @param shopId
	 * @param session
	 * @return
	 */
	public CreateOrderViewDTO setShopIdToSession(Integer shopId, HttpSession session) {
		CreateOrderViewDTO dto = sessionHasDTO(session);
		ShopInfo shop = this.shopsService.findShopByShopIdAndCompanyId(StorageUtil.getCompanyId(), shopId);
		if (shop == null) {
			dto.setSuccess(false);
			dto.setFailReason("店铺选择有误");
			return dto;
		}
		try {
			dto.setSuccess(true);
			dto.setShopId(shop.getShopId());
			dto.setDiscount(shop.getDiscount());
			session.setAttribute(CREATE_OREDER_VIEW_MAP, dto);
			return dto;
		} catch (Exception e) {
			dto.setSuccess(false);
			dto.setFailReason("修改店铺失败" + e.getMessage());
			return dto;
		}
	}

	/**
	 * 查询图书及可用量
	 * 
	 * @param bookNameOrIsbn
	 * @param discount
	 * @param session
	 * @return
	 */
	public CreateOrderQuerySkuInfoViewDTO findSkuInfoByBookNameOrIsbn(String bookNameOrIsbn, Double discount,
			HttpSession session) {
		CreateOrderQuerySkuInfoViewDTO dto = new CreateOrderQuerySkuInfoViewDTO();
		try {
			if (StringUtils.isBlank(bookNameOrIsbn)) {
				throw new RuntimeException("请输入查询条件");
			}
			if (discount == null) {
				throw new RuntimeException("折扣率为空");
			}
			String condition = bookNameOrIsbn.trim();
			if (StringUtils.isNumeric(condition) && condition.length() != 13) {
				throw new RuntimeException("isbn不为13位");
			}
			List<Bookinfo> bookList;
			if (StringUtils.isNumeric(condition) && condition.length() == 13) {
				bookList = this.bookService.findBookByISBN(condition);
			} else {
				// 模糊查询只展示前20条
				QueryUtil<Bookinfo> query = new QueryUtil<Bookinfo>();
				query.like("bookName", "%" + condition + "%");
				Pageable page = new PageRequest(0, 20);
				Page<Bookinfo> pageList = this.bookService.findBySpecification(query.getSpecification(), page);
				bookList = pageList.getContent();
			}
			// 组装信息
			List<SkuInfo> list = new ArrayList<CreateOrderQuerySkuInfoViewDTO.SkuInfo>();
			for (Bookinfo book : bookList) {
				StorageProduct sp = this.storageService.findBySkuIdAndRepoId(book.getId(), StorageUtil.getRepoId());
				// 可用量不足跳过
				if (sp.getStockAvailable() <= 0) {
					continue;
				}
				SkuInfo sku = new SkuInfo();
				BeanUtils.copyProperties(book, sku);
				sku.setBookPrice((book.getBookPrice() * discount));
				sku.setBookAmount(sp.getStockAvailable());
				list.add(sku);
			}
			dto.setSuccess(true);
			dto.setSkuList(list);
			return dto;
		} catch (Exception e) {
			dto.setSuccess(false);
			dto.setFailReason("查询图书失败:" + e.getMessage());
			return dto;
		}
	}

	/**
	 * 新增图书
	 * 
	 * @param skuId
	 * @param isbn
	 * @param itemName
	 * @param itemCount
	 * @param itemPrice
	 * @param session
	 * @return
	 */
	public CreateOrderViewDTO addSkuInfoToSession(Integer skuId, String isbn, String itemName, Integer itemCount,
			Double itemPrice, HttpSession session) {
		CreateOrderViewDTO dto = sessionHasDTO(session);
		try {
			if (skuId == null) {
				throw new RuntimeException("skuId不能为空");
			}
			if (StringUtils.isBlank(isbn)) {
				throw new RuntimeException("isbn不能为空");
			}
			if (StringUtils.isBlank(itemName)) {
				throw new RuntimeException("图书名称不能为空");
			}
			if (itemCount == null) {
				throw new RuntimeException("图书数量不能为空");
			}
			if (itemPrice == null) {
				throw new RuntimeException("图书价格不能为空");
			}
			if (dto.getSkuNumber() == null) {
				dto.setSkuNumber(0);
			} else {
				Integer i = dto.getSkuNumber();
				dto.setSkuNumber(++i);
			}
			List<SkuViewInfo> list = dto.getSkus();
			SkuViewInfo sb = new SkuViewInfo();
			sb.setSkuId(skuId);
			sb.setIsbn(isbn);
			sb.setItemCount(itemCount);
			sb.setItemName(itemName);
			sb.setResultInt(dto.getSkuNumber());
			sb.setItemPrice(itemPrice);
			if (list == null || list.isEmpty()) {
				list = new ArrayList<SkuViewInfo>();
				dto.setSkus(list);
			}else{
				for (SkuViewInfo s : list) {
					if(sb.getSkuId().equals(s.getSkuId())){
						throw new RuntimeException("图书在订单中存在,请修改其数量");
					}
				}
			}
			list.add(sb);
			SkuInfoViewDTO sbd = new SkuInfoViewDTO();
			BeanUtils.copyProperties(sb, sbd);
			dto.setSkuOld(sbd);
			Double orderMoney = getOrderMoney(dto);
			dto.setSuccess(true);
			dto.setOrderMoney(orderMoney);
			session.setAttribute(CREATE_OREDER_VIEW_MAP, dto);
			return dto;
		} catch (Exception e) {
			dto.setSuccess(false);
			dto.setFailReason("新增图书失败" + e.getMessage());
			return dto;
		}
	}

	/**
	 * 修改图书数量
	 * 
	 * @param skuId
	 * @param updateItemCount
	 * @param session
	 * @return
	 */
	public CreateOrderViewDTO updateSkuItemCount(Integer skuId, Integer updateItemCount, HttpSession session) {
		CreateOrderViewDTO dto = sessionHasDTO(session);
		try {
			if (skuId == null) {
				throw new RuntimeException("skuId不能为空");
			}
			if (updateItemCount == null) {
				throw new RuntimeException("图书数量不能为空");
			}
			StorageProduct sp = this.storageService.findBySkuIdAndRepoId(skuId, StorageUtil.getRepoId());
			if (updateItemCount > sp.getStockAvailable()) {
				throw new RuntimeException("图书可用量不足");
			}
			List<SkuViewInfo> list = dto.getSkus();
			if (list == null || list.isEmpty()) {
				throw new RuntimeException("session中没有图书信息");
			}
			for (SkuViewInfo s : list) {
				if (s.getSkuId().equals(skuId)) {
					s.setItemCount(updateItemCount);
					SkuInfoViewDTO sbd = new SkuInfoViewDTO();
					BeanUtils.copyProperties(s, sbd);
					dto.setSkuOld(sbd);
				}
			}
			Double orderMoney = getOrderMoney(dto);
			dto.setSuccess(true);
			dto.setOrderMoney(orderMoney);
			session.setAttribute(CREATE_OREDER_VIEW_MAP, dto);
			return dto;
		} catch (Exception e) {
			dto.setSuccess(false);
			dto.setFailReason("修改图书数量失败" + e.getMessage());
			return dto;
		}
	}

	/**
	 * 修改图书价格
	 * 
	 * @param skuId
	 * @param updateItemPrice
	 * @param session
	 * @return
	 */
	public CreateOrderViewDTO updateSkuItemPrice(Integer skuId, Double updateItemPrice, HttpSession session) {
		CreateOrderViewDTO dto = sessionHasDTO(session);
		try {
			if (skuId == null) {
				throw new RuntimeException("skuId不能为空");
			}
			if (updateItemPrice == null) {
				throw new RuntimeException("图书价格不能为空");
			}
			List<SkuViewInfo> list = dto.getSkus();
			if (list == null || list.isEmpty()) {
				throw new RuntimeException("session中没有图书信息");
			}
			for (SkuViewInfo s : list) {
				if (s.getSkuId().equals(skuId)) {
					s.setItemPrice(updateItemPrice);
					SkuInfoViewDTO sbd = new SkuInfoViewDTO();
					BeanUtils.copyProperties(s, sbd);
					dto.setSkuOld(sbd);
				}
			}
			Double orderMoney = getOrderMoney(dto);
			dto.setSuccess(true);
			dto.setOrderMoney(orderMoney);
			session.setAttribute(CREATE_OREDER_VIEW_MAP, dto);
			return dto;
		} catch (Exception e) {
			dto.setSuccess(false);
			dto.setFailReason("修改图书价格失败" + e.getMessage());
			return dto;
		}
	}

	/**
	 * 删除图书
	 * 
	 * @param skuId
	 * @param session
	 * @return
	 */
	public CreateOrderViewDTO deleteSubOrder(Integer skuId, HttpSession session) {
		CreateOrderViewDTO dto = sessionHasDTO(session);
		try {
			if (skuId == null) {
				throw new RuntimeException("skuId不能为空");
			}
			List<SkuViewInfo> list = dto.getSkus();
			if (list == null || list.isEmpty()) {
				throw new RuntimeException("session中没有图书信息");
			}
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getSkuId().equals(skuId)) {
					SkuInfoViewDTO sbd = new SkuInfoViewDTO();
					BeanUtils.copyProperties(list.get(i), sbd);
					dto.setSkuOld(sbd);
					list.remove(i);
				}
			}
			try {
				Double orderMoney = getOrderMoney(dto);
				dto.setOrderMoney(orderMoney);
			} catch (Exception e) {
				dto.setOrderMoney(0d);
			}
			dto.setSuccess(true);
			session.setAttribute(CREATE_OREDER_VIEW_MAP, dto);
			return dto;
		} catch (Exception e) {
			dto.setSuccess(false);
			dto.setFailReason("删除图书失败" + e.getMessage());
			return dto;
		}
	}

	/**
	 * 修改订单收件人相关信息
	 * 
	 * @param skuId
	 * @param session
	 * @return
	 */
	public CreateOrderViewDTO updateReceiverInfo(String receiverName, String receiverPhone, String receiverAddr,
			HttpSession session) {
		CreateOrderViewDTO dto = sessionHasDTO(session);
		try {
			if (StringUtils.isBlank(receiverName)) {
				throw new RuntimeException("收件人不能为空");
			}
			if (StringUtils.isBlank(receiverPhone)) {
				throw new RuntimeException("收件人手机不能为空");
			}
			if (StringUtils.isBlank(receiverAddr)) {
				throw new RuntimeException("收件人地址不能为空");
			}
			dto.setReceiverName(receiverName);
			dto.setReceiverPhone(receiverPhone);
			dto.setReceiverAddr(receiverAddr);
			dto.setSuccess(true);
			session.setAttribute(CREATE_OREDER_VIEW_MAP, dto);
			return dto;
		} catch (Exception e) {
			dto.setSuccess(false);
			dto.setFailReason("修改订单收件人失败" + e.getMessage());
			return dto;
		}
	}

	/**
	 * 修改邮费
	 * 
	 * @param postage
	 * @param session
	 * @return
	 */
	public CreateOrderViewDTO updateOrderPostage(Double postage, HttpSession session) {
		CreateOrderViewDTO dto = sessionHasDTO(session);
		try {
			if (postage == null) {
				throw new RuntimeException("postage不能为空");
			}
			dto.setPostage(postage);
			Double orderMoney = getOrderMoney(dto);
			dto.setSuccess(true);
			dto.setOrderMoney(orderMoney);
			session.setAttribute(CREATE_OREDER_VIEW_MAP, dto);
			return dto;
		} catch (Exception e) {
			dto.setSuccess(false);
			dto.setFailReason("修改邮费失败" + e.getMessage());
			return dto;
		}
	}

	/**
	 * 修改总金额
	 * @param orderMoney
	 * @param session
	 * @return
	 */
	public CreateOrderViewDTO updateOrderMoney(Double orderMoney, HttpSession session) {
		CreateOrderViewDTO dto = sessionHasDTO(session);
		try {
			if (orderMoney == null) {
				throw new RuntimeException("orderMoney不能为空");
			}
			Double money = getOrderMoney(dto);
			if (orderMoney < money) {
				throw new RuntimeException("写入订单价格不能小于计算后总价格");
			}
			dto.setSuccess(true);
			dto.setOrderMoney(orderMoney);
			session.setAttribute(CREATE_OREDER_VIEW_MAP, dto);
			return dto;
		} catch (Exception e) {
			dto.setSuccess(false);
			dto.setFailReason("修改金额失败" + e.getMessage());
			return dto;
		}
	}
	
	/**
	 * 出库
	 * @param expressNumber
	 * @return
	 */
	public SendOutViewDTO sendOut(String expressNumber){
		SendOutViewDTO dto = new SendOutViewDTO();
		try {
			if(StringUtils.isBlank(expressNumber)){
				throw new RuntimeException("快递单号不能为空");
			}
			String expressNumberTrim = expressNumber.trim();
			OrderVO vo = this.orderService.sendOut(StorageUtil.getRepoId(), expressNumberTrim, StorageUtil.getUserId());
			dto.setOrderVO(vo);
			dto.setSuccess(true);
			return dto;
		} catch (Exception e) {
			dto.setSuccess(false);
			dto.setFailReason("出库失败" + e.getMessage());
			return dto;
		}
	}

	/**
	 * 计算订单总价
	 * 
	 * @param dto
	 * @return
	 */
	private Double getOrderMoney(CreateOrderViewDTO dto) {
		Double orderMoney = 0d;
		if (dto.getSkus() == null || dto.getSkus().isEmpty()) {
			throw new RuntimeException("订单没有商品");
		}
		for (SkuViewInfo so : dto.getSkus()) {
			if (so == null || subOrderAllNull(so)) {
				continue;
			}
			orderMoney += (so.getItemPrice() * so.getItemCount());
		}
		if (dto.getPostage() != null) {
			orderMoney += dto.getPostage();
		}
		return orderMoney;
	}
	
	/**
	 * 判断是否有空值
	 * 
	 * @param so
	 * @return
	 */
	private boolean subOrderAllNull(SkuViewInfo so) {
		if (StringUtils.isBlank(so.getIsbn()) && StringUtils.isBlank(so.getItemName()) && so.getSkuId() == null
				&& so.getItemCount() == null && so.getItemPrice() == null) {
			return true;
		}
		if (so.getSkuId() == null || so.getItemCount() == null || so.getItemPrice() == null) {
			return true;
		}
		return false;
	}

	/**
	 * 从session 获取dto 如果没有则新建
	 * 
	 * @param session
	 * @return
	 */
	private CreateOrderViewDTO sessionHasDTO(HttpSession session) {
		CreateOrderViewDTO dto =  (CreateOrderViewDTO) session.getAttribute(CREATE_OREDER_VIEW_MAP);
		if (dto == null) {
			dto = new CreateOrderViewDTO();
		}
		return dto;
	}

	private String generateManualOrderNumber() {
		Integer seq = SequenceCreator.getSequence(SequenceCreator.SEQ_ORDER_MANUAL_NUM);
		return "manual" + ZisUtils.getDateString("yyyyMMddHHmmss") + seq;
	}

}
