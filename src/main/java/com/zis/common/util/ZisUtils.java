package com.zis.common.util;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * 公用的工具包
 * 
 * @author yz
 * 
 */
public class ZisUtils {
	
	public static Date format(String dt, Logger logger) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		try {
			return dateFormat.parse(dt);
		} catch (ParseException e) {
			logger.error("出版时间转换失败", e);
			throw new RuntimeException(e);
		}
	}

	public static Date youluNet(String dt, Logger logger) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月");
		try {
			return dateFormat.parse(dt);
		} catch (ParseException e) {
			logger.error("出版时间转换失败", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取系统当前TS
	 * 
	 * @return
	 */
	public static Timestamp getTS() {
		return new Timestamp(new Date().getTime());
	}

	/**
	 * 获取指定时间的TS
	 * 
	 * @return
	 */
	public static Timestamp getTS(Date date) {
		if (date == null) {
			return null;
		}
		return new Timestamp(date.getTime());
	}

	/**
	 * 获取指定时间的TS
	 * 
	 * @return
	 */
	public static Timestamp getTS(String dateString, String format) {
		try {
			return getTS(new SimpleDateFormat(format).parse(dateString));
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 生成时间字符串
	 * 
	 * @param format
	 * @return
	 */
	public static String getDateString(String format) {
		return getDateString(format, new Date());
	}

	/**
	 * 生成时间字符串
	 * 
	 * @param format
	 * @return
	 */
	public static String getDateString(String format, Date date) {
		return new SimpleDateFormat(format).format(date);
	}

	/**
	 * GET请求的中文字符转换，如果转换失败，会返回原内容
	 * 
	 * @param str
	 * @return
	 */
	public static String convertGetRequestCHN(String str) {
		try {
			return new String(str.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}

	/**
	 * 时间字符串转换为日期
	 * 
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static Date stringToDate(String dateStr, String pattern) {
		try {
			return new SimpleDateFormat(pattern).parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 时间字符串转换为日期
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date stringToDate(String dateStr) {
		Date date = stringToDate(dateStr, "yyyy-MM");
		if (date == null) {
			date = stringToDate(dateStr, "yyyy/MM");
		}
		return date;
	}
}
