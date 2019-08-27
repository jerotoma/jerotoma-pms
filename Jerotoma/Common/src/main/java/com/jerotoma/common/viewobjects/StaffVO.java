package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jerotoma.common.constants.UserConstant;

public class StaffVO extends PersonVO {
	
	private Integer id;
	
	private PositionVO position;
	
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

	public PositionVO getPosition() {
		return position;
	}

	public void setPosition(PositionVO position) {
		this.position = position;
	}
	
}
