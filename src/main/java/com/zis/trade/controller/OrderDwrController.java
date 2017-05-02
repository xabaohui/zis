package com.zis.trade.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.zis.storage.util.StorageUtil;
import com.zis.trade.dto.BlockOrderDTO;
import com.zis.trade.dto.ChangeOrderAddressDTO;
import com.zis.trade.service.OrderService;

@Controller
public class OrderDwrController {

	@Autowired
	private OrderService orderService;

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
		// Order order = this.orderService.findByOrderIdAndCompanyId(orderId,
		// StorageUtil.getCompanyId());
		return getOrder();
	}

	/**
	 * 拦截订单
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
			this.orderService.blockOrder(orderId, StorageUtil.getUserId(), blockReason);
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
		// if (orderId == null) {
		// dto.setSuccess(false);
		// dto.setFailMessage("orderId不能为空");
		// return dto;
		// }
		// Order order = this.orderService.findByOrderIdAndCompanyId(orderId,
		// StorageUtil.getCompanyId());
		// if (order == null) {
		// dto.setSuccess(false);
		// dto.setFailMessage("订单信息有误，请联系管理员");
		// return dto;
		// }
		// TODO 测试使用
		ChangeOrderAddressDTO test = getOrder();
		// BeanUtils.copyProperties(order, dto);
		try {
			this.orderService.changeOrderAddress(orderId, StorageUtil.getUserId(), receiverAddr);
			BeanUtils.copyProperties(test, dto);
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

	private ChangeOrderAddressDTO getOrder() {
		ChangeOrderAddressDTO o = new ChangeOrderAddressDTO();
		o.setOrderId(49213);
		o.setReceiverAddr("北京市海淀区定慧东里14号楼");
		o.setReceiverName("帅哥");
		o.setReceiverPhone("17777777777");
		return o;
	}
}
