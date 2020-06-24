package com.jerotoma.services.assemblers.systemconfigs;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.SystemConfigConstant;
import com.jerotoma.common.constants.SystemConfigConstant.GENERAL_CONFIG;
import com.jerotoma.common.constants.UserPreferenceConstant;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.viewobjects.SystemConfigVO;
import com.jerotoma.common.viewobjects.ThemeVO;
import com.jerotoma.common.viewobjects.UserPreferenceVO;
import com.jerotoma.database.assemblers.dao.systemconfig.AssemblerSystemConfigDao;
import com.jerotoma.database.assemblers.dao.systemconfig.AssemblerUserPreferenceDao;

@Service
public class AssemblerSystemConfigServiceImpl implements AssemblerSystemConfigService {

	@Autowired AssemblerSystemConfigDao systemConfigDao;
	@Autowired AssemblerUserPreferenceDao systemPreferenceDao;

	@Override
	public SystemConfigVO findObject(Integer primaryKey) throws SQLException {
		
		return systemConfigDao.findObject(primaryKey);
	}

	@Override
	public SystemConfigVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		GENERAL_CONFIG generalConfig = SystemConfigConstant.getGeneralConfigByName(uniqueKey);
		if (generalConfig != null) {
			uniqueKey = generalConfig.getDbName();
		}
		return systemConfigDao.findObjectUniqueKey(uniqueKey);
	}


	@Override
	public List<SystemConfigVO> loadList(QueryParam queryParam) throws SQLException {
		return systemConfigDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return systemConfigDao.loadMapList(queryParam);
	}

	@Override
	public Long countObject() throws SQLException {
		return systemConfigDao.countObject();
	}

	@Override
	public ThemeVO getCurrentThemeByUserID(Integer userId) {
		SystemConfigVO currentTheme = getSystemTheme(SystemConfigConstant.THEME_CONFIG.CURRENT_THEME.getDbName());
		SystemConfigVO overrideUserTheme = getSystemTheme(SystemConfigConstant.THEME_CONFIG.OVERRIDE_USER_THEME.getDbName());
		UserPreferenceVO userTheme =  getUserTheme(UserPreferenceConstant.THEME_CONFIG.CURRENT_USER_THEME.getDbName(), userId);
		
		ThemeVO theme = new ThemeVO();
		if (currentTheme != null) {
			theme.setSystemTheme(currentTheme.getValue());
			theme.setSystemThemeID(currentTheme.getId());
		}
		
		if (userTheme != null) {
			theme.setUserTheme(userTheme.getValue());
			theme.setUserThemeID(userTheme.getId());
		}
		
		if (overrideUserTheme != null) {
			theme.setOverrideUserTheme(Boolean.valueOf(overrideUserTheme.getValue()));
			theme.setOverrideUserThemeID(overrideUserTheme.getId());
		}		
		return theme;
	}
	
	
	private SystemConfigVO getSystemTheme(String systemConfigKey) {
		
		SystemConfigVO systemConfig = null;
		try {
			systemConfig = systemConfigDao.findObjectUniqueKey(systemConfigKey);
		} catch (SQLException  | EmptyResultDataAccessException e) {
			if (e instanceof EmptyResultDataAccessException) {
				systemConfig = null;
			} else {
				throw new JDataAccessException(e.getMessage(), e);
			}	
		}		
		return systemConfig;
	}
	
	private UserPreferenceVO getUserTheme(String userPreferenceKey, int userId) {			
		UserPreferenceVO userPreference = null;
		try {
			userPreference = systemPreferenceDao.findUserPreferenceByKeyAndUserId(userId, userPreferenceKey);
		} catch (SQLException e) {
			userPreference = null;
			e.printStackTrace();
		}		
		return userPreference;		
	}

	@Override
	public ThemeVO getCurrentTheme() {
		SystemConfigVO currentTheme = getSystemTheme(SystemConfigConstant.THEME_CONFIG.CURRENT_THEME.getDbName());
		SystemConfigVO overrideUserTheme = getSystemTheme(SystemConfigConstant.THEME_CONFIG.OVERRIDE_USER_THEME.getDbName());		
		
		ThemeVO theme = new ThemeVO();		
		
		theme.setSystemTheme(currentTheme.getValue());
		theme.setSystemThemeID(currentTheme.getId());		
		theme.setOverrideUserTheme(Boolean.valueOf(overrideUserTheme.getValue()));
		theme.setOverrideUserThemeID(overrideUserTheme.getId());
		
		return theme;
	}


}
