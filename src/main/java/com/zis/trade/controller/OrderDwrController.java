package com.zis.trade.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.zis.bookinfo.service.BookService;
import com.zis.storage.entity.StorageRepoInfo;
import com.zis.storage.service.StorageService;
import com.zis.storage.util.StorageUtil;
import com.zis.trade.dto.ArrangeOrderToRepoDTO;
import com.zis.trade.dto.BlockOrderDTO;
import com.zis.trade.dto.ChangeAddressDTO;
import com.zis.trade.dto.ChangeOrderAddressDTO;
import com.zis.trade.dto.FillExpressNumberDTO;
import com.zis.trade.dto.OrderVO;
import com.zis.trade.dto.RefundMemoDTO;
import com.zis.trade.dto.RemarkDTO;
import com.zis.trade.service.OrderService;

@Controller
public class OrderDwrController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private StorageService storageService;
	
	@Autowired
	private BookService bookService;

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
			dto.setFailMessage("拦截原因不能为空");
			return dto;
		}
		if (orderId == null) {
			dto.setSuccess(false);
			dto.setFailMessage("orderId不能为空");
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
			dto.setFailMessage(e.getMessage());
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
			dto.setFailMessage("收件人不能为空");
			return dto;
		}
		if (StringUtils.isBlank(receiverPhone)) {
			dto.setSuccess(false);
			dto.setFailMessage("收件人电话不能为空");
			return dto;
		}
		if (StringUtils.isBlank(receiverAddr)) {
			dto.setSuccess(false);
			dto.setFailMessage("收件人地址不能为空");
			return dto;
		}
		if (orderId == null) {
			dto.setSuccess(false);
			dto.setFailMessage("orderId不能为空");
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
			dto.setFailMessage(e.getMessage());
			return dto;
		}
	}

	/**
	 * 回填单号
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
	
	public static void main(String[] args) {
		String s="'";
	} 

	private ChangeOrderAddressDTO getOrder() {
		ChangeOrderAddressDTO o = new ChangeOrderAddressDTO();
		o.setOrderId(49213);
		o.setReceiverAddr("北京市海淀区定慧东里14号楼");
		o.setReceiverName("帅哥");
		o.setReceiverPhone("17777777777");
		return o;
	}
}
