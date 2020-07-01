package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.jerotoma.common.constants.ProgramConstant;

public class ProgramVO {

	private Integer id;
	
	private String code;
	
	private String name;
	
	private String description;
	
	private Date createdOn;
	
	private Date updatedOn;
	
	private List<AcademicLevelVO> academicLevels;

	public ProgramVO(ResultSet rs) throws SQLException {
		this.id = rs.getInt(ProgramConstant.ID);
		this.name = rs.getString(ProgramConstant.NAME);
		this.code = rs.getString(ProgramConstant.CODE);
		this.description = rs.getString(ProgramConstant.DESCRIPTION);
		this.createdOn = rs.getDate("createdOn");
		this.updatedOn = rs.getDate("updatedOn");
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

	public List<AcademicLevelVO> getAcademicLevels() {
		return academicLevels;
	}

	public void setAcademicLevels(List<AcademicLevelVO> academicLevels) {
		this.academicLevels = academicLevels;
	}
}
