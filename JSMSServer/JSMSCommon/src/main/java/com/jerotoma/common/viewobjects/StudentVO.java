package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.constants.UserConstant;

public class StudentVO extends PersonVO {
	
	private Integer id;
	private Integer studentNumber;
	
	private Integer programId;
	private String programName;
	
	private Integer academicLevelId;
	private String currentAcademicLevelName;
	
	private ParentVO primaryParent;
	
	private List<ParentVO> parents;
	
	public StudentVO(ResultSet rs) throws SQLException {
		super(rs, UserConstant.USER_TYPE.STUDENT.getType());
		this.id = rs.getInt(UserConstant.ID);
		this.studentNumber = rs.getInt(UserConstant.STUDENT_NUMBER);
		this.academicLevelId = rs.getInt(UserConstant.ACADEMIC_LEVEL_ID);
		this.programId = rs.getInt(UserConstant.PROGRAM_ID);
		this.currentAcademicLevelName = rs.getString(UserConstant.ACADEMIC_LEVEL_NAME);
		this.programName = rs.getString(UserConstant.PROGRAM_NAME);
	}

	public StudentVO() {
		
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

	public Integer getProgramId() {
		return programId;
	}

	public void setProgramId(Integer programId) {
		this.programId = programId;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public Integer getAcademicLevelId() {
		return academicLevelId;
	}

	public void setAcademicLevelId(Integer academicLevelId) {
		this.academicLevelId = academicLevelId;
	}

	public String getCurrentAcademicLevelName() {
		return currentAcademicLevelName;
	}

	public void setCurrentAcademicLevelName(String currentAcademicLevelName) {
		this.currentAcademicLevelName = currentAcademicLevelName;
	}

	public ParentVO getPrimaryParent() {
		return primaryParent;
	}

	public void setPrimaryParent(ParentVO primaryParent) {
		this.primaryParent = primaryParent;
	}
}
