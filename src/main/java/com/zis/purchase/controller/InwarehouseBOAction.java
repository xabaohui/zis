package com.zis.purchase.controller;

import org.apache.commons.lang3.StringUtils;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.zis.purchase.biz.DoPurchaseService;
import com.zis.purchase.dto.InwarehouseCreateDTO;
import com.zis.purchase.dto.InwarehouseCreateResult;
import com.zis.purchase.dto.InwarehouseDealtResult;

/**
 * 入库核心业务逻辑处理Action
 * <p/>
 * service层由于要借用RuntimeException控制Transaction，<br/>
 * 而dwr在Exception方面的处理相对不太灵活，<br/>
 * 因此使用这个类来进行桥接dwr和service
 * 
 * @author yz
 * 
 */
@Controller
@RemoteProxy(name = "inwarehouseBO")
public class InwarehouseBOAction {
	
	@Autowired
	private DoPurchaseService doPurchaseService;
	
	/**
	 * 创建采购入库单
	 * 
	 * @param inwarehouse
	 * @return 入库单ID
	 */
	public InwarehouseCreateResult createInwarehouse(InwarehouseCreateDTO inwarehouse) {
		try {
			return this.doPurchaseService.createInwarehouse(inwarehouse);
		} catch (Exception e) {
			InwarehouseCreateResult result = new InwarehouseCreateResult();
			result.setSuccess(false);
			result.setFailReason(e.getMessage());
			return result;
		}
	}
	
	/**
	 * 执行入库操作
	 * 
	 * @param inwarehouseId
	 *            入库单ID
	 * @param posLabel
	 *            库位标签
	 * @param bookId
	 *            图书ID
	 * @param amount
	 *            入库数量
	 */
	@RemoteMethod
	public InwarehouseDealtResult doInwarehouse(Integer inwarehouseId, String posLabel, Integer bookId,
			Integer amount) {
		try {
			return this.doPurchaseService.applyInwarehouse(inwarehouseId, posLabel, bookId, amount);
		} catch (Exception e) {
			InwarehouseDealtResult result = new InwarehouseDealtResult();
			result.setSuccess(false);
			result.setFailReason(e.getMessage());
			e.printStackTrace();
			return result;
		}
	}
	
	/**
	 * 删除入库详情记录
	 * @param detailId
	 * @return
	 */
	@RemoteMethod
	public String deleteInwarehouseDetail(Integer detailId) {
		try {
			this.doPurchaseService.deleteInwarehouseDetail(detailId);
			return StringUtils.EMPTY;
		} catch (Exception e) {
			return "操作失败," + e.getMessage();
		}
	}
	
	/**
	 * 删除入库单（置为无效）
	 * @param inwarehouseId
	 * @return
	 */
	@RemoteMethod
	public String deleteInwarehouse(Integer inwarehouseId) {
		try {
			this.doPurchaseService.deleteInwarehouse(inwarehouseId);
			return StringUtils.EMPTY;
		} catch (Exception e) {
			return "操作失败," + e.getMessage();
		}
	}
}
