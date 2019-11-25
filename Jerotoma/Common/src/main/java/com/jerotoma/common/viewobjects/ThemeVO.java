package com.jerotoma.common.viewobjects;

public class ThemeVO {
	
	Integer systemThemeID;
	
	String systemTheme;
	
	String userTheme;
	
	Integer userThemeID;
	
	boolean overrideUserTheme;
	
	Integer overrideUserThemeID;

	public Integer getSystemThemeID() {
		return systemThemeID;
	}

	public void setSystemThemeID(Integer systemThemeID) {
		this.systemThemeID = systemThemeID;
	}

	public String getSystemTheme() {
		return systemTheme;
	}

	public void setSystemTheme(String systemTheme) {
		this.systemTheme = systemTheme;
	}

	public String getUserTheme() {
		return userTheme;
	}

	public void setUserTheme(String userTheme) {
		this.userTheme = userTheme;
	}

	public Integer getUserThemeID() {
		return userThemeID;
	}

	public void setUserThemeID(Integer userThemeID) {
		this.userThemeID = userThemeID;
	}

	public boolean isOverrideUserTheme() {
		return overrideUserTheme;
	}

	public void setOverrideUserTheme(boolean overrideUserTheme) {
		this.overrideUserTheme = overrideUserTheme;
	}

	public Integer getOverrideUserThemeID() {
		return overrideUserThemeID;
	}

	public void setOverrideUserThemeID(Integer overrideUserThemeID) {
		this.overrideUserThemeID = overrideUserThemeID;
	}
}
