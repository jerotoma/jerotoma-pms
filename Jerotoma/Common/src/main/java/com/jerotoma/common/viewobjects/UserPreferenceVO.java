package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jerotoma.common.constants.UserPreferenceConstant;
import com.jerotoma.common.models.config.UserPreference;

public class UserPreferenceVO {
	
	private Integer id;
	
	private String name;
	
	private String value;
	
	private Integer userId;	

	public UserPreferenceVO(){
		
	}
	
	public UserPreferenceVO(ResultSet rs) throws SQLException {		
		this.userId = rs.getInt(UserPreferenceConstant.USER_ID);
		this.name = rs.getString(UserPreferenceConstant.NAME);
		this.value = rs.getString(UserPreferenceConstant.VALUE);
		this.id = rs.getInt(UserPreferenceConstant.ID);
	}
	
	public UserPreferenceVO(UserPreference preference) {		
		this.name = preference.getName();
		this.value = preference.getValue();
		this.id = preference.getId();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
