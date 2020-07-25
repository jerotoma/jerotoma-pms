package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AttendanceReport {
	
	private Integer teacherId;
	private String teacherFullName;
	
	private Integer studentId;
	private String studentFullName;
	
	private Integer academicYearId;
	private String academicYearName;	
	private String yearOfStudy;
	
	private Integer classId;
	private Integer courseId;
	private String courseName;
	
	public AttendanceReport(ResultSet rs) throws SQLException {
		
		this.teacherId = rs.getInt("teacherId");	
		this.teacherFullName = rs.getString("tfirstName") + " " + rs.getString("tlastName");
		
		this.studentId = rs.getInt("teacherId");	
		this.studentFullName = rs.getString("sfirstName") + " " + rs.getString("slastName");
		
		this.academicYearId = rs.getInt("academicYearId");
		this.academicYearName = rs.getString("academicYearName");
		this.yearOfStudy = rs.getString("yearOfStudy");
		
		this.classId = rs.getInt("classId");
		this.courseName = rs.getString("courseName");
		this.courseId = rs.getInt("courseId");		
	}

	public Integer getTeacherId() {
		return teacherId;
	}
	
	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}
	
	public String getTeacherFullName() {
		return teacherFullName;
	}
	
	public void setTeacherFullName(String teacherFullName) {
		this.teacherFullName = teacherFullName;
	}
	
	public Integer getStudentId() {
		return studentId;
	}
	
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	
	public String getStudentFullName() {
		return studentFullName;
	}
	
	public void setStudentFullName(String studentFullName) {
		this.studentFullName = studentFullName;
	}
	
	public Integer getAcademicYearId() {
		return academicYearId;
	}
	
	public void setAcademicYearId(Integer academicYearId) {
		this.academicYearId = academicYearId;
	}
	
	public String getAcademicYearName() {
		return academicYearName;
	}
	
	public void setAcademicYearName(String academicYearName) {
		this.academicYearName = academicYearName;
	}
	
	public String getYearOfStudy() {
		return yearOfStudy;
	}
	
	public void setYearOfStudy(String yearOfStudy) {
		this.yearOfStudy = yearOfStudy;
	}
	
	public Integer getClassId() {
		return classId;
	}
	
	public void setClassId(Integer classId) {
		this.classId = classId;
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
}
