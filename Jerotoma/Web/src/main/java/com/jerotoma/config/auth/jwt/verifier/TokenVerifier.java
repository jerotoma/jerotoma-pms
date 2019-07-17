package com.jerotoma.config.auth.jwt.verifier;

public interface TokenVerifier {
    public boolean verify(String jti);
}