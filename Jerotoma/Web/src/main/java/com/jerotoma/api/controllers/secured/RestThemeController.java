package com.jerotoma.api.controllers.secured;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jerotoma.api.controllers.BaseController;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.constants.SystemConfigConstant;
import com.jerotoma.common.constants.UserPreferenceConstant;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.config.SystemConfig;
import com.jerotoma.common.models.config.UserPreference;
import com.jerotoma.common.viewobjects.AppSetttingVO;
import com.jerotoma.common.viewobjects.ThemeVO;
import com.jerotoma.common.viewobjects.UserPreferenceVO;
import com.jerotoma.services.configs.SystemConfigService;
import com.jerotoma.services.configs.UserPreferenceService;
import com.jerotoma.utils.StringUtility;

@RestController
@RequestMapping(value=EndPointConstants.REST_THEME_CONTROLLER.BASE)
public class RestThemeController  extends BaseController {
	
	@Autowired SystemConfigService systemConfigService;
	@Autowired UserPreferenceService userPreferenceService;;

	@GetMapping(value = {"","/"})
	@ResponseBody
	public HttpResponseEntity<Object> loadThemeConfigs(Authentication auth) throws JDataAccessException {
		
		this.logRequestDetail("GET : " + EndPointConstants.REST_THEME_CONTROLLER.BASE);
		this.securityCheckAdminAccess(auth);
		this.proccessLoggedInUser(auth);
		
		SystemConfig currentTheme = getSystemTheme(SystemConfigConstant.THEME_CONFIG.CURRENT_THEME.getDbName());
		SystemConfig overrideUserTheme = getSystemTheme(SystemConfigConstant.THEME_CONFIG.OVERRIDE_USER_THEME.getDbName());
		UserPreference userTheme =  getUserTheme(UserPreferenceConstant.THEME_CONFIG.CURRENT_USER_THEME.getDbName(), authUser.getId());
		
		ThemeVO theme = new ThemeVO();
		
		theme.setSystemTheme(currentTheme.getValue());
		theme.setSystemThemeID(currentTheme.getId());
		
		theme.setUserTheme(userTheme.getValue());
		theme.setUserThemeID(userTheme.getId());
		
		theme.setOverrideUserTheme(StringUtility.booleanValueOf(overrideUserTheme.getValue()));
		theme.setOverrideUserThemeID(overrideUserTheme.getId());
		
		AppSetttingVO appSetting = new AppSetttingVO();
		Map<String, SystemConfig> mapSystemConfigs = new HashMap<>();
		Map<String, UserPreferenceVO> mapUserPreferences = new HashMap<>();
		
		
		
		mapSystemConfigs.put(SystemConfigConstant.THEME_CONFIG.CURRENT_THEME.getName(), currentTheme);
		mapSystemConfigs.put(SystemConfigConstant.THEME_CONFIG.OVERRIDE_USER_THEME.getName(), overrideUserTheme);
		mapUserPreferences.put(UserPreferenceConstant.THEME_CONFIG.CURRENT_USER_THEME.getName(), new UserPreferenceVO(userTheme));
		
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
		} catch (SQLException | EmptyResultDataAccessException e) {
			if (e instanceof EmptyResultDataAccessException) {
				systemConfig = new SystemConfig() ; 
			} else {
				throw new JDataAccessException(e.getMessage(), e);
			}			
		}
		return systemConfig;
	}
	
	private UserPreference getUserTheme(String userPreferenceKey, int userId) {
		UserPreference userPreference = null;
		
		try {
			userPreference = userPreferenceService.findUserPreferenceByKeyAndUserID(userId, userPreferenceKey);		
		} catch (SQLException | EmptyResultDataAccessException e) {
			if (e instanceof EmptyResultDataAccessException) {
				userPreference = new UserPreference() ; 
			} else {
				throw new JDataAccessException(e.getMessage(), e);
			}				
		}
		return userPreference;
	}

}
