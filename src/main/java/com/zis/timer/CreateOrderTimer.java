package com.zis.timer;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.zis.common.util.ZisUtils;
import com.zis.shop.bean.ShopInfo;
import com.zis.shop.service.ShopService;

public class CreateOrderTimer extends CommntOrderTimer {

	private ShopService doShopService;

	private static Logger logger = Logger.getLogger(CreateOrderTimer.class);

	private static final String HAS_ORDER = "订单已存在";

	public void createOrderForNet() {
		Date endTime = new Date();
		Date startTime = new Date(endTime.getTime() - 600000);
		logger.info("查询订单开始时间" + ZisUtils.getDateString("yyyy-MM-dd HH:mm:ss", startTime));
		logger.info("查询订单结束时间" + ZisUtils.getDateString("yyyy-MM-dd HH:mm:ss", endTime));
		List<ShopInfo> shopList = this.doShopService.queryAllShop();
		for (ShopInfo shop : shopList) {
			try {
				this.doShopService.createOrderForShopIdAndDate(shop.getShopId(), startTime, endTime);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				if (!HAS_ORDER.equals(e.getMessage())) {
					String[] email = { shop.getEmails() };
					String msg = String.format("%s %s %s %s \n %s %s", "开始时间",
							ZisUtils.getDateString("yyyy-MM-dd HH:mm:ss", startTime), "结束时间",
							ZisUtils.getDateString("yyyy-MM-dd HH:mm:ss", endTime), "错误原因", e.getMessage());
					sendFailEmail(email, shop.getShopName() + " 同步订单失败", msg, shop);
				}
			}
		}
	}

	public ShopService getDoShopService() {
		return doShopService;
	}

	public void setDoShopService(ShopService doShopService) {
		this.doShopService = doShopService;
	}
}
