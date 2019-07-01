package com.jerotoma.services.users;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.jerotoma.common.users.AuthUser;
import com.jerotoma.services.BaseService;

public interface AuthUserService extends UserDetailsService, BaseService<AuthUser> {
	public AuthUser loadUserByUsername(String username) throws UsernameNotFoundException;
}
