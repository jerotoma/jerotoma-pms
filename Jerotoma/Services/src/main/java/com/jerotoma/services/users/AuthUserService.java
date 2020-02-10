package com.jerotoma.services.users;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.RoleConstant;
import com.jerotoma.common.models.users.AuthUser;
import com.jerotoma.services.BaseService;

public interface AuthUserService extends UserDetailsService, BaseService<AuthUser> {
	public AuthUser loadUserByUsername(String username) throws UsernameNotFoundException;

	public List<AuthUser> search(QueryParam queryParam) throws SQLException;
	
	public Long countObject() throws SQLException;

	public AuthUser preUserCreation(Map<String, Object> params, RoleConstant.USER_ROLES userRole);
}
