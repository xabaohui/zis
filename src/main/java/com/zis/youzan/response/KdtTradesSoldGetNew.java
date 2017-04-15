package com.zis.youzan.response;

import com.youzan.open.sdk.gen.v1_0_0.api.KdtTradesSoldGet;

public class KdtTradesSoldGetNew extends KdtTradesSoldGet {
	@SuppressWarnings("rawtypes")
	public Class getResultModelClass() {
		return KdtTradesSoldGetResultNew.class;
	}
}
