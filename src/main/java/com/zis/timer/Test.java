package com.zis.timer;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.taobao.api.internal.util.StringUtils;
import com.zis.common.util.ZisUtils;

public class Test {
	
	public void myTest(){
		System.out.println(new Date());
		
		System.out.println(123123123);
	}
	
	public static void main(String[] args) {
		Date endTime = new Date();
//		Date endTime = new Date(now.getTime()-30000);
		Date startTime = new Date(endTime.getTime()-600000);
//		System.out.println(now);
		System.out.println(startTime);
		System.out.println(endTime);
//		date
//		System.out.println(StringUtils.parseDateTime("2016-06-20 00:00:00"));
	}
}
