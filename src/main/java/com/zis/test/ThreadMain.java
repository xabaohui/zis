package com.zis.test;

import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class ThreadMain {

	public static void main(String[] args) {
		ThreadPoolTaskExecutor exe = new ThreadPoolTaskExecutor();
		exe.setCorePoolSize(5);
		exe.setKeepAliveSeconds(100);
		exe.setMaxPoolSize(10);
		exe.setQueueCapacity(20);
		exe.initialize();

		for (int i = 1; i < 200; i++) {
			final int num = i;
			Runnable r = new Runnable() {

				public void run() {
					System.out.println("start thread=" + num);
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			while (true) {
				try {
					exe.execute(r);
					break;
				} catch (TaskRejectedException e) {
					System.out.println("reject, wait several seconds...");
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
	}
}
