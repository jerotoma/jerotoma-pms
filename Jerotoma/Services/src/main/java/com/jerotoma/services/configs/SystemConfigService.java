package com.jerotoma.services.configs;

import java.sql.SQLException;

import com.jerotoma.common.models.config.SystemConfig;
import com.jerotoma.services.BaseService;

public interface SystemConfigService extends BaseService<SystemConfig> {

	Long countObject() throws SQLException;
	

}
