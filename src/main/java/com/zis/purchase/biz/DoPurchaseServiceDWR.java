package com.zis.purchase.biz;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * shiro 权限验证DWR访问 代理service
 * @author think
 *
 */
@Component
public class DoPurchaseServiceDWR{
	
	@Autowired
	private DoPurchaseService doPurchaseService;
	
	@RequiresPermissions(value = "purchase:management")
	public String addManualDecision(Integer bookId, Integer amount) {
		return this.doPurchaseService.addManualDecision(bookId, amount);
	}

	@RequiresPermissions(value = "purchase:management")
	public String removeManualDecision(Integer bookId) {
		return this.doPurchaseService.removeManualDecision(bookId);
	}
	
	@RequiresPermissions(value = "purchase:management")
	public String recalculateRequireAmount(Integer bookId){
		return this.doPurchaseService.recalculateRequireAmount(bookId);
	}
	
	@RequiresPermissions(value = "purchase:management")
	public String updateBookStock(Integer bookId, Integer amount){
		return this.doPurchaseService.updateBookStock(bookId, amount);
	}
	
	@RequiresPermissions(value = "purchase:management")
	public String addBlackList(Integer bookId){
		return this.doPurchaseService.addBlackList(bookId);
	}
	
	@RequiresPermissions(value = "purchase:management")
	public String addWhiteList(Integer bookId){
		return this.doPurchaseService.addWhiteList(bookId);
	}
	@RequiresPermissions(value = "purchase:management")
	public String deleteBlackOrWhiteList(Integer bookId){
		return this.doPurchaseService.deleteBlackOrWhiteList(bookId);
	}
	
	@RequiresPermissions(value = "purchase:management")
	public String updateAssociateTempImportDetailWithBookInfo(Integer tempImportDetailId, Integer bookId){
		return this.doPurchaseService.updateAssociateTempImportDetailWithBookInfo(tempImportDetailId, bookId);
	}
}
