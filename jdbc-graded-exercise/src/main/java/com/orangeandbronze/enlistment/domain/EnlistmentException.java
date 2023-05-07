package com.orangeandbronze.enlistment.domain;

public class EnlistmentException extends RuntimeException {

	public EnlistmentException() {
	}

	public EnlistmentException(String message) {
		super(message);
	}

	public EnlistmentException(Throwable cause) {
		super(cause);
	}

	public EnlistmentException(String message, Throwable cause) {
		super(message, cause);
	}

	public EnlistmentException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
