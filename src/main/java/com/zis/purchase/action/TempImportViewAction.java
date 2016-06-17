package com.zis.purchase.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.zis.purchase.bean.TempImportTask;
import com.zis.purchase.bean.TempImportTaskBizType;
import com.zis.purchase.bean.TempImportTaskStatus;
import com.zis.purchase.biz.DoPurchaseService;
import com.zis.purchase.dto.TempImportTaskView;

/**
 * 已导入的临时数据查看/处理Action
 * 
 * @author yz
 * 
 */
public class TempImportViewAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private Integer taskId;
	private String status;

	private DoPurchaseService doPurchaseService;

	/**
	 * 查看task列表
	 * 
	 * @return
	 */
	public String viewTask() {
		List<TempImportTask> list = this.doPurchaseService
				.findAllTempImportTask();
		List<TempImportTaskView> viewList = new ArrayList<TempImportTaskView>();
		for (TempImportTask task : list) {
			viewList.add(buildTaskView(task));
		}
		ActionContext context = ActionContext.getContext();
		context.put("taskList", viewList);
		return SUCCESS;
	}

	private TempImportTaskView buildTaskView(TempImportTask task) {
		TempImportTaskView view = new TempImportTaskView();
		BeanUtils.copyProperties(task, view);
		view.setBizTypeDisplay(TempImportTaskBizType.getBizTypeDisplay(task
				.getBizType()));
		view.setStatusDisplay(TempImportTaskStatus.getDisplay(task.getStatus()));
		return view;
	}

	public void setDoPurchaseService(DoPurchaseService doPurchaseService) {
		this.doPurchaseService = doPurchaseService;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
