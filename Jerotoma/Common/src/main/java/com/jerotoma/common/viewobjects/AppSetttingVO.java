package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;
import java.util.Map;

import com.jerotoma.common.models.config.SystemConfig;

public class AppSetttingVO {
	
	private Map<String, SystemConfig> mapSystemConfigs;
	
	private Map<String, UserPreferenceVO> mapUserPreferences;
	
	public AppSetttingVO(){}

	public AppSetttingVO(ResultSet rs) {
		
	}
	
	public Map<String, SystemConfig> getMapSystemConfigs() {
		return mapSystemConfigs;
	}

	public void setMapSystemConfigs(Map<String, SystemConfig> systemConfigs) {
		this.mapSystemConfigs = systemConfigs;
	}

	public void setMapUserPreferences(Map<String, UserPreferenceVO> userPreferences) {
		this.mapUserPreferences = userPreferences;		
	}

	public Map<String, UserPreferenceVO> getMapUserPreferences() {
		return mapUserPreferences;
	}	
}
