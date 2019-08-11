package com.jerotoma.common.exceptions;

import org.springframework.dao.DataAccessException;

public class JDataAccessException extends DataAccessException {
	
	private String message = "";

	public JDataAccessException(String message) {
		super(message);
		this.message = message;
	}
	public JDataAccessException(Throwable sqlE) {
		super("", sqlE);
		sqlE.printStackTrace();
		
	}
	
	public JDataAccessException(String message, Throwable sqlE) {
		super(message, sqlE);
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
