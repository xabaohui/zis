package com.zis.requirement.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.zis.requirement.biz.BookAmountService;
import com.zis.requirement.dto.RequirementCollectScheduleDTO;

/**
 * 需求采集进度Action
 * 
 * @author yz
 * 
 */
public class RequirementCollectScheduleAction extends ActionSupport implements
		ServletResponseAware {

	private static final long serialVersionUID = 1L;
	private boolean groupByOperator;
	private BookAmountService bookAmountService;
	private HttpServletResponse response;

	/**
	 * 导出进度
	 * 
	 * @return
	 */
	public String exportSchedule() {
		List<RequirementCollectScheduleDTO> list = bookAmountService
				.findRequirementCollectSchedule(groupByOperator);
		Workbook book = new HSSFWorkbook();
		Sheet sheet = book.createSheet();
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("学校");
		row.createCell(1).setCellValue("学院");
		row.createCell(2).setCellValue("专业");
		row.createCell(3).setCellValue("年级");
		row.createCell(4).setCellValue("学期");
		row.createCell(5).setCellValue("收录员");
		row.createCell(6).setCellValue("条码数量");

		int i = 1;
		for (RequirementCollectScheduleDTO dto : list) {
			// XXX 过滤大一和大四第二学期
			if (dto.getGrade() == 1
					|| (dto.getGrade() == 4 && dto.getTerm() == 2)) {
				continue;
			}
			createNewRow(sheet, i, dto);
			i++;
		}

		// 导出
		setResponseHeader();
		try {
			book.write(response.getOutputStream());
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	/**
	 * @param sheet
	 * @param i
	 * @param line0
	 */
	private void createNewRow(Sheet sheet, int index,
			RequirementCollectScheduleDTO dto) {
		Row row = sheet.createRow(index);
		// "学校", "学院", "专业", "年级", "学期", "收录员", "条码数量"
		row.createCell(0).setCellValue(dto.getCollege());
		row.createCell(1).setCellValue(dto.getInstitute());
		row.createCell(2).setCellValue(dto.getPartName());
		row.createCell(3).setCellValue("第" + dto.getGrade() + "学年");
		row.createCell(4).setCellValue("第" + dto.getTerm() + "学期");
		row.createCell(5).setCellValue(dto.getOperator());
		row.createCell(6).setCellValue(dto.getCount());
	}

	/** 设置响应头 */
	private void setResponseHeader() {
		try {
			response.setContentType("application/msexcel;charset=UTF-8"); // 两种方法都可以
			// response.setContentType("application/octet-stream;charset=iso-8859-1");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ java.net.URLEncoder.encode("教材需求统计.csv", "UTF-8"));
			// 客户端不缓存
			response.addHeader("Pargam", "no-cache");
			response.addHeader("Cache-Control", "no-cache");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.struts2.interceptor.ServletResponseAware#setServletResponse
	 * (javax.servlet.http.HttpServletResponse)
	 */
	public void setServletResponse(HttpServletResponse arg0) {
		this.response = arg0;
	}

	public boolean isGroupByOperator() {
		return groupByOperator;
	}

	public void setGroupByOperator(boolean groupByOperator) {
		this.groupByOperator = groupByOperator;
	}

	public void setBookAmountService(BookAmountService bookAmountService) {
		this.bookAmountService = bookAmountService;
	}
}
