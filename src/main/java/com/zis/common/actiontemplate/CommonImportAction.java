/**
 * 
 */
package com.zis.common.actiontemplate;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.zis.common.excel.ExcelImporter;
import com.zis.common.excel.FileImporter;

/**
 * 临时数据（商品信息、库存、采购等）导入Action
 * 
 * @author lvbin 2014-10-25
 */
public abstract class CommonImportAction<T> extends ActionSupport {

	protected static final long serialVersionUID = 1L;

	protected File excelFile;
	protected String excelFileFileName;
	protected String templatePath;
	protected Integer headerRownums;
	
	protected final List<T> cannotImport = new ArrayList<T>();
	protected final Logger logger = initLogger();

	/**
	 * 导入文件主方法
	 * 
	 * @return
	 */
	@Validations(
	/* �������� */
	requiredFields = { @RequiredFieldValidator(fieldName = "excelFile", key = "文件必须输入"), })
	public String upload() {
		// 设置模板文件，用于检验导入文件是否合法
		templatePath = initTemplatePath();
		headerRownums = setHeaderRowNums(1);
		try {
			// 初始化导入器
			FileImporter<T> im = new ExcelImporter<T>(new FileInputStream(excelFile), templatePath);
			im.setHeaderRowNums(headerRownums);

			// 检验导入文件是否合法
			String errMsg = im.validate();
			if (StringUtils.isNotBlank(errMsg)) {
				this.addActionError(errMsg);
				return INPUT;
			}

			// 解析文件并入库
			Map<String, Integer> propMapping = initPropMapping();
			T instance = getInstance();
			List<T> list = im.parse(instance, propMapping);
			if(list.isEmpty()) {
				this.addActionError("导入失败，文件为空");
				return INPUT;
			}
			saveImportRecord(list);

			// 导入失败的数据处理
			if (!cannotImport.isEmpty()) {
				StringBuilder builder = new StringBuilder();
				for (T failRecord : cannotImport) {
					builder.append(getFaildRecordMessage(failRecord));
				}
				this.addActionError("导入失败的记录有：" + builder.toString());
				return INPUT;
			}
			return SUCCESS;
		} catch (Exception e) {
			this.addActionError("导入失败: " + e.getMessage());
			logger.error(e.getMessage(), e);
			return INPUT;
		}
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
	 * @return
	 */
	protected abstract Logger initLogger();
	
	/**
	 * 获取T的对象实例
	 * @return
	 */
	protected abstract T getInstance();

	/**
	 * 失败描述信息
	 * @param failRecord
	 * @return
	 */
	protected abstract String getFaildRecordMessage(T failRecord);

	/**
	 * 保存导入的记录
	 * @param list
	 */
	protected abstract void saveImportRecord(List<T> list);

	/**
	 * 初始化对象的映射文件
	 * @return
	 */
	protected abstract Map<String, Integer> initPropMapping();

	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}

	public void setExcelFileFileName(String excelFileFileName) {
		this.excelFileFileName = excelFileFileName;
	}
	
	public File getExcelFile() {
		return excelFile;
	}
	
	public String getExcelFileFileName() {
		return excelFileFileName;
	}
}
