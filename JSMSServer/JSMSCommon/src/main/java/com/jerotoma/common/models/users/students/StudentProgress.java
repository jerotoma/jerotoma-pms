package com.jerotoma.common.models.users.students;

import java.util.List;

public class StudentProgress {
	private Integer studentID;
	private String studentName;
	private Integer completedLevels;
	private Integer unCompletedLevels;
	private Integer requiredLevels;
	private List<StudentAcademicLevelProgress> studentAcademicLevelProgresses;
	
	public StudentProgress() {}

	public Integer getStudentID() {
		return studentID;
	}

	public void setStudentID(Integer studentID) {
		this.studentID = studentID;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public Integer getCompletedLevels() {
		return completedLevels;
	}

	public void setCompletedLevels(Integer completedLevels) {
		this.completedLevels = completedLevels;
	}

	public Integer getUnCompletedLevels() {
		return unCompletedLevels;
	}

	public void setUnCompletedLevels(Integer unCompletedLevels) {
		this.unCompletedLevels = unCompletedLevels;
	}

	public Integer getRequiredLevels() {
		return requiredLevels;
	}

	public void setRequiredLevels(Integer requiredLevels) {
		this.requiredLevels = requiredLevels;
	}

	public List<StudentAcademicLevelProgress> getStudentAcademicLevelProgresses() {
		return studentAcademicLevelProgresses;
	}

	public void setStudentAcademicLevelProgresses(List<StudentAcademicLevelProgress> studentAcademicLevelProgresses) {
		this.studentAcademicLevelProgresses = studentAcademicLevelProgresses;
	}	
}
