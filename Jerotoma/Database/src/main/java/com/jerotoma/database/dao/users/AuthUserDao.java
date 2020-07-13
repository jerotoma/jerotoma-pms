package com.jerotoma.database.dao.users;

import java.sql.SQLException;
import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.users.User;
import com.jerotoma.common.viewobjects.UserVO;
import com.jerotoma.database.dao.BaseDao;

public interface AuthUserDao extends BaseDao<User>{
	public User loadUserByUsername(String username) throws UsernameNotFoundException;
	public List<User> search(QueryParam queryParam) throws SQLException;
	public Long countObject() throws SQLException;
	public List<UserVO> searchUser(QueryParam param) throws SQLException;
}
