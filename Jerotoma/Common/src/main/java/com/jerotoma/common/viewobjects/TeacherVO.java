package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jerotoma.common.constants.UserConstant;

public class TeacherVO extends PersonVO {
		
	private PositionVO position;
	
	private AcademicDisciplineVO academicDiscipline;
	
	public TeacherVO(ResultSet rs) throws SQLException {
		super(rs, UserConstant.USER_TYPES.TEACHER.getType());						
	}

	public PositionVO getPosition() {
		return position;
	}

	public void setPosition(PositionVO position) {
		this.position = position;
	}

	public AcademicDisciplineVO getAcademicDiscipline() {
		return academicDiscipline;
	}

	public void setAcademicDiscipline(AcademicDisciplineVO academicDiscipline) {
		this.academicDiscipline = academicDiscipline;
	}
}
