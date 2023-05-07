package com.orangeandbronze.enlistment.dao;

public class DataAccessException extends RuntimeException {

	public DataAccessException() {
	}

	public DataAccessException(String message) {
		super(message);
	}

	public DataAccessException(Throwable cause) {
		super(cause);
	}

	public DataAccessException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataAccessException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
