package com.zis.purchase.action;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.service.BookService;
import com.zis.common.actiontemplate.CommonExcelExportAction;
import com.zis.common.util.ZisUtils;
import com.zis.purchase.bean.InwarehouseDetail;
import com.zis.purchase.biz.DoPurchaseService;

/**
 * 导出网渠宝商品资料文件--根据扫描入库数据导出
 * 
 * @author yz
 * 
 */
public class WangqubaoItemExportByInwarehouseAction extends
		CommonExcelExportAction<InwarehouseDetail> {

	private Set<String> uniqueIsbnDealt = new HashSet<String>();// 已处理的记录，导出的时候判断重复项用
	private Integer[] batchSelectedItem;

	private BookService bookService;
	private DoPurchaseService doPurchaseService;

	@Override
	protected String[] getTableHeaders() {
		return new String[] { "序号", "商品名称", "商品货号", "商品条形码", "仓库名称", "供应商",
				"供应商货号", "商品备案货号", "品牌", "商品属性", "大类名称", "小类名称", "单位", "颜色名称",
				"尺码名称", "单价", "条码", "亚马逊编码", "SKU条形码", "SKU备案货号", "重量", "颜色代码",
				"尺码代码" };
	}

	@Override
	protected boolean isSkip(InwarehouseDetail record) {
		Bookinfo book = this.bookService.findBookById(record.getBookId());
		if(book == null) {
			throw new RuntimeException("没有找到对应的图书："+record.getBookId());
		}
		String uniqueIsbn = getUniqueIsbn(book);
		// 如果已经处理过，则跳过
		if (uniqueIsbnDealt.contains(uniqueIsbn)) {
			return true;
		} else {
			uniqueIsbnDealt.add(uniqueIsbn);
			return false;
		}
	}

	/**
	 * 生成唯一标识
	 * 
	 * @param book
	 * @return
	 */
	private String getUniqueIsbn(Bookinfo book) {
		// 一码多书的，采用"条形码+bookId"作为唯一标识，正常的图书直接使用条形码
		return book.getRepeatIsbn() ? book.getIsbn() + "-" + book.getId()
				: book.getIsbn();
	}

	@Override
	protected String[] getRowDatas(InwarehouseDetail record) {
		String[] rowDatas = new String[this.getTableHeaders().length];
		Bookinfo book = this.bookService.findBookById(record.getBookId());
		String fmt = "%s (%s) %s %s";
		String itemTitle = String.format(fmt, book.getBookName(),
				book.getBookEdition(), book.getBookAuthor(), book.getIsbn());
		// 一码多书的，采用"条形码+bookId"作为唯一标识，正常的图书直接使用条形码
		String uniqueIsbn = getUniqueIsbn(book);
		rowDatas[1] = itemTitle;// 商品名称
		rowDatas[2] = uniqueIsbn;// 商品货号，必须是唯一标识
		rowDatas[3] = book.getIsbn();// 商品条形码，网渠宝规定必须是数字，可以重复
		rowDatas[8] = "二手教材";
		rowDatas[9] = "二手书";
		rowDatas[10] = "图书";
		rowDatas[11] = "二手书";
		rowDatas[15] = book.getBookPrice() + "";
		rowDatas[16] = uniqueIsbn;// 条码，更新库存用，与商品货号保持一致
		return rowDatas;
	}

	@Override
	protected List<InwarehouseDetail> queryExportData() {
		DetachedCriteria criteria = DetachedCriteria.forClass(InwarehouseDetail.class);
		criteria.add(Restrictions.in("inwarehouseId", batchSelectedItem));
		return this.doPurchaseService.findInwarehouseDetailByCriteria(criteria);
	}
	
	@Override
	protected String setExportFileName() {
		return "网渠宝商品资料-" + ZisUtils.getDateString("yyyy-MM-dd") + ".xls";
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}

	public void setDoPurchaseService(DoPurchaseService doPurchaseService) {
		this.doPurchaseService = doPurchaseService;
	}

	public Integer[] getBatchSelectedItem() {
		return batchSelectedItem;
	}

	public void setBatchSelectedItem(Integer[] batchSelectedItem) {
		this.batchSelectedItem = batchSelectedItem;
	}
}
