package com.orangeandbronze.enlistment.domain;

public class RoomCapacityReachedException extends EnlistmentException {

	public RoomCapacityReachedException() {
	}

	public RoomCapacityReachedException(String message) {
		super(message);
	}

	public RoomCapacityReachedException(Throwable cause) {
		super(cause);
	}

	public RoomCapacityReachedException(String message, Throwable cause) {
		super(message, cause);
	}

	public RoomCapacityReachedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
