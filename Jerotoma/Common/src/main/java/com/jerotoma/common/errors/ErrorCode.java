package com.jerotoma.common.errors;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorCode {
    GLOBAL(2),
    NULL_POINTER_EXCEPTION(4),
    AUTHENTICATION(10), 
    JWT_TOKEN_EXPIRED(11);
    
    private int errorCode;

    private ErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @JsonValue
    public int getErrorCode() {
        return errorCode;
    }
}