package com.jerotoma.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.users.AuthUser;
import com.jerotoma.common.viewobjects.UserVO;
import com.jerotoma.config.auth.common.UserContext;
import com.jerotoma.config.auth.interfaces.IAuthenticationFacade;
import com.jerotoma.services.users.AuthUserService;

@RestController
@RequestMapping(EndPointConstants.REST_USER_CONTROLLER.BASE)
public class RestUserController {
	
	@Autowired AuthUserService authUserService;
	@Autowired IAuthenticationFacade authenticationFacade;
	AuthUser authUser;

	@GetMapping(EndPointConstants.REST_USER_CONTROLLER.CURRENT_USER)
	@ResponseBody
	public HttpResponseEntity<UserVO> getAuthentcatedUser(Authentication auth) throws UsernameNotFoundException{
		HttpResponseEntity<UserVO> instance = new HttpResponseEntity<>();
		
		if(auth == null) {
			instance.setSuccess(false);
			instance.setStatusCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
			return instance;
		}
		
		UserContext userContext = authenticationFacade.getUserContext(auth);
		
		
		try {	
			authUser = authUserService.loadUserByUsername(userContext.getUsername());
		} catch (UsernameNotFoundException e) {
			throw new UsernameNotFoundException(e.getMessage());			
		}	
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setData(new UserVO(authUser));
		return instance;
		
	}

}
