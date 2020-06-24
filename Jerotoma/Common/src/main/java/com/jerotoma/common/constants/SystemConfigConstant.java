package com.jerotoma.common.constants;

public class SystemConfigConstant {
	
	public static final String SYSTEM_CONFINGS = "systemConfigs";	
	public static final String NAME = "name";
	public static final String VALUE = "value";
	public static final String ID = "id";	
	public static final String SYSTEM_CONFING_COUNT = "count";
	
	
	public static enum THEME_CONFIG {
		CURRENT_THEME("currentTheme", "system.config.current_theme"),
		OVERRIDE_USER_THEME("overrideUserTheme", "system.config.override_user_theme");
		
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
	
	public static enum GENERAL_CONFIG {
		CURRENT_THEME("currentTheme", "system.config.current_theme"),
		OVERRIDE_USER_THEME("overrideUserTheme", "system.config.override_user_theme"),
		CURRENT_ACADEMIC_YEAR("currentAcademicYear", "system.config.current_academic_year");
		
		String name;
		String dbName;
		
		GENERAL_CONFIG(String name, String dbName) {
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

	public static GENERAL_CONFIG getGeneralConfigByDbName(String dbName) {
		for (GENERAL_CONFIG themConfig: GENERAL_CONFIG.values()) {
			if (themConfig.getDbName().equals(dbName)) {
				return themConfig;
			}
		}
		return null;
	}
	
	public static GENERAL_CONFIG getGeneralConfigByName(String name) {
		for (GENERAL_CONFIG themConfig: GENERAL_CONFIG.values()) {
			if (themConfig.getName().equals(name)) {
				return themConfig;
			}
		}
		return null;
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
