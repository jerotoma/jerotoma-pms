package com.jerotoma.config.auth.listeners;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component("authenticationFailureListener")
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
	   	
	    @Override
	    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent e) {
	        System.out.println(e);
	    }
}