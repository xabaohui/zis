package com.zis.bookinfo.service;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zis.bookinfo.dto.BookInfoSearchResult;

@Service
public class BookServiceDWR {

	@Autowired
	private BookService bookService;

	@RequiresPermissions(value = { "bookInfo:view", "bookInfo:saveOrUpdate" }, logical = Logical.OR)
	public BookInfoSearchResult findAndCaptureBookByISBN(String isbn) {
		return this.bookService.findAndCaptureBookByISBN(isbn);
	}

	@RequiresPermissions(value = "requirement:books:input")
	public String getBookInfo(String bookName, String bookAuthor, String ISBN) {
		return this.bookService.getBookInfo(bookName, bookAuthor, ISBN);
	}

	@RequiresPermissions(value = "bookInfo:saveOrUpdate")
	public void updateSameBooksToOneGroup(Integer ids[]) {
		this.bookService.updateSameBooksToOneGroup(ids);
	}

	@RequiresPermissions(value = "bookInfo:saveOrUpdate")
	public void updateRelatedBooksToOneGroup(Integer ids[]) {
		this.bookService.updateRelatedBooksToOneGroup(ids);
	}

	@RequiresPermissions(value = "bookInfo:delete")
	public void updateBookForBatchDelete(Integer ids[]) {
		this.bookService.updateBookForBatchDelete(ids);
	}
}
