/**
 * 
 */
package com.zis.common.controllertemplate;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.zis.common.excel.ExcelImporter;
import com.zis.common.excel.FileImporter;

/**
 * 通用数据导入Action
 * 
 * @author lvbin 2014-10-25
 */
public abstract class CommonImportController<T> {

	protected final Logger logger = initLogger();

	/**
	 * 导入文件主方法
	 * 
	 * @return
	 */
	//TODO 验证框架
	// @Validations(
	// /* �������� */
	// requiredFields = { @RequiredFieldValidator(fieldName = "excelFile", key =
	// "文件必须输入"), })
	public String upload(InputStream fileInputStream, String memo) {
		// 设置模板文件，用于检验导入文件是否合法
		String templatePath = initTemplatePath();
		Integer headerRownums = setHeaderRowNums(1);
		try {
			// 初始化导入器
			FileImporter<T> im = new ExcelImporter<T>(fileInputStream, templatePath);
			im.setHeaderRowNums(headerRownums);

			// 检验导入文件是否合法
			String errMsg = im.validate();
			if (StringUtils.isNotBlank(errMsg)) {
				// TODO 验证框架
				// this.addActionError(errMsg);
				return getFailPage();
			}
			String subCheck = subCheckFileFormat(im.loadFactHeader());
			if (StringUtils.isNotBlank(subCheck)) {
				// TODO 验证框架
				// this.addActionError(subCheck);
				return getFailPage();
			}

			// 解析文件并入库
			Map<String, Integer> propMapping = initPropMapping();
			T instance = getInstance();
			List<T> list = im.parse(instance, propMapping);
			if (list.isEmpty()) {
				// TODO 验证框架
				// this.addActionError("导入失败，文件为空");
				return getFailPage();
			}
			saveImportRecord(list,memo);
			return getSuccessPage();
		} catch (Exception e) {
			// TODO 验证框架
			// this.addActionError("导入失败: " + e.getMessage());
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return getFailPage();
		}
	}

	/**
	 * 设置跳转成功路径
	 */
	protected abstract String getSuccessPage();

	/**
	 * 设置跳转失败路径
	 */
	protected abstract String getFailPage();

	/**
	 * 检查文件格式，由子类进行扩展
	 * 
	 * @param factHeader
	 * @return 返回错误原因，如果检验通过，返回null
	 */
	protected String subCheckFileFormat(List<String> factHeader) {
		return null;
	}

	/**
	 * 设置表头的行数，默认为1
	 */
	protected Integer setHeaderRowNums(Integer num) {
		return num;
	}

	/**
	 * 设置模板文件的路径
	 */
	protected abstract String initTemplatePath();

	/**
	 * 初始化logger
	 * 
	 * @return
	 */
	protected abstract Logger initLogger();

	/**
	 * 获取T的对象实例
	 * 
	 * @return
	 */
	protected abstract T getInstance();

	/**
	 * 失败描述信息
	 * 
	 * @param failRecord
	 * @return
	 */
	protected abstract String getFaildRecordMessage(T failRecord);

	/**
	 * 保存导入的记录
	 * 
	 * @param list
	 */
	protected abstract void saveImportRecord(List<T> list,String memo);

	/**
	 * 初始化对象的映射文件
	 * 
	 * @return
	 */
	protected abstract Map<String, Integer> initPropMapping();

}
