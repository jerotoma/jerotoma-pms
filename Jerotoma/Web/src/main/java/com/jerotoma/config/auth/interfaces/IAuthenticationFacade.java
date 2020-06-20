package com.jerotoma.config.auth.interfaces;

import org.springframework.security.core.Authentication;

import com.jerotoma.common.models.users.UserContext;

public interface IAuthenticationFacade {
	Authentication getAuthentication();
	 
	UserContext getUserContext(Authentication authentication);

	UserContext getUserContextByPrincipal(Object principal);
}
