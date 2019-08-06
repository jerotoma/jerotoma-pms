package com.jerotoma.database.dao.users;

import java.sql.SQLException;
import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.users.AuthUser;
import com.jerotoma.database.dao.BaseDao;

public interface AuthUserDao extends BaseDao<AuthUser>{
	public AuthUser loadUserByUsername(String username) throws UsernameNotFoundException;
	public List<AuthUser> search(QueryParam queryParam) throws SQLException;
	public Long countObject() throws SQLException;
}
