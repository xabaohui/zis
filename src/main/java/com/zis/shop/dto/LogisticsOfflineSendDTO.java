package com.zis.shop.dto;

import org.apache.commons.lang3.StringUtils;

public class LogisticsOfflineSendDTO {
	
	private Long tid;
	private String expressCompany;// 所有公司缩写
	private String expressNumber;

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	public String getExpressNumber() {
		return expressNumber;
	}

	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber;
	}

	public static enum ExpressCompany {
		
		SF("SF", "顺丰速运"),
		STO("STO", "申通快递"),
		YTO("YTO", "圆通快递"),
		BEST("BEST", "百世汇通"),
		EMS("EMS", "邮政特快专递"),
		TTK("TTK", "天天快递"),
		YUNDA("YUNDA", "韵达速递"),
		ZTO("ZTO", "中通快递");

		private String value;
		private String display;

		private ExpressCompany(String value, String display) {
			this.value = value;
			this.display = display;
		}

		/**
		 * 根据获得中文公司名称获取缩写
		 * 
		 * @param value
		 * @return
		 */
		public static String getValue(String display) {
			for (ExpressCompany ex : values()) {
				if (ex.getDisplay().contains(display))
					return ex.getValue();
			}
			return null;
		}
		
		/**
		 * 根据value获得枚举值
		 * 
		 * @param value
		 * @return
		 */
		public static ExpressCompany getEnum(String value) {
			for (ExpressCompany record : values()) {
				if (record.getValue().equals(value)) {
					return record;
				}
			}
			return null;
		}


		/**
		 * 判断value是否是预定义的
		 * 
		 * @param value
		 * @return
		 */
		public static boolean isDefined(String value) {
			if (StringUtils.isBlank(value)) {
				return false;
			}
			return getEnum(value) != null;
		}

		public String getValue() {
			return value;
		}

		public String getDisplay() {
			return display;
		}
	}

}
