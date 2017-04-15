package com.zis.shop.dto;

import com.zis.shop.bean.Company;
import com.zis.storage.entity.StorageRepoInfo;

public class CompanyAndStockDto {
	
	private Company company;
	private StorageRepoInfo storageRepoInfo;

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public StorageRepoInfo getStorageRepoInfo() {
		return storageRepoInfo;
	}

	public void setStorageRepoInfo(StorageRepoInfo storageRepoInfo) {
		this.storageRepoInfo = storageRepoInfo;
	}

}
