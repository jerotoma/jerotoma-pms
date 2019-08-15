package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.jerotoma.common.constants.UserConstant;
import com.jerotoma.common.models.users.Person;

public class TeacherVO extends Person{
	private Integer id;
	
	private Integer userId;
	
	private String teacherCode;
	
	private PositionVO position;
	
	private AcademicDisciplineVO academicDiscipline;
	
	private Date createdOn;
	
	private Date updatedOn;
	
	public TeacherVO(ResultSet rs) throws SQLException {
		this.id = rs.getInt(UserConstant.ID);
		this.userId = rs.getInt("user_id");
		this.firstName = rs.getString("first_name");
		this.lastName = rs.getString("last_name");
		this.gender = rs.getString(UserConstant.GENDER);
		this.occupation = rs.getString(UserConstant.OCCUPATION);
		this.picture = rs.getString(UserConstant.AVATAR);
		this.teacherCode = rs.getString(UserConstant.TEACHER_CODE);
		this.updatedOn = rs.getDate(UserConstant.UPDATED_ON);
		this.createdOn = rs.getDate(UserConstant.CREATED_ON);
		this.birthDate = rs.getDate("birth_date");
		
		this.fullName = this.firstName + " " + this.lastName;
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

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
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
