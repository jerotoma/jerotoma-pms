package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jerotoma.common.constants.UserConstant;

public class ParentDto extends PersonVO {
	
	private Integer id;
	
	public ParentDto(ResultSet rs) throws SQLException {
		super(rs, UserConstant.USER_TYPES.PARENT.getType());
		this.id = rs.getInt(UserConstant.ID);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}