package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.jerotoma.common.constants.StudentConstant;
import com.jerotoma.common.constants.SystemConstant;

public class StudentClassVO {
	
	private Integer id;
	
	private Integer studentAcademicLevelId;
	
	private Integer classId;
	
	private Integer completionStatusId;
	
	private Integer updatedBy;
	
	private Date createdOn;
	
	private Date updatedOn;
	
	public StudentClassVO(ResultSet rs) throws SQLException {
		this.id = rs.getInt(StudentConstant.Class.ID);
		this.studentAcademicLevelId = rs.getInt(StudentConstant.Class.STUDENT_ACADEMIC_LEVEL_ID);
		this.classId = rs.getInt(StudentConstant.Class.JCLASS_ID);
		this.completionStatusId = rs.getInt(StudentConstant.Class.COMPLETION_STATUS_ID);
		this.updatedBy = rs.getInt(SystemConstant.UPDATED_BY);
		this.updatedOn = rs.getDate(StudentConstant.Class.UPDATED_ON);
		this.createdOn = rs.getDate(StudentConstant.Class.CREATED_ON);
		
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getStudentAcademicLevelId() {
		return studentAcademicLevelId;
	}

	public void setStudentAcademicLevelId(Integer studentAcademicLevelId) {
		this.studentAcademicLevelId = studentAcademicLevelId;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public Integer getCompletionStatusId() {
		return completionStatusId;
	}

	public void setCompletionStatusId(Integer completionStatusId) {
		this.completionStatusId = completionStatusId;
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
	
	
}
