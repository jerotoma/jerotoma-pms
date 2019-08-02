package com.jerotoma.database.dao.users;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.jerotoma.common.models.users.AuthUser;
import com.jerotoma.database.dao.BaseDao;

public interface AuthUserDao extends BaseDao<AuthUser>{
	public AuthUser loadUserByUsername(String username) throws UsernameNotFoundException;
}
