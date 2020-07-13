package com.jerotoma.services.users.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.users.User;
import com.jerotoma.common.models.users.UserContext;
import com.jerotoma.common.viewobjects.PersonVO;
import com.jerotoma.common.viewobjects.UserVO;
import com.jerotoma.services.assemblers.AssemblerParentService;
import com.jerotoma.services.assemblers.AssemblerSequenceGeneratorService;
import com.jerotoma.services.assemblers.AssemblerStaffService;
import com.jerotoma.services.assemblers.AssemblerStudentService;
import com.jerotoma.services.assemblers.AssemblerTeacherService;
import com.jerotoma.services.users.AuthUserService;
import com.jerotoma.services.users.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired AuthUserService authUserService;
	@Autowired AssemblerTeacherService assemblerTeacherService;
	@Autowired AssemblerStudentService assemblerStudentService;
	@Autowired AssemblerStaffService assemblerStaffService;
	@Autowired AssemblerParentService assemblerParentService;
	@Autowired AssemblerSequenceGeneratorService sequenceGeneratorService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return authUserService.loadUserByUsername(username);
	}

	@Override
	public UserVO loadCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserContext userContext = getUserContext(authentication);
		User authUser = (User) loadUserByUsername(userContext.getUsername());		
		return getUserDetails(authUser);
	}

	@Override
	public UserContext getUserContext(Authentication authentication) {

		if (authentication == null) {
			throw new IllegalStateException();
		}
		Object principal = authentication.getPrincipal();

		if (authentication.getPrincipal() instanceof UserContext) {
			return (UserContext) principal;
		} else if (principal instanceof User) {
			User user = (User) principal;
			return new UserContext(user.getUsername(), user.getAuthorities());
		} else {
			throw new BadCredentialsException("Invalid credential was provided");
		}
	}

	@Override
	public UserVO getUserByUsername(String username) {		
		User authUser = (User)loadUserByUsername(username);		
		return getUserDetails(authUser);
	}

	private UserVO getUserDetails(User authUser) {
		PersonVO person = null;
		
		if (authUser == null) {
			return null;
		}
		
		try {
			switch (authUser.getUserType()) {
			case TEACHER:
				person = assemblerTeacherService.findObjectUniqueKey(authUser.getUsername());
				break;
			case STUDENT:
				person = assemblerStudentService.findObjectUniqueKey(authUser.getUsername());
				break;
			case STAFF:
				person = assemblerStaffService.findObjectUniqueKey(authUser.getUsername());
				break;
			case PARENT:
				person = assemblerParentService.findObjectUniqueKey(authUser.getUsername());
				break;
			default:
				throw new UsernameNotFoundException("User type not found");
			}
		} catch (SQLException e) {
			person = null;
			e.printStackTrace();
		}
		
		if (person == null) {
			return null;
		}		
		return new UserVO(authUser, person);
	}

	@Override
	public UserVO getUserByUserId(Integer userId) {
		User authUser = getUserById(userId);
		return getUserDetails(authUser);
	}
	
	
	private User getUserById(Integer userId) {
		try {
			return authUserService.findObject(userId);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<UserVO> searchUser(QueryParam param) {		
		try {
			return authUserService.searchUser(param);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return new ArrayList<UserVO>();		
	}

}
