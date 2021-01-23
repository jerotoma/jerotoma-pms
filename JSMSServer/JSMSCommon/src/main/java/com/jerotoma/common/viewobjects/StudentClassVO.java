package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.jerotoma.common.constants.CompletionStatus;
import com.jerotoma.common.constants.StudentConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.models.academic.ScoreStanding;

public class StudentClassVO {
	
	private Integer id;
	
	private Integer classId;
	
	private Integer courseId;
	
	private String courseName;
	
	private Integer studentAcademicLevelId;
	
	private StudentVO student;
		
	private Integer statusId;
	
	private String status;
	
	private Double score;
	
	private ScoreStanding scoreStanding;
	
	private Integer updatedBy;	
	
	private Date createdOn;
	
	private Date updatedOn;
			
	public StudentClassVO(ResultSet rs) throws SQLException {
		this.id = rs.getInt(StudentConstant.Class.ID);		
		this.classId = rs.getInt(StudentConstant.Class.CLASS_ID);
		this.courseId = rs.getInt(StudentConstant.Class.COURSE_ID);
		this.courseName = rs.getString(StudentConstant.Class.COURSE_NAME);
		this.studentAcademicLevelId = rs.getInt(StudentConstant.Class.STUDENT_ACADEMIC_LEVEL_ID);
		this.statusId = rs.getInt(StudentConstant.Class.COMPLETION_STATUS_ID);
		this.score = rs.getDouble(StudentConstant.Class.SCORE);
		CompletionStatus completionStatus = CompletionStatus.getCompletionStatusfromID(statusId);		
		this.status = completionStatus != null ? completionStatus.getDescription() : null;
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

	public Integer getClassId() {
		return classId;
	}
	
	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
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

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public ScoreStanding getScoreStanding() {
		return scoreStanding;
	}

	public void setScoreStanding(ScoreStanding scoreStanding) {
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
