package com.test.jishibao;

import java.util.ArrayList;
import java.util.List;

import com.zis.trade.dto.OrderInfoDTO.SkuInfo;

public class test {
	public static void main(String[] args) {
		test t = new test();
		List<SkuInfo> list = t.getList();
		System.out.println(list);
		t.setList(list);
		System.out.println(list.get(0).getItemCount());
	}

	private List<SkuInfo> getList() {
		List<SkuInfo> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			SkuInfo s = new SkuInfo();
			s.setSkuId(i);
			list.add(s);
		}
		return list;
	}

	private void setList(List<SkuInfo> list) {
		for (SkuInfo s : list) {
			s.setItemCount(10);
		}
	}
}