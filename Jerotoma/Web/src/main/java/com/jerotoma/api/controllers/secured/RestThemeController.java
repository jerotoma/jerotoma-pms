package com.jerotoma.api.controllers.secured;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jerotoma.api.controllers.BaseController;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.services.assemblers.systemconfigs.AssemblerSystemConfigService;
import com.jerotoma.services.assemblers.systemconfigs.AssemblerUserPreferenceService;

@RestController
@RequestMapping(value=EndPointConstants.REST_THEME_CONTROLLER.BASE)
public class RestThemeController  extends BaseController {
	
	@Autowired AssemblerSystemConfigService systemConfigService;
	@Autowired AssemblerUserPreferenceService userPreferenceService;;

	@GetMapping(value = {"","/"})
	@ResponseBody
	public HttpResponseEntity<Object> loadThemeConfigs(Authentication auth) throws JDataAccessException {
		
		this.logRequestDetail("GET : " + EndPointConstants.REST_THEME_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		this.proccessLoggedInUser(auth);		
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setData(systemConfigService.getCurrentThemeByUserID(authUser.getId()));		
		return response;
	}
}
