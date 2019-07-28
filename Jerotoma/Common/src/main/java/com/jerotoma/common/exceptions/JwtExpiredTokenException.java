package com.jerotoma.exceptions;

import org.springframework.security.core.AuthenticationException;

import com.jerotoma.config.auth.tokens.JwtToken;

public class JwtExpiredTokenException extends AuthenticationException {
    private static final long serialVersionUID = -5959543783324224864L;
    
    private JwtToken token;

    public JwtExpiredTokenException(String msg) {
        super(msg);
    }

    public JwtExpiredTokenException(JwtToken token, String msg, Throwable t) {
        super(msg, t);
        this.token = token;
    }
    public JwtExpiredTokenException(String msg, Throwable t) {
        super(msg, t);       
    }

    public String token() {
        return this.token.getToken();
    }
}