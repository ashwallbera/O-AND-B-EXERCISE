package com.orangeandbronze.enlistment.domain;

public class DuplicateEnlistmentException extends EnlistmentException {

	public DuplicateEnlistmentException() {
	}

	public DuplicateEnlistmentException(String message) {
		super(message);
	}

	public DuplicateEnlistmentException(Throwable cause) {
		super(cause);
	}

	public DuplicateEnlistmentException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicateEnlistmentException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
