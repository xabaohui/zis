package com.test.jishibao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.zis.trade.dto.OrderInfoDTO.SkuInfo;

public class test {
	public static void main(String[] args) {
		test t = new test();
		List<SkuInfo> list = t.getList();
		System.out.println(list);
		List<SkuInfo> alist = t.userList(list);
		System.out.println(JSON.toJSON(alist));
		System.out.println(list.get(0).getItemCount());
	}
	
//	private Set<Integer> getSet(){
//		Set<Integer> set = new HashSet<>();
//		set.add(1);
//		set.add(1);
//		set.add(1);
//		set.add(1);
//		set.add(1);
//		set.add(1);
//		set.add(1);
//		set.add(1);
//		set.add(1);
//		set.add(1);
//		return set;
//	}

	private List<SkuInfo> getList() {
		List<SkuInfo> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			SkuInfo s = new SkuInfo();
			s.setSkuId(i);
			list.add(s);
		}
		return list;
	}

	private List<SkuInfo> userList(List<SkuInfo> list) {
		setList(list);
		return list;
	}

	private void setList(List<SkuInfo> list) {
		Set<Integer> set = new HashSet<>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getSkuId() == 5) {
				set.add(i);
			}
			list.get(i).setItemCount(i);
		}
		for (Integer i : set) {
			list.remove(i);
		}
	}

}