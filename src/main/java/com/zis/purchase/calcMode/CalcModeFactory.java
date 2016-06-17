package com.zis.purchase.calcMode;

import java.util.Map;

public class CalcModeFactory {
	
	private static Map<Integer, CalculateModeInterface> map;

	public static CalculateModeInterface getInstance(Integer mode) {
		return map.get(mode);
	}

	public Map<Integer, CalculateModeInterface> getMap() {
		return map;
	}

	public void setMap(Map<Integer, CalculateModeInterface> map) {
		this.map = map;
	}
	
}
