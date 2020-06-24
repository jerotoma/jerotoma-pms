package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jerotoma.common.constants.SystemConfigConstant;

public class SystemConfigVO {
	
	private Integer id;
		
	private String name;
	
	private String value;

	public SystemConfigVO(ResultSet rs) throws SQLException {
		this.id = rs.getInt(SystemConfigConstant.ID);
		this.name = rs.getString(SystemConfigConstant.NAME);
		this.value = rs.getString(SystemConfigConstant.VALUE);
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
	
	

}
