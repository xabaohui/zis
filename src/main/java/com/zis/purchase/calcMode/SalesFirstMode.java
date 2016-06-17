package com.zis.purchase.calcMode;

import com.zis.purchase.calculate.BookAmountCalculateInterface;

public class SalesFirstMode implements CalculateModeInterface {
	
	private BookAmountCalculateInterface requirementCalculater;
	private BookAmountCalculateInterface salesCalculater;
	private BookAmountCalculateInterface defaultCalculate;
	
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
		
		return defaultCalculate.calculate(bookId);
	}

	public void setRequirementCalculater(
			BookAmountCalculateInterface requirementCalculater) {
		this.requirementCalculater = requirementCalculater;
	}

	public void setSalesCalculater(BookAmountCalculateInterface salesCalculater) {
		this.salesCalculater = salesCalculater;
	}

	public void setDefaultCalculate(BookAmountCalculateInterface defaultCalculate) {
		this.defaultCalculate = defaultCalculate;
	}
}
