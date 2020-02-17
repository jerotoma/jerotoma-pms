package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.constants.UserConstant;

public class StudentVO extends PersonVO {
	
	private Integer id;
	private Integer studentNumber;	
	
	private List<ParentVO> parents;
	
	public StudentVO(ResultSet rs) throws SQLException {
		super(rs, UserConstant.USER_TYPES.STUDENT.getType());
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

	public List<ParentVO> getParents() {
		return parents;
	}

	public void setParents(List<ParentVO> parents) {
		this.parents = parents;
	}	
}
