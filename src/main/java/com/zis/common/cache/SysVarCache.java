package com.zis.common.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zis.requirement.bean.SysVar;
import com.zis.requirement.dao.SysVarDao;

/**
 * 系统变量缓存
 * 
 * @author yz
 * 
 */
public class SysVarCache {

	private Map<String, Integer> cache;

	private SysVarDao sysVarDao;

	public SysVarCache() {
	}

	/**
	 * 初始化系统变量
	 */
	private void initSystemVar() {
		cache = new HashMap<String, Integer>();
		// init all: db->default
		for (SysVarConstant var : SysVarConstant.values()) {
			// 加载系统变量
			SysVar sysvar = loadSysVarFromDB(var.getKeyName());
			if (sysvar == null) {
				// 如果数据库没有，保存默认值0到数据库
				sysvar = new SysVar();
				sysvar.setDepKey(var.getKeyName());
				sysvar.setDepValue(0);
				this.sysVarDao.save(sysvar);
			}
			cache.put(var.getKeyName(), sysvar.getDepValue());
		}
	}

	/**
	 * 查询系统变量
	 * 
	 * @param key
	 * @return
	 */
	public Integer getSystemVar(String key) {
		if(cache == null) {
			initSystemVar();
		}
		// 从cache中读取，如果没有，则从数据库中查询
		Integer value = cache.get(key);
		if (value == null) {
			SysVar dbVar = loadSysVarFromDB(key);
			if (dbVar != null) {
				value = dbVar.getDepValue();
				cache.put(key, dbVar.getDepValue());
			}
		}
		return value;
	}

	/**
	 * 更新系统变量
	 * 
	 * @param key
	 * @param value
	 */
	public void updateSysVar(String key, Integer value) {
		if(cache == null) {
			initSystemVar();
		}
		// update db->cache
		SysVar var = loadSysVarFromDB(key);
		if (var == null) {
			var = new SysVar();
			var.setDepKey(key);
		}
		var.setDepValue(value);
		this.sysVarDao.update(var);
		cache.put(key, value);
	}

	// 从数据库加载数据
	private SysVar loadSysVarFromDB(String key) {
		@SuppressWarnings("unchecked")
		List<SysVar> list = this.sysVarDao.findByDepKey(key);
		if (list == null || list.isEmpty()) {
			return null;
		}
		if (list.size() > 1) {
			throw new RuntimeException("重复的系统变量，key=" + key);
		}
		return list.get(0);
	}

	public void setSysVarDao(SysVarDao sysVarDao) {
		this.sysVarDao = sysVarDao;
	}

	public List<SysVar> getAllSysVars() {
		if(cache == null) {
			initSystemVar();
		}
		List<SysVar> list = new ArrayList<SysVar>();
		for (String key : cache.keySet()) {
			SysVar e = new SysVar();
			e.setDepKey(key);
			e.setDepValue(cache.get(key));
			list.add(e);
		}
		return list;
	}
}
