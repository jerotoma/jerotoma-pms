package com.jerotoma.config;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.jerotoma.config.auth.common.UserContext;
import com.jerotoma.config.auth.interfaces.IAuthenticationFacade;
import com.jerotoma.config.auth.jwt.JwtAuthenticationToken;

@Component
public class AuthenticationFacade implements IAuthenticationFacade{

	@Override
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	@Override
	public UserContext getUserContext(Authentication authentication) {
		
		if(authentication == null) {
			throw new IllegalStateException();	
		}
		Object principal = authentication.getPrincipal();
		
		if( authentication.getPrincipal() instanceof UserContext) {
    		return (UserContext)principal;
    	} else if( principal instanceof User) {
    		User user = (org.springframework.security.core.userdetails.User)principal;
    		return new UserContext(user.getUsername(), user.getAuthorities());
    	} else {
    		throw new BadCredentialsException("Invalid credential was provided");
    	}
	}
	
	@Override
	public UserContext getUserContextByPrincipal(Object principal) {
		
		if( principal instanceof UserContext) {
    		return (UserContext)principal;
    	} else if( principal instanceof User) {
    		User user = (org.springframework.security.core.userdetails.User)principal;
    		return new UserContext(user.getUsername(), user.getAuthorities());
    	} else if( principal instanceof JwtAuthenticationToken) {
    		JwtAuthenticationToken user = (JwtAuthenticationToken)principal;
		return (UserContext)user.getPrincipal();
	} else {
    		throw new BadCredentialsException("Invalid credential was provided");
    	}
	}
}
