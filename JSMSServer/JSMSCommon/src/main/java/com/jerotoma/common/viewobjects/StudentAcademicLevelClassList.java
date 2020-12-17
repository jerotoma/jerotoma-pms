package com.jerotoma.common.viewobjects;

import java.util.List;

public class StudentAcademicLevelClassList {
	private StudentVO student;
	private AcademicLevelVO academicLevel;	
	private List<ClassVO> classes;
	
	
	public StudentAcademicLevelClassList(StudentVO student, AcademicLevelVO academicLevel, List<ClassVO> classes) {
		super();
		this.student = student;
		this.academicLevel = academicLevel;
		this.classes = classes;
	}
	public StudentVO getStudent() {
		return student;
	}
	public void setStudent(StudentVO student) {
		this.student = student;
	}
	public AcademicLevelVO getAcademicLevel() {
		return academicLevel;
	}
	public void setAcademicLevel(AcademicLevelVO academicLevel) {
		this.academicLevel = academicLevel;
	}
	public List<ClassVO> getClasses() {
		return classes;
	}
	public void setClasses(List<ClassVO> classes) {
		this.classes = classes;
	}
	
	
	
}
