package com.zis.toolkit.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.service.BookService;
import com.zis.bookinfo.util.YouLuNetTextUtil;

/**
 * 图书信息修复工具
 * 
 * @author yz
 * 
 */
public class BookInfoToolkit {

	private BookService bookService;

	private Logger logger = Logger.getLogger(BookInfoToolkit.class);

	/**
	 * 批量更新书名
	 * <p/>
	 * 如果书名同时包含startLabel和keyword，则保留startLabel之前的部分作为新的书名，其余部分全部删除
	 * 
	 * @param startLabel
	 *            起始字符
	 * @param keyword
	 *            关键字
	 * @return 返回处理的记录数量
	 */
	public List<String> updateFixBookName(String startLabel, String keyword) {
		if (StringUtils.isBlank(startLabel) || StringUtils.isBlank(keyword)) {
			String errMsg = String.format(
					"illegal arguments, for input startLabel=%s, keyword=%s",
					startLabel, keyword);
			throw new RuntimeException(errMsg);
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(Bookinfo.class);
		criteria.add(Restrictions.like("bookName", "%" + startLabel + "%"
				+ keyword + "%"));
		List<Bookinfo> list = this.bookService.findBookByCriteria(criteria);
		List<String> result = new ArrayList<String>();
		for (Bookinfo book : list) {
			String origName = book.getBookName();
			String newName = origName
					.substring(0, origName.lastIndexOf(startLabel));
			book.setBookName(newName);
			this.bookService.updateBook(book);
			String infoMsg = String.format(
					"update bookName %s to %s where id=%s", origName, newName,
					book.getId());
			logger.info(infoMsg);
			result.add(infoMsg);
		}
		return result;
	}

	/**
	 * 批量更新书名
	 * <p/>
	 * 
	 * @param orig
	 *            起始字符
	 * @param replace
	 *            关键字
	 * @return 返回处理的记录数量
	 */
	public List<String> updateBatchReplaceBookName(String orig, String replace) {
		if (StringUtils.isBlank(orig)) {
			String errMsg = String.format(
					"illegal arguments, for input orig=%s", orig);
			throw new RuntimeException(errMsg);
		}
		if(StringUtils.isBlank(replace)) {
			replace = "";
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(Bookinfo.class);
		criteria.add(Restrictions.like("bookName", "%" + orig + "%"));
		List<Bookinfo> list = this.bookService.findBookByCriteria(criteria);
		List<String> result = new ArrayList<String>();
		for (Bookinfo book : list) {
			String origName = book.getBookName();
			String newName = origName.replace(orig, replace);
			book.setBookName(newName);
			this.bookService.updateBook(book);
			String infoMsg = String.format(
					"update bookName %s to %s where id=%s", origName, newName,
					book.getId());
			logger.info(infoMsg);
			result.add(infoMsg);
		}
		return result;
	}

	/**
	 * 删除作者中的无用后缀，例如著、编等
	 * 
	 * @param suffix
	 * @return
	 */
	public List<String> deleteUselessSuffixInBookAuthor(String suffix) {
		if (StringUtils.isBlank(suffix)) {
			throw new RuntimeException("illegal arguments, for input suffix="
					+ suffix);
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(Bookinfo.class);
		criteria.add(Restrictions.like("bookAuthor", "%" + suffix));
		List<Bookinfo> list = this.bookService.findBookByCriteria(criteria);
		List<String> result = new ArrayList<String>();
		for (Bookinfo book : list) {
			String orig = book.getBookAuthor();
			String newStr = orig.substring(0, orig.lastIndexOf(suffix));
			book.setBookAuthor(newStr);
			this.bookService.updateBook(book);
			String infoMsg = String.format(
					"update bookAuthor %s to %s where id=%s", orig, newStr,
					book.getId());
			logger.info(infoMsg);
			result.add(infoMsg);
		}
		return result;
	}

	/**
	 * 批量修复版次：删除书名中“修订版”、“修订本”，并追加到版次最后
	 * <p/>
	 * 例如：书名：儿童早期教育修订版；版次：第一版 ==> 书名：儿童早期教育；版次：第一版修订版
	 * 
	 * @param keyword
	 * @return
	 */
	public List<String> updateFixEditionByBookName(String keyword) {
		if (StringUtils.isBlank(keyword)) {
			throw new RuntimeException("illegal arguments, for input keyword="
					+ keyword);
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(Bookinfo.class);
		criteria.add(Restrictions.like("bookName", "%" + keyword + "%"));
		List<Bookinfo> list = this.bookService.findBookByCriteria(criteria);
		List<String> result = new ArrayList<String>();
		for (Bookinfo book : list) {
			String orig = book.getBookName();
			String origEdition = book.getBookEdition();
			String newName = orig.replace(keyword, "");
			newName = newName.replace("()", "");
			newName = newName.replace("( )", "");
			newName = newName.replace("（）", "");
			newName = newName.replace("（ ）", "");
			String newEdition = origEdition + keyword;
			book.setBookName(newName);
			book.setBookEdition(newEdition);
			this.bookService.updateBook(book);
			String infoMsg = String.format(
					"update book %s,%s to %s,%s where id=%s", orig,
					origEdition, newName, newEdition, book.getId());
			logger.info(infoMsg);
			result.add(infoMsg);
		}
		return result;
	}

	/**
	 * 批量修复版次：使用书名中的“第X版”代替版次的值
	 * 
	 * @return
	 */
	public List<String> updateReplaceEditionByBookName() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Bookinfo.class);
		criteria.add(Restrictions.like("bookName", "%第%版%"));
		List<Bookinfo> list = this.bookService.findBookByCriteria(criteria);
		List<String> result = new ArrayList<String>();
		for (Bookinfo book : list) {
			String orig = book.getBookName();
			String origEdition = book.getBookEdition();
			String newEdition = null;
			try {
				newEdition = orig.substring(orig.lastIndexOf("第"),
						orig.lastIndexOf("版") + 1);
			} catch (Exception e) {
				logger.error("修复版次失败, bookId=" + book.getId(), e);
				continue;
			}
			String newName = orig.replace(newEdition, "");
			newName = newName.replace("()", "");
			newName = newName.replace("( )", "");
			newName = newName.replace("（）", "");
			newName = newName.replace("（ ）", "");
			book.setBookName(newName);
			book.setBookEdition(newEdition);
			this.bookService.updateBook(book);
			String infoMsg = String.format(
					"update book %s,%s to %s,%s where id=%s", orig,
					origEdition, newName, newEdition, book.getId());
			logger.info(infoMsg);
			result.add(infoMsg);
		}
		return result;
	}
	
	/**
	 * 整理作者中的错误内容
	 * @param idList
	 * @return
	 */
	public List<String> updateFixBookAuthor(List<Bookinfo> bookList) {
		if(bookList == null || bookList.isEmpty()) {
			return new ArrayList<String>();
		}
		List<String> result = new ArrayList<String>();
		for (Bookinfo book : bookList) {
			String origAuthor = book.getBookAuthor();
			try {
				String destAuthor = YouLuNetTextUtil.clearBookAuthor(origAuthor);
				if(origAuthor.equals(destAuthor)) {
					continue; // 如果处理后的作者名称没有变化，则跳过
				}
				book.setBookAuthor(destAuthor);
				this.bookService.updateBook(book);
				String infoMsg = String.format(
						"update bookAuthor %s to %s where id=%s", origAuthor,
						destAuthor, book.getId());
				logger.info(infoMsg);
				result.add(infoMsg);
			} catch (Exception e) {
				logger.error("更新数据错误：" + e.getMessage(), e);
			}
		}
		return result;
	}

	public BookService getBookService() {
		return bookService;
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}
}
