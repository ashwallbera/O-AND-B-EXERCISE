package com.orangeandbronze.enlistment.dao;

public class StaleDataException extends DataAccessException {

	public StaleDataException(String message) {
		super(message);
	}
}
