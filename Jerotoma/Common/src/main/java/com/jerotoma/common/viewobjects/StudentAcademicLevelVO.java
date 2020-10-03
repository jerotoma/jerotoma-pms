package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.jerotoma.common.constants.StudentConstant;

public class StudentAcademicLevelVO {
	
	private Integer id;
	
	private Integer classesCount;
	
	private StudentVO student;
	
	private AcademicYearVO academicYear;
	private AcademicLevelVO academicLevel;
	
	private List<ClassVO> jClasses;
	
	private Integer updatedBy;
	
	private Date createdOn;
	
	private Date updatedOn;
	
	public StudentAcademicLevelVO(ResultSet rs) throws SQLException {
		this.id = rs.getInt(StudentConstant.Class.ID);
		this.classesCount = rs.getInt(StudentConstant.Class.CLASSES_COUNT);
		this.updatedOn = rs.getDate(StudentConstant.Class.UPDATED_ON);
		this.createdOn = rs.getDate(StudentConstant.Class.CREATED_ON);
		
	}

	public StudentVO getStudent() {
		return student;
	}

	public void setStudent(StudentVO student) {
		this.student = student;
	}

	public AcademicYearVO getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(AcademicYearVO academicYear) {
		this.academicYear = academicYear;
	}
	
	public List<ClassVO> getjClasses() {
		return jClasses;
	}

	public void setJClasses(List<ClassVO> jClasses) {
		if (jClasses != null) {
			this.classesCount = jClasses.size();
		}
		this.jClasses = jClasses;
	}

	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getClassesCount() {
		return classesCount;
	}

	public void setClassesCount(Integer classesCount) {
		this.classesCount = classesCount;
	}

	public AcademicLevelVO getAcademicLevel() {
		return academicLevel;
	}

	public void setAcademicLevel(AcademicLevelVO academicLevel) {
		this.academicLevel = academicLevel;
	}

	public void setjClasses(List<ClassVO> jClasses) {
		this.jClasses = jClasses;
	}
}
