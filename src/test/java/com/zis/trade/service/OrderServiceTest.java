package com.zis.trade.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.zis.base.BaseTestUnit;
import com.zis.storage.entity.StorageIoDetail;
import com.zis.storage.service.StorageService;
import com.zis.trade.dto.ChangeAddressDTO;
import com.zis.trade.dto.CreateTradeOrderDTO;
import com.zis.trade.dto.OrderAddressImportDTO;
import com.zis.trade.dto.CreateTradeOrderDTO.SubOrder;
import com.zis.trade.dto.OrderQueryCondition;
import com.zis.trade.dto.OrderVO;
import com.zis.trade.dto.OrderVO.OrderDetailVO;
import com.zis.trade.entity.Order;
import com.zis.trade.entity.Order.OrderType;
import com.zis.trade.entity.Order.StorageStatus;
import com.zis.trade.entity.OrderDetail;


public class OrderServiceTest extends BaseTestUnit {

	@Autowired
	OrderService service;
	@Autowired
	StorageService storageService;
	
	@Test
	public void testCreateOrder() {
		CreateTradeOrderDTO orderDTO = createOrder("蕾蕾-未支付");
		service.createOrder(orderDTO);
	}
	
	@Test
	public void testPayOrder() {
		CreateTradeOrderDTO orderDTO = createOrder("张三-支付");
		Order order = service.createOrder(orderDTO);
		
		service.payOrder(order.getId(), 39.2, 1001);
	}

	@Test
	public void testCancelOrder() {
		CreateTradeOrderDTO orderDTO = createOrder("张三-取消");
		Order order = service.createOrder(orderDTO);
		
		service.cancelOrder(order.getId(), 1001);
	}
	
	@Test
	public void testApplyRefund() {
		CreateTradeOrderDTO orderDTO = createOrder("张三-申请退款");
		Order order = service.createOrder(orderDTO);
		service.payOrder(order.getId(), 39.2, 1001);
		
		service.applyRefund(order.getId(), 8311, new Date(), "买家不想要了");
	}
	
	@Test
	public void testAgreeRefund() {
		CreateTradeOrderDTO orderDTO = createOrder("张三-申请退款");
		Order order = service.createOrder(orderDTO);
		service.payOrder(order.getId(), 39.2, 1001);
		service.applyRefund(order.getId(), 8311, new Date(), "买家不想要了");
		
		service.agreeRefund(order.getId(), 1124, "已停止发货");
	}
	
	@Test
	public void testCancelRefund() {
		CreateTradeOrderDTO orderDTO = createOrder("张三-申请退款");
		Order order = service.createOrder(orderDTO);
		service.payOrder(order.getId(), 39.2, 1001);
		service.applyRefund(order.getId(), 8311, new Date(), "买家不想要了");

		service.cancelRefund(order.getId(), 1124, "继续发货");
	}
	
	@Test
	public void testChangeOrderAddress() {
		CreateTradeOrderDTO orderDTO = createOrder("张三-申请退款");
		Order order = service.createOrder(orderDTO);
		service.payOrder(order.getId(), 39.2, 1001);
		
		ChangeAddressDTO addr = new ChangeAddressDTO();
		addr.setReceiverAddr("山东省 枣庄市 小镇");
		addr.setReceiverName("大锤");
		addr.setReceiverPhone("13100222200");
		service.changeOrderAddress(order.getId(), 1234, addr);
	}
	
	@Test
	public void testBlockOrder() {
		CreateTradeOrderDTO orderDTO = createOrder("张三-拦截");
		Order order = service.createOrder(orderDTO);
		service.payOrder(order.getId(), 39.2, 1001);
		
		service.blockOrder(order.getId(), 311, "涉嫌欺诈");
	}

	@Test
	public void testBlockOrderForSendOut() {
		CreateTradeOrderDTO orderDTO = createOrder("张三-拦截");
		Order order = service.createOrder(orderDTO);
		final Integer oid = order.getId();
		final Integer repoId = 1;
		service.payOrder(oid, 39.2, 1001);
		service.arrangeOrderToRepo(oid, 111, repoId);
		List<Integer> orderIds = new ArrayList<Integer>();
		orderIds.add(oid);
		int batchId = service.arrangeOrderToPos(repoId, orderIds, 311);
		StorageIoDetail ioDetail = storageService.pickupLock(batchId, 1);
		while(ioDetail != null) {
			ioDetail = storageService.pickupDoneAndLockNext(ioDetail.getDetailId(), 1);
		}
 		service.finishSend(repoId, batchId, 5);
		service.printExpress(oid, 211);
		service.blockOrder(oid, 311, "涉嫌欺诈");
		service.fillExpressNumber(oid, "ex"+oid, "申通快递", 111);
		service.sendOut(repoId, "ex"+oid, 222);
	}
	
	@Test
	public void testAppendSellerRemark() {
		CreateTradeOrderDTO orderDTO = createOrder("张三-拦截");
		Order order = service.createOrder(orderDTO);
		service.appendSellerRemark(order.getId(), 1, "仔细打包");
	}
	
	@Test
	public void testFindOrdersByStatus() {
		PageRequest page = new PageRequest(0, 10);
		Page<OrderVO> vos = service.findOrdersByStatus(1, null, null, StorageStatus.WAIT_ARRANGE, page);
		for (OrderVO vo : vos.getContent()) {
			printOrder(vo);
		}
	}
	
	private void printOrder(OrderVO vo) {
		StringBuilder builder = new StringBuilder();
		for (OrderDetailVO odvo : vo.getOrderDetailVOs()) {
			builder.append(odvo.getBookName()).append("*").append(odvo.getItemCount()).append(", ");
		}
		String s = String.format("orderId=%s, outNo=%s, payStatus=%s, exStatus=%s, stStatus=%s, detail=%s",
				vo.getId(), vo.getOutOrderNumbers(), vo.getPayStatusDisplay(), vo.getExpressStatusDisplay(), vo.getStorageStatusDisplay(), builder.toString());
		System.out.println(s);
	}

	@Test
	public void testFindOrdersByCondition() {
		PageRequest page = new PageRequest(0, 10);
		OrderQueryCondition cond = new OrderQueryCondition();
//		cond.setOutOrderNumber("TB3100943-233");
		cond.setOrderId(1411);
//		cond.setReceiverName("张三-拦截");
//		cond.setExpressNumber("ex15");
		Page<OrderVO> orders = service.findOrdersByCondition(1, cond, page);
		for (OrderVO vo : orders.getContent()) {
			printOrder(vo);
		}
	}
	
	@Test
	public void testFindByOrderIdAndCompanyId() {
		CreateTradeOrderDTO orderDTO = createOrder("张三-拦截");
		Order order = service.createOrder(orderDTO);
		
		Order ordRs = service.findByOrderIdAndCompanyId(order.getId(), order.getCompanyId());
		for (OrderDetail od : ordRs.getOrderDetails()) {
			System.out.println(od.getOrderDetailId());
		}
	}
	
	@Test
	public void testFillExpressNumber() {
		service.fillExpressNumber(14, "ex11011", "中通快递", 3);
	}
	
	@Test
	public void testImportReceiverAddr() {
		List<OrderAddressImportDTO> addrs = new ArrayList<OrderAddressImportDTO>();
		addrs.add(new OrderAddressImportDTO("TB3100943-54", "张三", "110", "陕西省西安市"));
		addrs.add(new OrderAddressImportDTO("TB3100943-104", "张二", "110", "陕西省西安市"));
		service.importReceiverAddr(addrs);
	}
	
	private CreateTradeOrderDTO createOrder(String name) {
		CreateTradeOrderDTO orderDTO = new CreateTradeOrderDTO();
		orderDTO.setOperator(1101);
		orderDTO.setOrderMoney(99.5);
		orderDTO.setOrderType(OrderType.SELF.getValue());
		orderDTO.setOutOrderNumber("TB3100943-" + new Random().nextInt(1000));
		orderDTO.setReceiverAddr("北京朝阳区来广营");
		orderDTO.setReceiverName(name);
		orderDTO.setReceiverPhone("13812345670");
		orderDTO.setBuyerMessage("尽快发货哦");
		orderDTO.setShopId(1);
		List<SubOrder> subOrders = new ArrayList<SubOrder>();
		subOrders.add(createSubOrder(71419, 16));
		subOrders.add(createSubOrder(42115, 20));
		orderDTO.setSubOrders(subOrders);
		return orderDTO;
	}

	private SubOrder createSubOrder(Integer itemId, Integer skuId) {
		SubOrder subOrder = new SubOrder();
		subOrder.setItemCount(2);
		subOrder.setItemId(itemId);
		subOrder.setItemName("商品" + itemId);
		subOrder.setItemOutNum("out-1101-" + itemId);
		subOrder.setItemPrice(29.9);
		subOrder.setSkuId(skuId);
		return subOrder;
	}
}
