package com.zis.timer;

import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;

import com.zis.common.mail.MailSenderFactory;
import com.zis.common.mail.SimpleMailSender;
import com.zis.common.util.ZisUtils;
import com.zis.shop.bean.ShopInfo;

public abstract class CommntOrderTimer {
	
	private final String shaoweiEmail[] = { "to_shaowei@163.com" };
	
	private SimpleMailSender mailSender = MailSenderFactory.getSender();

	private static Logger logger = Logger.getLogger(CommntOrderTimer.class);
	/**
	 * 发送错误邮件
	 * 
	 * @param mail
	 * @param msg
	 * @param shop
	 */
	protected void sendFailEmail(String[] mail,String title, String msg, ShopInfo shop) {
		try {
			mailSender.send(mail, title + ZisUtils.getDateString("yyyy年MM月dd天HH时mm分"), msg);
		} catch (AddressException e) {
			logger.error(e.getMessage(), e);
			try {
				mailSender.send(shaoweiEmail,
						shop.getShopId() + title + ZisUtils.getDateString("yyyy年MM月dd天HH时mm分"), msg);
			} catch (Exception e2) {
				logger.error(e2.getMessage(), e2);
			}
		} catch (Exception e1) {
			logger.error(e1.getMessage(), e1);
		}
	}
}
