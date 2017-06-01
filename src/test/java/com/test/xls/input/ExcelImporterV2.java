/**
 * 
 */
package com.test.xls.input;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.zis.common.excel.FileImporter;

/**
 * excel ���빤����
 * 
 * @author lvbin 2015-8-13
 */
public class ExcelImporterV2<T> extends FileImporter<T> {

	private Sheet sheet;

	private Integer sheetCount;

	private Integer activeSheetIndex;

	/**
	 * 
	 * @param inputStream
	 *            �����ļ�
	 * @param templateName
	 *            ģ�����
	 */
	public ExcelImporterV2(InputStream inputStream, String templateName, Integer sheetCount) {
		super(inputStream, templateName);
		try {
			Workbook book = new HSSFWorkbook(inputStream);
			activeSheetIndex = book.getActiveSheetIndex();
			this.sheetCount = sheetCount;
			sheet = book.getSheetAt(sheetCount);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Integer getActiveSheetIndex() {
		return activeSheetIndex;
	}
	protected List<String> loadFileFormat(InputStream input) throws IOException {
		Workbook book = new HSSFWorkbook(input);
		Row headerRow = book.getSheetAt(sheetCount).getRow(headerRowNums - 1);
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < headerRow.getLastCellNum(); i++) {
			list.add(headerRow.getCell(i).getStringCellValue());
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.his.common.excel.AbstractFileImporter#getRowNums()
	 */
	@Override
	protected Integer getRowNums() {
		return sheet.getLastRowNum();
	}

	private Cell getCell(int rowNums, Integer index) {
		Row row = this.sheet.getRow(rowNums);
		if (row == null) {
			return null;
		}
		return row.getCell(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.his.common.excel.AbstractFileImporter#getDate(java.lang.Integer,
	 * java.lang.Integer)
	 */
	@Override
	public Object getDate(Integer rowNums, Integer index) {
		Cell cell = getCell(rowNums, index);
		if (cell == null) {
			return null;
		}
		return cell.getDateCellValue();
	}

	@Override
	public String getString(Integer rowNums, int index) {
		Cell cell = getCell(rowNums, index);
		if (cell == null) {
			return "";
		}
		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			return cell.getStringCellValue().trim();
		}
		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			return new DecimalFormat("###").format(cell.getNumericCellValue());
		}
		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.his.common.excel.AbstractFileImporter#getInteger(java.lang.Integer,
	 * int)
	 */
	@Override
	public Integer getInteger(Integer rowNums, int index) {
		Cell cell = getCell(rowNums, index);
		if (cell == null) {
			return null;
		}
		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			return Integer.parseInt(cell.getStringCellValue().trim());
		}
		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			return (int) cell.getNumericCellValue();
		}
		return (int) cell.getNumericCellValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.his.common.excel.AbstractFileImporter#getDouble(java.lang.Integer,
	 * int)
	 */
	@Override
	public Double getDouble(Integer rowNums, int index) {
		Cell cell = getCell(rowNums, index);
		if (cell == null) {
			return null;
		}
		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			if (StringUtils.isBlank(cell.getStringCellValue())) {
				return 0.0;
			} else {
				return Double.parseDouble(cell.getStringCellValue().trim());
			}
		}
		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			return cell.getNumericCellValue();
		}
		return cell == null ? null : cell.getNumericCellValue();
	}

	@Override
	public List<String> loadFactHeader() {
		int headRowIndex = headerRowNums - 1;
		int columns = sheet.getRow(headRowIndex).getLastCellNum();
		List<String> factList = new ArrayList<String>();
		for (int i = 0; i < columns; i++) {
			factList.add(getString(headerRowNums - 1, i));
		}
		return factList;
	}
}
