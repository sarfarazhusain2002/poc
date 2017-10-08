package com.oxy.s3m.notification.exception;

public class SellerException extends Exception {

	private static final long serialVersionUID = 1L;

	public SellerException(String message) {
		super(message);
	}

	public SellerException(Throwable t) {
		super(t);
	}

	public SellerException(String message, Throwable t) {
		super(message, t);
	}
}
