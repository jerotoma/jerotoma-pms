package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jerotoma.common.constants.UserConstant;

public class TeacherVO extends PersonVO {
		
	private PositionVO position;	
	private DepartmentVO department;
	
	public TeacherVO(ResultSet rs) throws SQLException {
		super(rs, UserConstant.USER_TYPE.TEACHER.getType());						
	}

	public PositionVO getPosition() {
		return position;
	}

	public void setPosition(PositionVO position) {
		this.position = position;
	}

	public DepartmentVO getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentVO department) {
		this.department = department;
	}
}
