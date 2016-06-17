package com.zis.purchase.dto;

import java.util.Date;
import java.util.List;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.purchase.bean.TempImportDetail;

/**
 * 临时导入表匹配详情，用于页面展示
 * 
 * @author yz
 * 
 */
public class TempImportDetailView extends TempImportDetail{

	private Bookinfo associateBook;// 对应图书
	private List<Bookinfo> relatedBooks;// 关联图书

	public Bookinfo getAssociateBook() {
		return associateBook;
	}

	public void setAssociateBook(Bookinfo associateBook) {
		this.associateBook = associateBook;
	}

	public List<Bookinfo> getRelatedBooks() {
		return relatedBooks;
	}

	public void setRelatedBooks(List<Bookinfo> relatedBooks) {
		this.relatedBooks = relatedBooks;
	}
}
