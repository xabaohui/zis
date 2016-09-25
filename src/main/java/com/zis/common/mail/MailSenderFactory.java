package com.zis.common.mail;


/**
 * 发件箱工厂
 * 
 * @author MZULE
 * 
 */
public class MailSenderFactory {

	/**
	 * 服务邮箱
	 */
	private static SimpleMailSender serviceSms = null;

	/**
	 * 获取邮箱
	 * 
	 * @param type
	 *            邮箱类型
	 * @return 符合类型的邮箱
	 */
	public static SimpleMailSender getSender() {
		if (serviceSms == null) {
			serviceSms = new SimpleMailSender("lvbin0502@126.com", "716startY");
		}
		return serviceSms;
	}
	
	public static void main(String[] args) {
		try {
			getSender().send(new String[] { "lvbin0502@126.com" }, "test", "testshangde");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}