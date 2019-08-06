package com.jerotoma.common.exceptions;

import java.sql.SQLException;

public class DataAccessException extends SQLException {
	
	private String message = "";

	public DataAccessException(String message) {
		this.message = message;
	}
	public DataAccessException(String message, Throwable sqlE) {
		this.message = message;
		sqlE.printStackTrace();
		
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String getMessage(){
		return message;
	}
}
