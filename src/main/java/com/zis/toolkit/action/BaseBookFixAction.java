package com.zis.toolkit.action;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public abstract class BaseBookFixAction extends ActionSupport {

	protected static final long serialVersionUID = 1L;
	private Integer maxShowCount = 20;

	protected void showResult(List<String> list) {
		this.addActionMessage("成功处理记录条数：" + list.size());
		ActionContext context = ActionContext.getContext();
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
