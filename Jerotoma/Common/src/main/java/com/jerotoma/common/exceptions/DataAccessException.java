package com.jerotoma.common.exceptions;

import java.sql.SQLException;

public class DataAccessException extends SQLException {
	
	private String message = "";

	public DataAccessException(String message) {
		this.message = message;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String getMessage(){
		return message;
	}
}
