package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.jerotoma.common.models.users.Teacher;

public class AcademicDisciplineVO {
	
	private Integer id;
	
	private String name;
	
	private String code;
	
	private String description;
	
	private Teacher teacher;	
	
	private Date createdOn;
	
	private Date updatedOn;
	
	public AcademicDisciplineVO(ResultSet rs) throws SQLException {
		this.code = rs.getString("code");
		this.name = rs.getString("name");
		this.description = rs.getString("description"); 
		this.id = rs.getInt("id");
		this.updatedOn = rs.getDate("updated_on");
		this.createdOn = rs.getDate("created_on");
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
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
}
