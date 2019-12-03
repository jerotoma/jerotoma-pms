package com.jerotoma.common.constants;

public class SystemConfigConstant {
	
	public static final String SYSTEM_CONFINGS = "SystemConfigs";	
	public static final String NAME = "name";
	public static final String VALUE = "value";
	public static final String ID = "id";
	//public static final String CURRENT_THEME = "currentTheme";
	//public static final String CURRENT_THEME_ID = "currentThemeID";
	
	/*public static interface THEME {
		public static final String CURRENT_THEME = "system.config.current_theme";
		public static final String OVERRIDE_USER_THEME = "system.config.overide_user_theme";
	}
	*/
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
