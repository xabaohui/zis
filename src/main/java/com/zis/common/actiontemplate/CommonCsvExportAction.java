package com.zis.common.actiontemplate;

import java.io.BufferedOutputStream;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.zis.common.util.ZisUtils;

/**
 * 通用的数据导出Action
 * 
 * @author yz
 * 
 */
public abstract class CommonCsvExportAction<T> extends ActionSupport implements
		ServletResponseAware {

	private static final long serialVersionUID = 1L;
	private BufferedOutputStream bos;
	private HttpServletResponse response;
	
	protected Logger logger = Logger.getLogger(CommonCsvExportAction.class);

	/**
	 * 导出数据
	 * 
	 * @return
	 */
	public String export() {
		// 获取需要导出的数据
		List<T> list = queryExportData();
		Collection<T> transformedList = transformList(list);
		// 创建工作表
		setResponseHeader();
		// 创建表头 
		try {
			byte commonCsvHead[] = { (byte) 0xEF, (byte) 0xBB,
		            (byte) 0xBF };
			bos.write(commonCsvHead);
			bos.write(getTableHeaders().getBytes());
			bos.flush();
			// 填充数据
			for (T record : transformedList) {
				// 过滤需要跳过的记录
				bos.write(getRowDatas(record).getBytes());
				bos.flush();
			}
			bos.close();
			return SUCCESS;
		} catch (Exception e) {
			logger.error("导出数据失败" + e.getMessage(), e);
			throw new RuntimeException("导出数据失败", e);
		}
	}

	/**
	 * 转换结果集，由子类进行扩展，可以过滤掉部分不符合条件的数据，或增加、调整其中的部分记录
	 * @param list
	 * @return
	 */
	protected Collection<T> transformList(List<T> list) {
		return list;
	}

	/**
	 * 生成表头
	 * @return
	 */
	protected abstract String getTableHeaders();
	
	/**
	 * 生成单行数据
	 * @param record
	 * @return
	 */
	protected abstract String getRowDatas(T record);

//	/**
//	 * 跳过部分不符合条件的记录
//	 * @param record
//	 * @return
//	 */
//	protected boolean isSkip(T record) {
//		return false;
//	}

	/**
	 * 查询出需要导出的记录
	 * @return
	 */
	protected abstract List<T> queryExportData();

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
			this.bos = new BufferedOutputStream(response.getOutputStream());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 设置导出文件名
	 * @return
	 */
	protected String setExportFileName() {
		return "导出数据-" + ZisUtils.getDateString("yyyy-MM-dd") + ".csv";
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
