package com.zis.purchase.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zis.purchase.bean.TempImportTask;
import com.zis.purchase.bean.TempImportTaskBizTypeEnum;
import com.zis.purchase.bean.TempImportTaskStatus;
import com.zis.purchase.biz.DoPurchaseService;
import com.zis.purchase.dto.TempImportTaskView;

/**
 * 已导入的临时数据查看/处理Action
 * 
 * @author yz
 * 
 */
@Controller
@RequestMapping(value="/purchase")
public class TempImportViewController {

//	private Integer taskId;
//	private String status;
	@Autowired
	private DoPurchaseService doPurchaseService;

	/**
	 * 查看task列表
	 * 
	 * @return
	 */
	@RequestMapping(value="/viewTempImportTask")
	public String viewTask(ModelMap context) {
		List<TempImportTask> list = this.doPurchaseService
				.findAllTempImportTask();
		List<TempImportTaskView> viewList = new ArrayList<TempImportTaskView>();
		for (TempImportTask task : list) {
			viewList.add(buildTaskView(task));
		}
		context.put("taskList", viewList);
		return "purchase/tempImportTaskView";
	}

	private TempImportTaskView buildTaskView(TempImportTask task) {
		TempImportTaskView view = new TempImportTaskView();
		BeanUtils.copyProperties(task, view);
		view.setBizTypeDisplay(TempImportTaskBizTypeEnum.parseEnum(task.getBizType()).getDisplayValue());
		view.setStatusDisplay(TempImportTaskStatus.getDisplay(task.getStatus()));
		return view;
	}
}
