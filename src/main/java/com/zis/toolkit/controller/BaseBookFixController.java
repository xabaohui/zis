package com.zis.toolkit.controller;

import java.util.List;

import org.springframework.ui.ModelMap;

public abstract class BaseBookFixController {

	protected static final long serialVersionUID = 1L;
	private Integer maxShowCount = 20;

	protected void showResult(List<String> list, ModelMap context) {
		context.put("actionMessage", "成功处理记录条数：" + list.size());
		Integer max = list.size() > maxShowCount ? maxShowCount : list.size();
		context.put("showResult", list.subList(0, max));
	}

	public Integer getMaxShowCount() {
		return maxShowCount;
	}

	public void setMaxShowCount(Integer maxShowCount) {
		this.maxShowCount = maxShowCount;
	}
}
