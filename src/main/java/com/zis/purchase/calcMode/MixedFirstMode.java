package com.zis.purchase.calcMode;

import com.zis.purchase.calculate.BookAmountCalculateInterface;

public class MixedFirstMode implements CalculateModeInterface {
	
	private BookAmountCalculateInterface mixedCalculater;
	private BookAmountCalculateInterface defaultCalculate;
	
	public Integer doCalculate(int bookId) {
		Integer result = null;
		
		result = mixedCalculater.calculate(bookId);
		if (result != null && result > 0){
			return result;
		}
		
		return defaultCalculate.calculate(bookId);
	}

	public void setMixedCalculater(BookAmountCalculateInterface mixedCalculater) {
		this.mixedCalculater = mixedCalculater;
	}

	public void setDefaultCalculate(BookAmountCalculateInterface defaultCalculate) {
		this.defaultCalculate = defaultCalculate;
	}
}
