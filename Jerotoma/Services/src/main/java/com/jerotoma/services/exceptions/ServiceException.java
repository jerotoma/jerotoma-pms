package com.jerotoma.services.exceptions;

import javax.persistence.PersistenceException;

public class ServiceException extends PersistenceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	public ServiceException(String message, Throwable t) {
		super(message, t);
	}
	
	public ServiceException(Throwable t) {
		super(t);
	}
	
	public ServiceException(String message) {
		super(message);
	}
	

}
