package com.zis.purchase.calcMode;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zis.purchase.calculate.BookAmountCalculateInterface;

@Component
public class SalesFirstMode implements CalculateModeInterface {

	@Resource
	private BookAmountCalculateInterface requirementCalculater;
	@Resource
	private BookAmountCalculateInterface salesCalculater;
	@Resource
	private BookAmountCalculateInterface defaultCalculater;
	
	public Integer doCalculate(int bookId) {
		Integer result = null;
		
		result = salesCalculater.calculate(bookId);
		if (result!= null && result > 0){
			return result;
		}
		
		result = requirementCalculater.calculate(bookId);
		if (result!= null && result > 0){
			return result;
		}
		
		return defaultCalculater.calculate(bookId);
	}
}
