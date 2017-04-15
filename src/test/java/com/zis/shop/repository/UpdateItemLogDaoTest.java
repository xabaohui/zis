package com.zis.shop.repository;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zis.shop.bean.UpdateItemLog;
import com.zis.shop.bean.UpdateItemLog.UpdateItemLogStatus;
import com.zis.shop.util.TestUtil;


public class UpdateItemLogDaoTest extends TestUtil{
	
	@Autowired
	private UpdateItemLogDao dao;
	
	@Test
	public void save(){
		UpdateItemLog log = getLog();
		this.dao.save(log);
		
	}
	
	private UpdateItemLog getLog(){
		UpdateItemLog log = new UpdateItemLog();
		log.setShopId(111);
		log.setBookId(11);
		log.setAmount(3);
		log.setDescription("22222");
		log.setStatus(UpdateItemLogStatus.SUCCESS.getValue());
		log.setMappingId(123);
		log.setUpdateTime(new Date());
		log.setCreateTime(new Date());
		return log;
	}
}
