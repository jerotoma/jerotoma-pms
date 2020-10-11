package com.jerotoma.services.users;

import java.sql.SQLException;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.users.Person;
import com.jerotoma.common.models.users.User;
import com.jerotoma.common.models.users.UserContext;
import com.jerotoma.common.viewobjects.UserVO;

public interface UserService extends UserDetailsService {	
	public UserVO loadCurrentUser();
	public UserContext getUserContext(Authentication authentication);
	public UserVO getUserByUsername(String username);
	public UserVO getUserVOByUserId(Integer userId);
	public List<UserVO> searchUser(QueryParam param) ;
		
	public Person createUser(Person person, User user) throws SQLException;

}
