package com.jerotoma.services.configs.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.SystemConfigConstant;
import com.jerotoma.common.constants.SystemConfigConstant.GENERAL_CONFIG;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.constants.UserPreferenceConstant;
import com.jerotoma.common.models.config.SystemConfig;
import com.jerotoma.common.models.config.UserPreference;
import com.jerotoma.common.viewobjects.ThemeVO;
import com.jerotoma.database.dao.configs.SystemConfigDao;
import com.jerotoma.database.dao.configs.UserPreferenceDao;
import com.jerotoma.services.configs.SystemConfigService;

@Service
@Transactional
public class SystemConfigServiceImpl implements SystemConfigService {
	
	@Autowired SystemConfigDao systemConfigDao;
	@Autowired UserPreferenceDao systemPreferenceDao;

	@Override
	public SystemConfig findObject(Integer primaryKey) throws SQLException {
		
		return systemConfigDao.findObject(primaryKey);
	}

	@Override
	public SystemConfig findObjectUniqueKey(String uniqueKey) throws SQLException {
		GENERAL_CONFIG generalConfig = SystemConfigConstant.getGeneralConfigByName(uniqueKey);
		if (generalConfig != null) {
			uniqueKey = generalConfig.getDbName();
		}
		return systemConfigDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public SystemConfig createObject(SystemConfig object) throws SQLException {
		return systemConfigDao.createObject(object);
	}

	@Override
	public SystemConfig updateObject(SystemConfig object) throws SQLException {
		return systemConfigDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(SystemConfig object) throws SQLException {
		return systemConfigDao.deleteObject(object);
	}

	@Override
	public List<SystemConfig> loadList(QueryParam queryParam) throws SQLException {
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
	public ThemeVO getCurrentThemeByUserID(Integer userId) throws SQLException {
		SystemConfig currentTheme = getSystemTheme(SystemConfigConstant.THEME_CONFIG.CURRENT_THEME.getDbName());
		SystemConfig overrideUserTheme = getSystemTheme(SystemConfigConstant.THEME_CONFIG.OVERRIDE_USER_THEME.getDbName());
		UserPreference userTheme =  getUserTheme(UserPreferenceConstant.THEME_CONFIG.CURRENT_USER_THEME.getDbName(), userId);
		
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
	
	
	private SystemConfig getSystemTheme(String systemConfigKey) throws SQLException {
		
		SystemConfig systemConfig = null;
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
	
	private UserPreference getUserTheme(String userPreferenceKey, int userId) throws SQLException {			
		UserPreference userPreference = null;
		try {
			userPreference = systemPreferenceDao.findUserPreferenceByKeyAndUserID(userId, userPreferenceKey);
		} catch (SQLException  | EmptyResultDataAccessException e) {
			if (e instanceof EmptyResultDataAccessException) {
				userPreference = null;
			} else {
				throw new JDataAccessException(e.getMessage(), e);
			}	
		}		
		return userPreference;		
	}

	@Override
	public ThemeVO getCurrentTheme() throws SQLException {
		SystemConfig currentTheme = getSystemTheme(SystemConfigConstant.THEME_CONFIG.CURRENT_THEME.getDbName());
		SystemConfig overrideUserTheme = getSystemTheme(SystemConfigConstant.THEME_CONFIG.OVERRIDE_USER_THEME.getDbName());		
		
		ThemeVO theme = new ThemeVO();		
		
		theme.setSystemTheme(currentTheme.getValue());
		theme.setSystemThemeID(currentTheme.getId());		
		theme.setOverrideUserTheme(Boolean.valueOf(overrideUserTheme.getValue()));
		theme.setOverrideUserThemeID(overrideUserTheme.getId());
		
		return theme;
	}

}
