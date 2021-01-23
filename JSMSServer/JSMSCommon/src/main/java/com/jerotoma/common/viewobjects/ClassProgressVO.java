package com.jerotoma.common.viewobjects;

import java.util.List;

public class ClassProgressVO {
	
	private AcademicYearVO academicYear;
	
	private  AcademicLevelVO academicLevel;
	
	private List<StudentClassVO> studentClasses;	

	public ClassProgressVO(AcademicYearVO academicYear, AcademicLevelVO academicLevel,
			List<StudentClassVO> studentClasses) {		
		this.academicYear = academicYear;
		this.academicLevel = academicLevel;
		this.studentClasses = studentClasses;
	}

	public AcademicYearVO getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(AcademicYearVO academicYear) {
		this.academicYear = academicYear;
	}

	public AcademicLevelVO getAcademicLevel() {
		return academicLevel;
	}

	public void setAcademicLevel(AcademicLevelVO academicLevel) {
		this.academicLevel = academicLevel;
	}

	public List<StudentClassVO> getStudentClasses() {
		return studentClasses;
	}

	public void setStudentClasses(List<StudentClassVO> studentClasses) {
		this.studentClasses = studentClasses;
	}
	
	

}
