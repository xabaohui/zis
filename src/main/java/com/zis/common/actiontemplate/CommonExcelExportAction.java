package com.zis.common.actiontemplate;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.zis.common.util.ZisUtils;

/**
 * 通用的数据导出Action
 * 
 * @author yz
 * 
 */
public abstract class CommonExcelExportAction<T> extends ActionSupport implements
		ServletResponseAware {

	private static final long serialVersionUID = 1L;
	private Integer colnum;
	private HttpServletResponse response;

	/**
	 * 导出数据
	 * 
	 * @return
	 */
	public String export() {
		// 获取需要导出的数据
		List<T> list = queryExportData();
		Collection<T> listAfterTransform = TransformResultList(list);
		// 创建工作表
		Workbook book = new HSSFWorkbook();
		Sheet sheet = book.createSheet();
		// 创建表头 
		Row row = sheet.createRow(0);
		String[] tableHeaders = getTableHeaders();
		colnum = tableHeaders.length;
		for (int i=0; i<colnum; i++) {
			row.createCell(i).setCellValue(tableHeaders[i]);
		}
		// 填充数据
		int i = 1;
		for (T record : listAfterTransform) {
			// 过滤需要跳过的记录
			if (isSkip(record)) {
				continue;
			}
			createNewRow(sheet.createRow(i), getRowDatas(record));
			i++;
		}
		// 导出
		setResponseHeader();
		try {
			book.write(response.getOutputStream());
			response.getOutputStream().flush();
			response.getOutputStream().close();
			return SUCCESS;
		} catch (IOException e) {
			throw new RuntimeException("导出数据失败", e);
		}
	}

	/**
	 * 转换结果集，由子类进行扩展
	 * @param list
	 * @return
	 */
	protected Collection<T> TransformResultList(List<T> list) {
		return list;
	}

	/**
	 * 生成表头
	 * @return
	 */
	protected abstract String[] getTableHeaders();
	
	/**
	 * 生成单行数据
	 * @param record
	 * @return
	 */
	protected abstract String[] getRowDatas(T record);

	/**
	 * 跳过部分不符合条件的记录，子类可进行扩展
	 * @param record
	 * @return
	 */
	protected boolean isSkip(T record) {
		return false;
	}

	/**
	 * 查询出需要导出的记录
	 * @return
	 */
	protected abstract List<T> queryExportData();

	/**
	 * 填充单行数据
	 * @param sheet
	 * @param i
	 * @param line0
	 */
	protected void createNewRow(Row row, String[] rowData) {
		for(int i=0; i<colnum; i++) {
			String data = rowData[i] == null ? "" : rowData[i];
			row.createCell(i).setCellValue(data);
		}
	}

	/** 设置响应头 */
	private void setResponseHeader() {
		try {
			response.setContentType("application/msexcel;charset=UTF-8"); // 两种方法都可以
			// response.setContentType("application/octet-stream;charset=iso-8859-1");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ java.net.URLEncoder.encode(setExportFileName(), "UTF-8"));
			// 客户端不缓存
			response.addHeader("Pargam", "no-cache");
			response.addHeader("Cache-Control", "no-cache");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 设置导出文件名
	 * @return
	 */
	protected String setExportFileName() {
		return "导出数据-" + ZisUtils.getDateString("yyyy-MM-dd") + ".xls";
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
}
