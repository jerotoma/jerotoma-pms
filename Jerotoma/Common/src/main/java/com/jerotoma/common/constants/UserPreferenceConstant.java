package com.jerotoma.common.constants;

public class UserPreferenceConstant {
	public static final String USER_PREFERENCE = "userPreference";	
	public static final String USER_PREFERENCES = "userPreferences";	
	public static final String NAME = "name";
	public static final String VALUE = "value";
	public static final String ID = "id";
	public static final String USER_ID = "user_id";
	
	public static enum THEME_CONFIG {
		CURRENT_USER_THEME("currentUserTheme", "system.config.userpreference.current_theme");
		
		String name;
		String dbName;
		
		THEME_CONFIG(String name, String dbName) {
			this.name = name;
			this.dbName = dbName;
		}

		public String getName() {
			return name;
		}

		public String getDbName() {
			return dbName;
		}
	}

	public static THEME_CONFIG getThemeConfigByDbName(String dbName) {
		for (THEME_CONFIG themConfig: THEME_CONFIG.values()) {
			if (themConfig.getDbName().equals(dbName)) {
				return themConfig;
			}
		}
		return null;
	}
	
	public static THEME_CONFIG getThemeConfigByName(String name) {
		for (THEME_CONFIG themConfig: THEME_CONFIG.values()) {
			if (themConfig.getName().equals(name)) {
				return themConfig;
			}
		}
		return null;
	}

	
}
