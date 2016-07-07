package com.zis.common.capture;

public class BookMetadataCaptureException extends RuntimeException{

	private static final long serialVersionUID = -5861479152859725352L;

	public BookMetadataCaptureException() {
		super();
	}
	
	public BookMetadataCaptureException(String msg) {
		super(msg);
	}

	public BookMetadataCaptureException(Throwable cause) {
		super(cause);
	}

	public BookMetadataCaptureException(String message, Throwable cause) {
		super(message, cause);
	}

}
