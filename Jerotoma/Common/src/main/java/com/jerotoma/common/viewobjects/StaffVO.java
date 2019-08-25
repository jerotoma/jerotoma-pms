package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jerotoma.common.constants.UserConstant;
import com.jerotoma.common.models.users.Person;

public class StaffVO extends Person {
	
	private Integer id;
	
	public StaffVO(ResultSet rs) throws SQLException {
		super(rs);
		this.id = rs.getInt(UserConstant.ID);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
