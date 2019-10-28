package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jerotoma.common.constants.UserPreferenceConstant;

public class UserPreferenceVO {
	
	private Integer id;
	
	private String name;
	
	private String value;
	
	private TeacherVO user;
	
	UserPreferenceVO(){
		
	}
	
	public UserPreferenceVO(ResultSet rs, TeacherVO user) throws SQLException {
		this.user = user;
		this.name = rs.getString(UserPreferenceConstant.NAME);
		this.value = rs.getString(UserPreferenceConstant.VALUE);
		this.id = rs.getInt(UserPreferenceConstant.ID);
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

	public TeacherVO getUser() {
		return user;
	}

	public void setUser(TeacherVO user) {
		this.user = user;
	}		
}
