package com.jerotoma.common.exceptions;

import org.springframework.security.authentication.AuthenticationServiceException;

public class AuthMethodNotSupportedException extends AuthenticationServiceException {
    private static final long serialVersionUID = 3705043083010304496L;

    public AuthMethodNotSupportedException(String msg) {
        super(msg);
    }
    
    public AuthMethodNotSupportedException(String msg, Throwable t) {
        super(msg, t);
    }
}