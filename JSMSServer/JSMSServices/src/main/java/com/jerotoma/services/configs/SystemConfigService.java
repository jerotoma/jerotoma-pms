package com.jerotoma.services.configs;

import java.sql.SQLException;

import com.jerotoma.common.models.config.SystemConfig;
import com.jerotoma.common.viewobjects.ThemeVO;
import com.jerotoma.services.BaseService;

public interface SystemConfigService extends BaseService<SystemConfig> {
	public ThemeVO getCurrentThemeByUserID(Integer userId) throws SQLException;
	public Long countObject() throws SQLException;
	public ThemeVO getCurrentTheme() throws SQLException;;	

}
