package com.jerotoma.api.controllers.secured;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jerotoma.api.controllers.BaseController;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.http.HttpResponseEntity;

@RestController
@RequestMapping(EndPointConstants.REST_PERMISSION_CONTROLLER.BASE)
public class RestPermissionController extends BaseController {
	
	

	@GetMapping
	public HttpResponseEntity<Object> index(Authentication auth) {
		
		this.logRequestDetail("GET : "+ EndPointConstants.REST_PROGRAM_CONTROLLER.BASE);
		this.proccessLoggedInUser(auth);
		this.securityCheckAccessByRoles(auth);								
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setData(map);
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}
}
