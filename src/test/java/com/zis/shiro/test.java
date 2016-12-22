package com.zis.shiro;

import java.util.ArrayList;
import java.util.List;

public class test {
	
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>();
		list.add(new Integer(1));
		list.add(new Integer(2));
		list.add(new Integer(3));
		list.add(new Integer(4));
		list.add(new Integer(5));
		List<Integer> list1 = new ArrayList<Integer>();
		list1.add(new Integer(1));
		list1.add(new Integer(2));
		list1.add(new Integer(3));
		list1.add(new Integer(4));
		list1.add(new Integer(5));
		if (list.containsAll(list1)) {
			System.out.println(1111);
		}
	}

}
