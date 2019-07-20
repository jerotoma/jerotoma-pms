package com.jerotoma.api.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.constants.RoleConstant;
import com.jerotoma.common.constants.UserConstant;
import com.jerotoma.common.roles.Role;
import com.jerotoma.common.users.AuthUser;
import com.jerotoma.http.HttpResponseEntity;
import com.jerotoma.services.users.AuthUserService;

@RestController
@RequestMapping(EndPointConstants.REST_AUTH_CONTROLLER.BASE)
public class RestAuthController {
	
	@Autowired AuthUserService authUserService;
	AuthUser authUser;
	
	@PostMapping(EndPointConstants.REST_AUTH_CONTROLLER.REGISTER)
	@ResponseBody
	public HttpResponseEntity<AuthUser> postCreate(@RequestBody Map<String, Object> params) throws SQLException{
		HttpResponseEntity<AuthUser> instance = new HttpResponseEntity<AuthUser>();
		
		String username = (String) params.get(UserConstant.USER_NAME);
		String firstName = (String) params.get(UserConstant.FIRST_NAME);
		String lastName = (String) params.get(UserConstant.LAST_NAME);
		String password = (String) params.get(UserConstant.PASSWORD);
		String confirmPass = (String) params.get(UserConstant.CONFIRM_PASS);
		String term = (String) params.get(UserConstant.TERM);
		Date updatedOn = new Date();
		Date createdOn = new Date();
		
		
		if(!password.trim().equals(confirmPass.trim())) {
			throw new RuntimeException("Password not confirmed");
		}
		
		List<Role> roles = new ArrayList<>();
		Role role = new Role();	
		role.setName(RoleConstant.EROLE.ROLE_ADMIN.name());
		role.setDisplayName("ADMIN");
		roles.add(role);
		
		authUser = new AuthUser(username, password, true, true, true, true, roles);
		authUser.setCreatedOn(createdOn);
		authUser.setFirstName(firstName);
		authUser.setLastName(lastName);		
		authUser.setUpdatedOn(updatedOn);
		

		try {
			authUser = authUserService.createObject(authUser);
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());			
		}	
		instance.setSuccess(true);
		instance.setStatusCode("200");
		instance.setData(authUser);
		return instance;
		
	}

}
