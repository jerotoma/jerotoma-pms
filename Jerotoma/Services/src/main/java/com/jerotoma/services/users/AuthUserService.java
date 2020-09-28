package com.jerotoma.services.users;

import java.sql.SQLException;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.users.User;
import com.jerotoma.services.BaseService;

public interface AuthUserService extends UserDetailsService, BaseService<User> {
	public User loadUserByUsername(String username) throws UsernameNotFoundException;

	public List<User> search(QueryParam queryParam) throws SQLException;
	
	public Long countObject() throws SQLException;
	
	public boolean doesUserExist(String username);

	public User createUserLoginAccount(User authUser);	
}
