package com.jerotoma.common.models.users.students;

import com.jerotoma.common.constants.CompletionStatus;
import com.jerotoma.common.viewobjects.AcademicLevelVO;
import com.jerotoma.common.viewobjects.AcademicYearVO;

public class StudentAcademicLevelProgress {	
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
