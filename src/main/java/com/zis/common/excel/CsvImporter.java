/**
 * 
 */
package com.zis.common.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author lvbin
 * 2015-8-14
 * @param <T>
 */
public class CsvImporter<T> extends FileImporter<T>{

	/**
	 * @param inputStream
	 * @param templateName
	 */
	public CsvImporter(InputStream inputStream, String templateName) {
		super(inputStream, templateName);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.his.common.excel.AbstractFileImporter#getDate(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Object getDate(Integer rowNums, Integer index) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.his.common.excel.AbstractFileImporter#getDouble(java.lang.Integer, int)
	 */
	@Override
	public Double getDouble(Integer rowNums, int index) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.his.common.excel.AbstractFileImporter#getInteger(java.lang.Integer, int)
	 */
	@Override
	public Integer getInteger(Integer rowNums, int index) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.his.common.excel.AbstractFileImporter#getRowNums()
	 */
	@Override
	protected Integer getRowNums() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.his.common.excel.AbstractFileImporter#getString(java.lang.Integer, int)
	 */
	@Override
	public String getString(Integer rowNums, int index) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.his.common.excel.AbstractFileImporter#loadFileFormat(java.io.InputStream)
	 */
	@Override
	protected List<String> loadFileFormat(InputStream input) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
