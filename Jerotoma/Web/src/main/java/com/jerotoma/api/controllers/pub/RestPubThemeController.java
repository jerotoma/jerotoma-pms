package com.jerotoma.api.controllers.pub;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.constants.SystemConfigConstant;
import com.jerotoma.common.constants.UserPreferenceConstant;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.config.SystemConfig;
import com.jerotoma.common.viewobjects.AppSetttingVO;
import com.jerotoma.common.viewobjects.UserPreferenceVO;
import com.jerotoma.services.configs.SystemConfigService;

@RestController
@RequestMapping(value=EndPointConstants.REST_PUB_THEME_CONTROLLER.BASE)
public class RestPubThemeController {
	
	@Autowired SystemConfigService systemConfigService;
	
	@GetMapping(value = {"","/"})
	@ResponseBody
	public HttpResponseEntity<Object> loadThemeConfigs() throws JDataAccessException {
		HttpResponseEntity<Object> instance = new HttpResponseEntity<>();
		SystemConfig systemConfig = getSystemTheme(SystemConfigConstant.THEME_CONFIG.CURRENT_THEME.getDbName());
		SystemConfig overrideUserTheme = getSystemTheme(SystemConfigConstant.THEME_CONFIG.OVERRIDE_USER_THEME.getDbName());
				
		AppSetttingVO appSetting = new AppSetttingVO();
		Map<String, SystemConfig> mapSystemConfigs = new HashMap<>();
		Map<String, UserPreferenceVO> mapUserPreferences = new HashMap<>();
		
		
		
		mapSystemConfigs.put(SystemConfigConstant.THEME_CONFIG.CURRENT_THEME.getName(), systemConfig);
		mapSystemConfigs.put(SystemConfigConstant.THEME_CONFIG.OVERRIDE_USER_THEME.getName(), overrideUserTheme);
		mapUserPreferences.put(UserPreferenceConstant.THEME_CONFIG.CURRENT_USER_THEME.getName(), new UserPreferenceVO());
		
		appSetting.setMapUserPreferences(mapUserPreferences);
		appSetting.setMapSystemConfigs(mapSystemConfigs);		
		
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setData(appSetting);
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
