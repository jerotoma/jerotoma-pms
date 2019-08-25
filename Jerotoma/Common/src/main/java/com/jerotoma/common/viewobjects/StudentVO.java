package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jerotoma.common.constants.UserConstant;
import com.jerotoma.common.models.users.Person;

public class StudentVO extends Person {
	
	private Integer id;
	private Integer studentNumber;	
	
	public StudentVO(ResultSet rs) throws SQLException {
		super(rs);
		this.id = rs.getInt(UserConstant.ID);
		this.studentNumber = rs.getInt(UserConstant.STUDENT_NUMBER);		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(Integer studentNumber) {
		this.studentNumber = studentNumber;
	}
}
