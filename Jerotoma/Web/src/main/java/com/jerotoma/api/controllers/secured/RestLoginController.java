package com.jerotoma.api.controllers.secured;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.users.AuthUser;
import com.jerotoma.services.users.AuthUserService;

@RestController
@RequestMapping(EndPointConstants.REST_LOGIN_CONTROLLER.BASE)
public class RestLoginController {
	
	@Autowired AuthUserService authUserService;
	
	public RestLoginController(){
		
	}
	
	@GetMapping(value={""})
	@ResponseBody
	public HttpResponseEntity<AuthUser> getIndexLogin(){
		HttpResponseEntity<AuthUser> instance = new HttpResponseEntity<AuthUser>();
		
		return instance;
		
	}
	
}
