package com.orangeandbronze.enlistment.domain;

public class SectionCreationException extends RuntimeException {

	SectionCreationException() {
	}

	SectionCreationException(String message) {
		super(message);
	}

	public SectionCreationException(Throwable cause) {
		super(cause);
	}

	public SectionCreationException(String message, Throwable cause) {
		super(message, cause);
	}

	public SectionCreationException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
