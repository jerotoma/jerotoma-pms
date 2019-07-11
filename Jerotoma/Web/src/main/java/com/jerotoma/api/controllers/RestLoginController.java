package com.jerotoma.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jerotoma.common.users.AuthUser;
import com.jerotoma.http.HttpResponseEntity;
import com.jerotoma.http.endpoints.EndPointConstants;
import com.jerotoma.services.users.AuthUserService;

@RestController
@RequestMapping(EndPointConstants.LOGIN_CONTROLLER.BASE)
public class RestLoginController {
	
	@Autowired AuthUserService authUserService;
	
	public RestLoginController(){
		
	}
	
	@GetMapping(value={"",EndPointConstants.LOGIN_CONTROLLER.INDEX})
	@ResponseBody
	public HttpResponseEntity<AuthUser> getIndexLogin(){
		HttpResponseEntity<AuthUser> instance = new HttpResponseEntity<AuthUser>();
		String username = "otomang@hotmail.com";
		AuthUser authUser = authUserService.loadUserByUsername(username);		
		instance.setSuccess(true);
		instance.setStatusCode("200");
		instance.setData(authUser);
		return instance;
		
	}
	
}
