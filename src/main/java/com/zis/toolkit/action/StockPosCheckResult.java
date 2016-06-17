package com.zis.toolkit.action;

import java.util.List;
import java.util.Set;

import com.zis.bookinfo.bean.Bookinfo;

/**
 * 库位核对结果
 * 
 * @author yz
 * 
 */
public class StockPosCheckResult {

	private boolean lastRecordSuccess;

	private String failReason;

	private List<Bookinfo> scannedBookList;

	private Set<String> comparablePos;

	public StockPosCheckResult(boolean lastRecordSuccess, String failReason) {
		this.lastRecordSuccess = lastRecordSuccess;
		this.failReason = failReason;
	}

	public boolean getLastRecordSuccess() {
		return lastRecordSuccess;
	}

	public void setLastRecordSuccess(boolean lastRecordSuccess) {
		this.lastRecordSuccess = lastRecordSuccess;
	}

	public List<Bookinfo> getScannedBookList() {
		return scannedBookList;
	}

	public void setScannedBookList(List<Bookinfo> scannedBookList) {
		this.scannedBookList = scannedBookList;
	}

	public Set<String> getComparablePos() {
		return comparablePos;
	}

	public void setComparablePos(Set<String> comparablePos) {
		this.comparablePos = comparablePos;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
}
