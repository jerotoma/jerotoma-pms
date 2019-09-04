package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.jerotoma.common.constants.AcademicYearConstant;
import com.jerotoma.common.constants.SystemConstant;

public class AcademicYearVO {
	
	private Integer id;
	
	private String code;
	
	private String name;
	
	private String yearOfStudy;
	
	private String description;
	
	private Date createdOn;
	
	private Date updatedOn;
	
	public AcademicYearVO(ResultSet rs) throws SQLException {
		this.code = rs.getString(AcademicYearConstant.ACADEMIC_YEAR_CODE);
		this.name = rs.getString(AcademicYearConstant.ACADEMIC_YEAR_NAME);
		this.description = rs.getString(AcademicYearConstant.ACADEMIC_YEAR_DESCRIPTION);
		this.yearOfStudy = rs.getString(AcademicYearConstant.ACADEMIC_YEAR_OF_STUDY);
		this.id = rs.getInt(AcademicYearConstant.ACADEMIC_YEAR_ID);
		this.updatedOn = rs.getDate(SystemConstant.UPDATED_ON);
		this.createdOn = rs.getDate(SystemConstant.CREATED_ON);
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getYearOfStudy() {
		return yearOfStudy;
	}

	public void setYearOfStudy(String yearOfStudy) {
		this.yearOfStudy = yearOfStudy;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
