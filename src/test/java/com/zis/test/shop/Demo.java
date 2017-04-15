package com.zis.test.shop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zis.shop.api.ApiTransfer;
import com.zis.shop.api.impl.ApiTransferFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:spring/spring.xml", "classpath:spring/shiro.xml" })
// @TransactionConfiguration(defaultRollback=false)
public class Demo {

	@Autowired
	private ApiTransferFactory api;
	
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	@Test
	public void test(){
		final ApiTransfer apiTaobao = this.api.getInstance("taobao");
		Thread task = new Thread(new Runnable() {
			public void run() {
					try {
						apiTaobao.addItem(null, null);
					} catch (Exception e) {
					}
				}
		});
		taskExecutor.execute(task);
	}
}
