package com.jerotoma.common.exceptions;

public class InvalidJwtTokenException extends RuntimeException {
    public InvalidJwtTokenException(String s) {
		super(s);
	}

	private static final long serialVersionUID = -294671188037098603L;
}