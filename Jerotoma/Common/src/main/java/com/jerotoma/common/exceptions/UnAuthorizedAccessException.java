package com.jerotoma.common.exceptions;

public class UnAuthorizedAccessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnAuthorizedAccessException(String msg) {
		super(msg);		
	}

}
