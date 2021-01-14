package com.jerotoma.common.viewobjects;

import java.util.List;
import java.util.Objects;

public class StudentAcademicLevelClass {
	private StudentVO student;
	private AcademicYearVO academicYear;
	private AcademicLevelVO academicLevel;	
	private List<ClassVO> classes;
	
	
	public StudentAcademicLevelClass(StudentVO student, AcademicLevelVO academicLevel, AcademicYearVO academicYear, List<ClassVO> classes) {
		super();
		this.student = student;
		this.academicLevel = academicLevel;
		this.academicYear = academicYear;
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
		
	public AcademicYearVO getAcademicYear() {
		return academicYear;
	}
	
	public void setAcademicYear(AcademicYearVO academicYear) {
		this.academicYear = academicYear;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(academicLevel, classes, student);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof StudentAcademicLevelClass)) {
			return false;
		}
		StudentAcademicLevelClass other = (StudentAcademicLevelClass) obj;
		return Objects.equals(academicLevel, other.academicLevel) && Objects.equals(classes, other.classes)
				&& Objects.equals(student, other.student);
	}	
}
