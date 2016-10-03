package com.zis.purchase.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.zis.bookinfo.dto.BookInfoAndDetailDTO;
import com.zis.bookinfo.service.BookService;

/**
 * 生成淘宝数据包
 * @author yz
 *
 */
public abstract class TaobaoCsvDataExportController {
	
	protected String[] emails = {"to_shaowei@163.com", "sw119088324@163.com"};
	@Autowired
	protected BookService bookService;
	@Autowired
	protected ThreadPoolTaskExecutor taskExecutor;
	
	public String export(HttpServletRequest request) {
		// 查询数据
		final List<BookInfoAndDetailDTO> list = queryBookInfoAndDetails(request);
		// 异步执行
		Thread task = new Thread(new Runnable() {
			public void run() {
				bookService.generateTaobaoCsvDataFile(list, emails);
			}
		});
		taskExecutor.execute(task);
		
		// 预估操作时间
		int seconds = list.size() / 60;
		if(seconds < 2) {
			seconds = 2;
		}
		String msg = String.format("数据正在生成中，可能需要%s分钟，成功后将发送到邮箱%s", seconds, ArrayUtils.toString(emails));
		System.out.println(msg);
		// 返回并提示结果
		//TODO 验证框架
//		this.addActionMessage(msg);
		return getSuccessPage();
	}
	protected abstract String getSuccessPage();
	
	/**
	 * 查询数据
	 * @return
	 */
	protected abstract List<BookInfoAndDetailDTO> queryBookInfoAndDetails(HttpServletRequest request);

}