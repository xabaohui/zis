package com.zis.trade.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.zis.trade.dto.ChangeAddressDTO;
import com.zis.trade.dto.CreateTradeOrderDTO;
import com.zis.trade.dto.ExpressNumberDTO;
import com.zis.trade.dto.OrderQueryCondition;
import com.zis.trade.dto.OrderVO;
import com.zis.trade.entity.Order;
import com.zis.trade.entity.Order.ExpressStatus;
import com.zis.trade.entity.Order.PayStatus;
import com.zis.trade.entity.Order.StorageStatus;

/**
 * 订单接口
 * 
 * @author yz
 * 
 */
public interface OrderService {

	/**
	 * 创建订单
	 * <p/>
	 * 自动合并同店铺、同地址的订单
	 * 
	 * @param orderDTO
	 * @return
	 */
	Order createOrder(CreateTradeOrderDTO orderDTO);

	/**
	 * 取消订单，资金状态：未支付->订单关闭(未支付)
	 * 
	 * @param orderId
	 *            主订单号
	 * @param operator
	 *            操作员Id
	 * @throws 取消失败抛出异常
	 */
	void cancelOrder(Integer orderId, Integer operator);

	/**
	 * 取消订单，资金状态：未支付->订单关闭(未支付)
	 * 
	 * @param shopId
	 *            店铺Id
	 * @param outOrderNumber
	 *            外部订单号
	 * @param operator
	 *            操作员Id
	 */
	void cancelOrder(Integer shopId, String outOrderNumber, Integer operator);

	/**
	 * 支付订单，资金状态：未支付->已支付
	 * 
	 * @param orderId
	 *            主订单号
	 * @param paymentAmount
	 *            支付金额
	 * @param operator
	 *            操作员Id
	 * @throws 支付失败抛出异常
	 */
	void payOrder(Integer orderId, Double paymentAmount, Integer operator);

	/**
	 * 支付订单，资金状态：未支付->已支付
	 * 
	 * @param shopId
	 *            店铺Id
	 * @param outOrderNumber
	 *            外部订单号
	 * @param paymentAmount
	 *            支付金额
	 * @param operator
	 *            操作员Id
	 * @throws 支付失败抛出异常
	 */
	void payOrder(Integer shopId, String outOrderNumber, Double paymentAmount, Integer operator);

	/**
	 * 申请退款，资金状态：已支付->退款中
	 * 
	 * @param orderId
	 *            主订单Id
	 * @param operator
	 *            操作员Id
	 * @param applyTime
	 *            退款申请时间
	 * @param refundMemo
	 *            退款说明
	 */
	OrderVO applyRefund(Integer orderId, Integer operator, Date applyTime, String refundMemo);

	/**
	 * 申请退款，资金状态：已支付->退款中
	 * 
	 * @param shopId
	 *            店铺Id
	 * @param outOrderNumber
	 *            外部订单号
	 * @param operator
	 *            操作员Id
	 * @param applyTime
	 *            退款申请时间
	 * @param refundMemo
	 *            退款说明
	 */
	OrderVO applyRefund(Integer shopId, String outOrderNumber, Integer operator, Date applyTime, String refundMemo);

	/**
	 * 同意退款，资金状态：退款中->订单关闭(已退款)
	 * 
	 * @param orderId
	 *            订单Id
	 * @param operator
	 *            操作员Id
	 * @param memo
	 *            操作说明
	 */
	void agreeRefund(Integer orderId, Integer operator, String memo);

	/**
	 * 取消退款，资金状态：退款中->已支付
	 * 
	 * @param orderId
	 *            订单Id
	 * @param operator
	 *            操作员Id
	 * @param memo
	 *            操作说明
	 */
	void cancelRefund(Integer orderId, Integer operator, String memo);

	/**
	 * 修改收货地址
	 * 
	 * @param orderId
	 *            主订单Id
	 * @param operator
	 *            操作员Id
	 * @param newAddress
	 *            新地址
	 */
	OrderVO changeOrderAddress(Integer orderId, Integer operator, ChangeAddressDTO newAddress);

	/**
	 * 修改商品 TODO 未实现
	 */
	void changeItems();
	
	/**
	 * 添加卖家备注（追加模式）
	 * @param orderId
	 * @param operator
	 * @param remark 备注内容
	 * @return 订单备注（处理完本次请求后的内容）
	 */
	String appendSellerRemark(Integer orderId, Integer operator, String remark);

	/**
	 * 拦截订单
	 * 
	 * @param orderId
	 *            主订单Id
	 * @param operator
	 *            操作员Id
	 * @param blockReason
	 *            拦截原因
	 */
	OrderVO blockOrder(Integer orderId, Integer operator, String blockReason);

	/**
	 * 分配订单到仓库，配货状态：未分配仓库->已分配仓库
	 * 
	 * @param orderId
	 *            主订单Id
	 * @param operator
	 *            操作员Id
	 * @param repoId
	 *            仓库Id
	 */
	void arrangeOrderToRepo(Integer orderId, Integer operator, Integer repoId);

	/**
	 * 开始配货，配货状态：已分配仓库->配货中
	 * 
	 * @param repoId 仓库Id
	 * @param orderIds
	 *            主订单Id列表
	 * @param operator
	 *            操作员Id
	 * @return 返回配货批次号
	 */
	int arrangeOrderToPos(Integer repoId, List<Integer> orderIds, Integer operator);

	/**
	 * 取消配货，配货状态：已分配仓库->未分配仓库
	 * 
	 * @param orderId
	 * @param operator
	 * @param memo 原因说明
	 */
	void cancelArrangeOrder(Integer orderId, Integer operator, String memo);

	/**
	 * 完成配货，配货状态：配货中->配货完成
	 * 
	 * @param batchId
	 *            配货批次Id
	 * @param operator
	 */
	void finishSend(Integer repoId, Integer batchId, Integer operator);

	/**
	 * 订单缺货，配货状态：配货中->未分配仓库
	 * <p/>
	 * TODO 需重新考虑缺货且无后续可用库位补充的情况，建议仓库端手动返回
	 * 
	 * @param orderId
	 * @param operator
	 */
	void lackness(Integer orderId, Integer operator);

	/**
	 * 打印快递单，物流状态：未打印->已打印
	 * @param orderId
	 * @param operator
	 * @return
	 */
	OrderVO printExpress(Integer orderId, Integer operator);

	/**
	 * 打印快递单，物流状态：未打印->已打印
	 * 
	 * @param orderIds
	 *            主订单Id
	 * @param operator
	 *            操作员Id
	 */
	List<OrderVO> printExpressList(List<Integer> orderIds, Integer operator);

	/**
	 * 回填快递单号，物流状态：已打印->已填单
	 * 
	 * @param orderId
	 *            订单号
	 * @param expressNumber
	 *            快递单号
	 * @param expressCompany
	 *            快递公司
	 * @param operator
	 *            操作员Id
	 */
	void fillExpressNumber(Integer orderId, String expressNumber, String expressCompany, Integer operator);

	/**
	 * 回填快递单号，物流状态：已打印->已填单
	 * 
	 * @param numbers
	 *            快递单号DTO
	 * @param operator
	 *            操作员Id
	 */
	void fillExpressNumbers(List<ExpressNumberDTO> numbers, Integer operator);

	/**
	 * 出库扫描
	 * <p/>
	 * 扫描快递单号，匹配到订单后，检查订单是否允许出库：<br/>
	 * 如果允许，更新订单物流状态：已填单->已出库；<br/>
	 * 如果不允许，抛出异常。
	 * 
	 * @param repoId
	 *            仓库Id
	 * @param expressNumber
	 * @param operator
	 */
	OrderVO sendOut(Integer repoId, String expressNumber, Integer operator);
	
	/**
	 * 按照状态查询订单
	 * @param companyId 公司Id
	 * @param payStatus 资金状态
	 * @param expressStatus 物流状态
	 * @param storageStatus 配货状态
	 * @param page 分页参数
	 * @return
	 */
	Page<OrderVO> findOrdersByStatus(Integer companyId, PayStatus payStatus,
			ExpressStatus expressStatus, StorageStatus storageStatus, Pageable page);
	
	/**
	 * 按照具体信息查询订单
	 * @param companyId 公司Id
	 * @param cond 订单页查询条件
	 * @param page 分页参数
	 * @return
	 */
	Page<OrderVO> findOrdersByCondition(Integer companyId, OrderQueryCondition cond, Pageable page);
}
