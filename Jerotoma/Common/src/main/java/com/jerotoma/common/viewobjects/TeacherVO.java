package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jerotoma.common.constants.UserConstant;
import com.jerotoma.common.models.users.Person;

public class TeacherVO extends Person{
	private Integer id;
	
	private Integer userId;
	
	private String teacherCode;
	
	private PositionVO position;
	
	private AcademicDisciplineVO academicDiscipline;
	
	public TeacherVO(ResultSet rs) throws SQLException {
		super(rs);
		this.id = rs.getInt(UserConstant.ID);
		this.userId = rs.getInt(UserConstant.USER_ID);
		this.teacherCode = rs.getString(UserConstant.TEACHER_CODE);
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getTeacherCode() {
		return teacherCode;
	}

	public void setTeacherCode(String teacherCode) {
		this.teacherCode = teacherCode;
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
