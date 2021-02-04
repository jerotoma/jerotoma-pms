package com.jerotoma.api.controllers.secured;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jerotoma.api.controllers.BaseController;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.services.academic.ProgressStatusService;

@RestController
@RequestMapping(EndPointConstants.REST_PROGRESSS_STATUS_CONTROLLER.BASE)
public class RestProgressStatusController extends BaseController {
	
	@Autowired ProgressStatusService progressStatusService;
	
	@GetMapping
	public HttpResponseEntity<Object> loadProgressStatuses(Authentication auth) {
		
		this.logRequestDetail("GET : " + EndPointConstants.REST_PROGRAM_CONTROLLER.BASE);
		this.proccessLoggedInUser(auth);
		this.securityCheckAccessByRoles(auth);		
		response.setData(progressStatusService.loadProgressStatuses());								
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));		
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}

}
