package com.zis.shop.dto;

import java.io.InputStream;

public class ShopDownloadJiShiBaoDto implements ShopDownloadInterfaceDto {
	
	private InputStream inputStream;

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}
