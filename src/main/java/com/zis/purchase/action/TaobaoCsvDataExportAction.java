package com.zis.purchase.action;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.zis.bookinfo.dto.BookInfoAndDetailDTO;
import com.zis.bookinfo.service.BookService;

/**
 * 生成淘宝数据包
 * @author yz
 *
 */
public abstract class TaobaoCsvDataExportAction extends ActionSupport {

	private static final long serialVersionUID = 4950321328483465534L;

	protected String[] emails = {"lvbin0502@126.com", "290479238@qq.com"};
	
	protected BookService bookService;
	protected ThreadPoolTaskExecutor taskExecutor;
	
	public String export() {
		// 查询数据
		final List<BookInfoAndDetailDTO> list = queryBookInfoAndDetails();
		
		// 异步执行
		Thread task = new Thread(new Runnable() {
			public void run() {
				bookService.generateTaobaoCsvDataFile(list, emails);
			}
		});
		taskExecutor.execute(task);
		
		// 预估操作时间
		int seconds = list.size() / 30;
		if(seconds < 2) {
			seconds = 2;
		}
		String msg = String.format("数据正在生成中，可能需要%s分钟，成功后将发送到邮箱%s", seconds, ArrayUtils.toString(emails));
		
		// 返回并提示结果
		this.addActionMessage(msg);
		return SUCCESS;
	}
	
	/**
	 * 查询数据
	 * @return
	 */
	protected abstract List<BookInfoAndDetailDTO> queryBookInfoAndDetails();

	public void setEmails(String[] emails) {
		this.emails = emails;
	}
	
	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}
	
	public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
}
