package com.jerotoma.config.auth.listeners;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {
   
    @Override
    public void onApplicationEvent(final AuthenticationSuccessEvent e) {
      	System.out.println("Login Event generated by "+ this.getClass().getSimpleName());
        
    }

}