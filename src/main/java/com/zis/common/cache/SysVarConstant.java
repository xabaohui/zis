package com.zis.common.cache;

/**
 * 系统变量
 * @author yz
 *
 */
public enum SysVarConstant {
	/** 采购计划-默认采购量 */
	PURCHASE_DEFAULT_REQURIE_AMT("defaultAmount"),
	/** 采购计划-混合定量模式-销售百分比 */
	PURCHASE_SALES_PERCENT("salesPercent"),
	/** 采购计划-需求定量模式-市场份额 */
	PURCHASE_REQUIREMENT_PORTIO("requiremenPortio"),
	/** 采购计划-销售定量模式-市场份额 */
	PURCHASE_SALES_PORTIO("salsePortio"),
	/** 采购计划-过期图书采购策略 */
	PURCHASE_STRATEGY_ALTER_EDITION_ALLOW("alterEditionAllow"),
	/** 采购计划-人工定量是否优先 */
	PURCHASE_STRATEGY_MANUAL_FIRST("manualDecisionFirst"),
	/** 采购计划-图书采购计划定量策略 */
	PURCHASE_STRATEGY_AMT_CALCULATE("amountCalcStrategy");
	
	private String keyName;
	
	private SysVarConstant(String keyName) {
		this.keyName = keyName;
	}

	public String getKeyName() {
		return keyName;
	}
}
