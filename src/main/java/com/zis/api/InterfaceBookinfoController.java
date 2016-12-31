/**
 * 
 */
package com.zis.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.zis.api.response.BaseApiResponse;
import com.zis.api.response.BookInfoQueryData;
import com.zis.api.response.BookInfoQueryResponse;
import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.service.BookService;
import com.zis.bookinfo.util.YouLuNetDetailCapture;

/**
 * @author lvbin 2015-10-7
 */
@SuppressWarnings("deprecation")
@Controller
@RequestMapping(value = "/api")
public class InterfaceBookinfoController {

	// private String isbn;
	@Autowired
	private BookService bookService;
	private YouLuNetDetailCapture youLuNetDetailCapture;
	private static Logger logger = Logger.getLogger(InterfaceBookinfoController.class);
	
	/**
	 * 按照isbn查找图书信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/queryBookInfo", produces = "text/plain;charset=utf-8")
	public String queryBookInfo(String isbn, HttpServletResponse resp) {
		logger.info("api.InterfaceBookinfoAction.queryBookInfo.request--" + "isbn=" + isbn);
		BaseApiResponse response = new BaseApiResponse();

		// 参数检验
		if (StringUtils.isBlank(isbn)) {
			response.setCode(BaseApiResponse.CODE_ILLEGAL_ARGUMENT);
			response.setMsg("ISNB不能为空!");
			renderResult(response, resp);
			return "indexApi";
		}
		try {
			// 查询数据
			List<Bookinfo> booklist = bookService.findBookByISBN(isbn);
			if (booklist.isEmpty()) {
				try {
					Bookinfo book = getFromYouLuNet(isbn);
					if (book != null) {
						booklist.add(book);
					}
				} catch (Exception e) {
					logger.error("有路网查询失败", e);
					BookInfoQueryResponse responseBookinfo = new BookInfoQueryResponse();
					responseBookinfo.setCode(BaseApiResponse.CODE_SUCCESS);
					renderResult(responseBookinfo, resp);
					return "indexApi";
				}
			}

			// 复制list
			List<BookInfoQueryData> resultList = new ArrayList<BookInfoQueryData>();
			for (Bookinfo bi : booklist) {
				BookInfoQueryData entry = new BookInfoQueryData();
				BeanUtils.copyProperties(bi, entry);
				entry.setBookId(bi.getId());
				resultList.add(entry);
			}

			BookInfoQueryResponse responseBookinfo = new BookInfoQueryResponse();
			responseBookinfo.setCode(BookInfoQueryResponse.CODE_SUCCESS);
			responseBookinfo.setResultList(resultList);
			renderResult(responseBookinfo, resp);
			logger.info("api.InterfaceBookinfoAction.queryBookInfo.response successful..");
			return "indexApi";
		} catch (BeansException e) {
			logger.error("api invoke failed!", e);
			BookInfoQueryResponse responseBookinfo = new BookInfoQueryResponse();
			responseBookinfo.setCode(BookInfoQueryResponse.CODE_INNER_ERROR);
			responseBookinfo.setMsg(e.getMessage());
			renderResult(responseBookinfo, resp);
			logger.info("api.InterfaceBookinfoAction.queryBookInfo.response failed!");
			return "indexApi";
		}
	}

	/**
	 * 从有路网获取数据
	 * 
	 * @return
	 */
	private Bookinfo getFromYouLuNet(String isbn) {
		logger.info("api.InterfaceBookinfoAction.queryBookInfo.request--get from youlu.net, isbn=" + isbn);
		int bookid = youLuNetDetailCapture.getBookIdByIsbn(isbn);
		if (bookid <= 0) {
			throw new RuntimeException("有路网解析数据错误，bookId必须大于0，isbn=" + isbn);
		}
		Bookinfo book = bookService.saveBookinfoByCaptureFromYouluNet(bookid);
		return book;
	}

	/**
	 * 渲染结果
	 * 
	 * @param content
	 */
	private void renderResult(Object obj, HttpServletResponse resp) {
		// json序列化
		String content = JSON.toJSONString(obj);
		//
		// HttpServletResponse resp = ((ServletWebRequest)
		// RequestContextHolder.getRequestAttributes()).getResponse();
		// ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		resp.setContentType("text/html;charset=utf-8");
		try {
			// PrintWriter out = ServletActionContext.getResponse().getWriter();
			PrintWriter out = resp.getWriter();
			out.print(content);
			out.flush();
			out.close();
		} catch (IOException e) {
			logger.error("序列化过程失败", e);
		}
	}
}