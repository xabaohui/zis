package com.zis.common.util;

import org.apache.commons.lang.StringUtils;

/**
 * 整理文本工具类
 * 
 * @author yz
 * 
 */
public class TextClearUtils {

	/** 匹配模式：不做任何匹配限制 */
	public static final int MATCH_MODE_NONE = 0;
	/** 匹配模式：强制匹配开始字符 */
	public static final int MATCH_MODE_FORCE_START = 1;
	/** 匹配模式：强制匹配结束字符 */
	public static final int MATCH_MODE_FORCE_END = 2;
	/** 匹配模式：强制要求不能匹配开始字符 */
	public static final int MATCH_MODE_FORCE_NONE_START = 3;
	/** 匹配模式：强制要求不能匹配结束字符 */
	public static final int MATCH_MODE_FORCE_NONE_END = 4;

	/**
	 * 用空格替换特殊字符，特殊字符有：、 ， ： ； 。 , ; : .
	 * 
	 * @param bookAuthor
	 * @return
	 */
	public static String clearSpecialChar(String text) {
		return clearSpecialChar(text, " ");
	}

	/**
	 * 用指定字符替换特殊字符，特殊字符有：、 ， ： ； 。 , ; : . () （） / - |
	 * 
	 * @param text
	 * @param replacement
	 * @return
	 */
	public static String clearSpecialChar(String text, String replacement) {
		String[] specialCharSet = { "、", "，", "：", "；", "。", ",", ":", ";", ".", "(", ")", "（", "）", "/", "-", "|"};
		for (String character : specialCharSet) {
			text = text.replace(character, replacement);
		}
		return text;
	}

	/**
	 * 截取字符串，从beginStr之后开始截取，到endStr之前
	 * 
	 * @param text
	 * @param beginStr
	 * @param endStr
	 * @return
	 */
	public static String subString(String text, String beginStr, String endStr) {
		return subString(text, beginStr, endStr, MATCH_MODE_NONE);
	}

	/**
	 * 截取字符串，从beginStr之后开始截取
	 * 
	 * @param text
	 * @param beginStr
	 * @return
	 */
	public static String subString(String text, String beginStr) {
		return subString(text, beginStr, null, MATCH_MODE_NONE);
	}

	/**
	 * 截取字符串，从beginStr之后开始截取，到endStr之前
	 * 
	 * @param text
	 * @param beginStr
	 *            开始字符
	 * @param endStr
	 *            结束字符
	 * @param matchMode
	 *            匹配模式，例如TextClearUtils.MATCH_MODE_NONE
	 * @return
	 */
	public static String subString(String text, String beginStr, String endStr,
			int matchMode) {
		if (StringUtils.isBlank(text)) {
			throw new RuntimeException(
					"illegal argument, text should not be null");
		}
		if (StringUtils.isBlank(beginStr)) {
			throw new RuntimeException(
					"illegal argument, text beginStr not be null");
		}
		// 强制匹配开始字符
		if (matchMode == MATCH_MODE_FORCE_START) {
			if (!text.startsWith(beginStr)) {
				return text;
			}
		}
		// 强制匹配结束字符
		else if (matchMode == MATCH_MODE_FORCE_END) {
			if (StringUtils.isBlank(endStr)) {
				throw new RuntimeException("强制匹配结束字符模式下，endStr不能为空");
			}
			if (!text.endsWith(endStr)) {
				return text;
			}
		}
		// 强制要求不能匹配开始字符
		else if (matchMode == MATCH_MODE_FORCE_NONE_START) {
			if (text.startsWith(beginStr)) {
				return text;
			}
		}
		// 强制要求不能匹配结束字符
		else if (matchMode == MATCH_MODE_FORCE_NONE_END) {
			if (StringUtils.isBlank(endStr)) {
				throw new RuntimeException("强制要求不能匹配结束字符模式下，endStr不能为空");
			}
			if (text.endsWith(endStr)) {
				return text;
			}
		}
		// 不做任何匹配限制
		else if (matchMode == MATCH_MODE_NONE) {

		} else {
			throw new RuntimeException("错误的匹配模式：" + matchMode);
		}
		int beginIndex = text.indexOf(beginStr);
//		int beginIndex = text.indexOf(beginStr) + beginStr.length();
		// 有结束字符
		if (StringUtils.isNotBlank(endStr)) {
			int endIndex = text.indexOf(endStr);
			if (beginIndex >= 0 && endIndex >= 0 && endIndex > beginIndex) {
				return text.substring(beginIndex + beginStr.length(), endIndex);
			}
		}
		// 没有结束字符
		else {
			if (beginIndex >= 0) {
				return text.substring(beginIndex + beginStr.length());
			}
		}
		return text;
	}
	
	/**
	 * 如果text包含matchStr，则保留matchStr之前的部分，其余部分全部删除<br/>
	 * 如果text是以matchStr开始，该方法不会删除字符串，会返回原字符
	 * @param text
	 * @param matchStr
	 * @return
	 */
	public static String deleteString(String text, String matchStr) {
		return deleteString(text, matchStr, null, MATCH_MODE_FORCE_NONE_START);
	}

	/**
	 * 如果text同时包含firstMatchStr和secondMatchStr，则保留firstMatchStr之前的部分，其余部分全部删除<br/>
	 * 如果text是以firstMatchStr开始，该方法不会删除字符串，会返回原字符
	 * 
	 * @param text
	 * @param firstMatchStr
	 * @param secondMatchStr
	 * @param matchMode
	 * @return
	 */
	public static String deleteString(String text, String firstMatchStr,
			String secondMatchStr, int matchMode) {
		if (StringUtils.isBlank(text)) {
			throw new RuntimeException(
					"illegal argument, text should not be null");
		}
		if (StringUtils.isBlank(firstMatchStr)) {
			throw new RuntimeException(
					"illegal argument, firstMatchStr should not be null");
		}
		// 不支持MATCH_MODE_FORCE_START模式
		if (matchMode == MATCH_MODE_FORCE_START) {
			throw new RuntimeException("该方法不支持MATCH_MODE_FORCE_START模式");
		}
		// 强制匹配结束字符
		else if (matchMode == MATCH_MODE_FORCE_END) {
			if (StringUtils.isBlank(secondMatchStr)) {
				if (!text.endsWith(firstMatchStr)) {
					return text;
				}
			} else {
				if (!text.endsWith(secondMatchStr)) {
					return text;
				}
			}
		}
		// 强制要求不能匹配开始字符
		else if (matchMode == MATCH_MODE_FORCE_NONE_START) {
			if (text.startsWith(firstMatchStr)) {
				return text;
			}
		}
		// 强制要求不能匹配结束字符
		else if (matchMode == MATCH_MODE_FORCE_NONE_END) {
			if (StringUtils.isBlank(secondMatchStr)) {
				if (text.endsWith(firstMatchStr)) {
					return text;
				}
			} else {
				if (text.endsWith(secondMatchStr)) {
					return text;
				}
			}
		}
		// 不做任何匹配限制
		else if (matchMode == MATCH_MODE_NONE) {

		} else {
			throw new RuntimeException("错误的匹配模式：" + matchMode);
		}
		// secondMatchStr 为空
		int endIndex = text.indexOf(firstMatchStr);
		if (StringUtils.isBlank(secondMatchStr)) {
			if (endIndex > 0) {//如果text是以firstMatchStr开始，该方法不会删除
				return text.substring(0, endIndex);
			}
		}
		// secondMatchStr 不为空
		else {
			int secondEndIndex = text.indexOf(secondMatchStr, endIndex);
			if(endIndex > 0 && secondEndIndex > 0) {//如果text是以firstMatchStr开始，该方法不会删除
				return text.substring(0, endIndex);
			}
		}
		return text;
	}

}
