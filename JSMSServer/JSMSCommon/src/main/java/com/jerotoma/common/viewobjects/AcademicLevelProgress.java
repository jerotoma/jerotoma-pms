package com.jerotoma.common.viewobjects;

import com.jerotoma.common.constants.CompletionStatus;

public class AcademicLevelProgress {	
	private AcademicLevelVO academicLevel;
	private AcademicYearVO academicYear;
	private CompletionStatus completionStatus;
	private String completionStatusName;
	
	public AcademicLevelVO getAcademicLevel() {
		return academicLevel;
	}
	
	public void setAcademicLevel(AcademicLevelVO academicLevel) {
		this.academicLevel = academicLevel;
	}
	
	public AcademicYearVO getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(AcademicYearVO academicYear) {
		this.academicYear = academicYear;
	}

	public CompletionStatus getCompletionStatus() {
		return completionStatus;
	}
	
	public void setCompletionStatus(CompletionStatus completionStatus) {
		this.completionStatus = completionStatus;
	}
	
	public String getCompletionStatusName() {
		return completionStatusName;
	}
	
	public void setCompletionStatusName(String completionStatusName) {
		this.completionStatusName = completionStatusName;
	}
}
