package com.jerotoma.api.controllers.pub;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.constants.SystemConfigConstant;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.config.SystemConfig;
import com.jerotoma.common.viewobjects.ThemeVO;
import com.jerotoma.services.configs.SystemConfigService;

@RestController
@RequestMapping(value=EndPointConstants.REST_PUB_THEME_CONTROLLER.BASE)
public class RestPubThemeController {
	
	@Autowired SystemConfigService systemConfigService;
	
	@GetMapping(value = {"","/"})
	@ResponseBody
	public HttpResponseEntity<Object> loadThemeConfigs() throws JDataAccessException {
		HttpResponseEntity<Object> instance = new HttpResponseEntity<>();
		SystemConfig systemConfig = getSystemTheme(SystemConfigConstant.THEME.CURRENT_THEME);
		
		ThemeVO theme = new ThemeVO();
		
		theme.setSystemTheme(systemConfig.getValue());
		theme.setSystemThemeID(systemConfig.getId());
		
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setData(theme);
		return instance;
	}

	private SystemConfig getSystemTheme(String systemConfigKey) {
		SystemConfig systemConfig = null;
		
		try {
			systemConfig = systemConfigService.findObjectUniqueKey(systemConfigKey);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		return systemConfig;
	}

}
