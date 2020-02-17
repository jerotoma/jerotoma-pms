package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.constants.UserConstant;

public class ParentVO extends PersonVO {
	
	private Integer id;	
	
	private List<StudentVO> students;

	public ParentVO(ResultSet rs) throws SQLException {
		super(rs, UserConstant.USER_TYPES.PARENT.getType());
		this.id = rs.getInt(UserConstant.ID);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<StudentVO> getStudents() {
		return students;
	}

	public void setStudents(List<StudentVO> students) {
		this.students = students;
	}	
	
	
}
