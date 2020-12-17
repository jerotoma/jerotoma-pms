package com.jerotoma.api.controllers.pub;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.services.configs.SystemConfigService;

@RestController
@RequestMapping(value=EndPointConstants.REST_PUB_THEME_CONTROLLER.BASE)
public class RestPubThemeController {
	
	@Autowired SystemConfigService systemConfigService;
	
	@GetMapping(value = {"","/"})
	@ResponseBody
	public HttpResponseEntity<Object> loadThemeConfigs() throws JDataAccessException {
		HttpResponseEntity<Object> instance = new HttpResponseEntity<>();
		
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		try {
			instance.setData(systemConfigService.getCurrentTheme());
		} catch (SQLException  | EmptyResultDataAccessException e) {
			if (e instanceof EmptyResultDataAccessException) {
				instance.setData(null);
			} else {
				throw new JDataAccessException(e.getMessage(), e);
			}	
		}
		return instance;
	}

}
