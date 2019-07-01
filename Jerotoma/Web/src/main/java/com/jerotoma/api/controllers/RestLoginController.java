package com.jerotoma.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jerotoma.common.users.AuthUser;
import com.jerotoma.http.HttpResponseEntity;
import com.jerotoma.http.endpoints.AppEndpoint;
import com.jerotoma.services.users.AuthUserService;

@Controller
@RequestMapping(AppEndpoint.LOGIN_CONTROLLER.BASE)
public class RestLoginController {
	
	@Autowired AuthUserService authUserService;
	
	public RestLoginController(){
		
	}
	
	@GetMapping(value={"",AppEndpoint.LOGIN_CONTROLLER.INDEX})
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
