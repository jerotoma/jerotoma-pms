package com.jerotoma.common.viewobjects;

import java.util.List;

public class StudentAcademicLevelsProgress {
	private Integer studentID;
	private String studentName;
	private Integer completedLevels;
	private Integer unCompletedLevels;
	private Integer requiredLevels;
	private List<AcademicLevelProgress> academicLevelProgresses;
	
	public StudentAcademicLevelsProgress() {}

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

	public List<AcademicLevelProgress> getAcademicLevelProgresses() {
		return academicLevelProgresses;
	}

	public void setAcademicLevelProgresses(List<AcademicLevelProgress> academicLevelProgresses) {
		this.academicLevelProgresses = academicLevelProgresses;
	}	
}
