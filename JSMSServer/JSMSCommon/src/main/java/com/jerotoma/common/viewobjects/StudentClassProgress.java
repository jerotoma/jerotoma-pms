package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.jerotoma.common.constants.CompletionStatus;
import com.jerotoma.common.constants.StudentConstant;
import com.jerotoma.common.constants.SystemConstant;

public class StudentClassProgress {
	
	private Integer studentAcademicLevelId;
	
	private StudentVO student;
		
	private Integer statusId;
	
	private String status;
	
	private Integer score;
	
	private String scoreStanding;
	
	private Integer updatedBy;	
	
	private Date createdOn;
	
	private Date updatedOn;
	
	public StudentClassProgress(ResultSet rs) throws SQLException {		
		this.studentAcademicLevelId = rs.getInt(StudentConstant.Class.STUDENT_ACADEMIC_LEVEL_ID);
		this.statusId = rs.getInt(StudentConstant.Class.COMPLETION_STATUS_ID);
		this.score = rs.getInt(StudentConstant.Class.SCORE);
		CompletionStatus completionStatus = CompletionStatus.getCompletionStatusfromID(statusId);		
		this.status = completionStatus != null ? completionStatus.name() : null;
		this.updatedBy = rs.getInt(SystemConstant.UPDATED_BY);
		this.updatedOn = rs.getDate(StudentConstant.Class.UPDATED_ON);
		this.createdOn = rs.getDate(StudentConstant.Class.CREATED_ON);
	}

	public StudentVO getStudent() {
		return student;
	}

	public void setStudent(StudentVO student) {
		this.student = student;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getScoreStanding() {
		return scoreStanding;
	}

	public void setScoreStanding(String scoreStanding) {
		this.scoreStanding = scoreStanding;
	}

	public Integer getStudentAcademicLevelId() {
		return studentAcademicLevelId;
	}

	public void setStudentAcademicLevelId(Integer studentAcademicLevelId) {
		this.studentAcademicLevelId = studentAcademicLevelId;
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
