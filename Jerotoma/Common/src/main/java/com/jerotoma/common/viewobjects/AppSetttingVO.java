package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import com.jerotoma.common.models.config.UserPreference;

public class AppSetttingVO {
	
	private Map<String, Object> systemConfigs;
	
	private List<UserPreferenceVO> userPreferences;
	
	public AppSetttingVO(){}

	public AppSetttingVO(ResultSet rs) {
		
	}
	
	public Map<String, Object> getSystemConfigs() {
		return systemConfigs;
	}

	public void setSystemConfigs(Map<String, Object> systemConfigs) {
		this.systemConfigs = systemConfigs;
	}

	public void setUserPreferences(List<UserPreferenceVO> userPreferences) {
		this.userPreferences = userPreferences;
		
	}
}
