package com.zis.common.util;

/**
 * 过期图书采购策略常量类
 * @author yz
 *
 */
public enum AlterEditionPurchaseStrategyEnum {

	GET_NONE(0),
	GET_ALL(1),
	GET_WHITE_LIST(2);
	
	private Integer value;

	private AlterEditionPurchaseStrategyEnum(Integer value) {
		this.value = value;
	}
	
	public Integer getValue() {
		return value;
	}
	
	public static AlterEditionPurchaseStrategyEnum getEnumByValue(Integer value) {
		for (AlterEditionPurchaseStrategyEnum st : values()) {
			if(st.getValue().equals(value)) {
				return st;
			}
		}
		return null;
	}
}
