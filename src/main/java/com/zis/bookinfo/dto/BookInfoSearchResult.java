package com.zis.bookinfo.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.bean.BookinfoDetail;
import com.zis.bookinfo.util.BookMetadata;

/**
 * 图书信息查询结果
 * <p/>
 * 记录系统中存在的记录或者网络采集的数据
 * 
 * @author yz
 * 
 */
public class BookInfoSearchResult {
	private boolean isSysData; // 是否是系统采集数据
	private List<BookInfoAndDetailDTO> booksExist = new ArrayList<BookInfoAndDetailDTO>();
	private BookMetadata bookCaptured;

	public boolean isSysData() {
		return isSysData;
	}

	public void setSysData(boolean isSysData) {
		this.isSysData = isSysData;
	}

	public List<BookInfoAndDetailDTO> getBooksExist() {
		return booksExist;
	}

	public BookMetadata getBookCaptured() {
		return bookCaptured;
	}

	public void setBookCaptured(BookMetadata bookCaptured) {
		this.bookCaptured = bookCaptured;
	}

	public void addBookExist(Bookinfo book, BookinfoDetail detail) {
		BookInfoAndDetailDTO record = new BookInfoAndDetailDTO();
		BeanUtils.copyProperties(book, record);
		if(detail != null) {
			BeanUtils.copyProperties(detail, record);
		}
		this.booksExist.add(record);
	}
}
