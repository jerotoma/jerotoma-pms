package com.jerotoma.config.auth.common;

public enum Scopes {
	REFRESH_TOKEN;
    
    public String authority() {
        return "ROLE_" + this.name();
    }

}
