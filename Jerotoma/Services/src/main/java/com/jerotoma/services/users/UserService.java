package com.jerotoma.services.users;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.users.UserContext;
import com.jerotoma.common.viewobjects.UserVO;

public interface UserService extends UserDetailsService {	
	public UserVO loadCurrentUser();
	public UserContext getUserContext(Authentication authentication);
	public UserVO getUserByUsername(String username);
	public UserVO getUserByUserId(Integer userId);
	public List<UserVO> searchUser(QueryParam param) ;

}
