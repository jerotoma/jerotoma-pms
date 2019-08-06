package com.jerotoma.common.exceptions;

public class FieldCanNotBeEmptyException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FieldCanNotBeEmptyException(String message) {
		super(message);
	}

}
