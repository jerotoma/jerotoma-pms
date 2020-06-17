package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Set;

import com.jerotoma.common.constants.AttendanceConstant;

public class ClassAttendanceVO {
	private Integer id;
	private Integer addedBy;
	private Integer teacherId;
	private String fullName;
	
	private Integer academicYearId;
	private String academicYearName;	
	private String yearOfStudy;
	
	private Integer classId;
	private Integer courseId;
	private String courseName;
	
	private Date attendanceDate;
	
	private Set<StudentAttendanceVO> studentAttendances;
		
	private Date createdOn;	
	private Date updatedOn;
		
	public ClassAttendanceVO(ResultSet rs) throws SQLException {
		this.id = rs.getInt(AttendanceConstant.ID);
		this.addedBy = rs.getInt(AttendanceConstant.ADDED_BY);
		this.attendanceDate = rs.getDate(AttendanceConstant.ATTENDANCE_DATE);		
		
		this.teacherId = rs.getInt("teacherId");	
		this.fullName = rs.getString("firstName") + " " + rs.getString("lastName");
		
		this.academicYearId = rs.getInt("academicYearId");
		this.academicYearName = rs.getString("academicYearName");
		this.yearOfStudy = rs.getString("yearOfStudy");
		
		this.classId = rs.getInt("classId");
		this.courseName = rs.getString("courseName");
		this.courseId = rs.getInt("courseId");
		
		this.addedBy = rs.getInt(AttendanceConstant.ADDED_BY);
		this.attendanceDate = rs.getDate(AttendanceConstant.ATTENDANCE_DATE);	
		
		
		this.updatedOn = rs.getDate(AttendanceConstant.UPDATED_ON);
		this.createdOn = rs.getDate(AttendanceConstant.CREATED_ON);
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Date getAttendanceDate() {
		return attendanceDate;
	}
	
	public void setAttendanceDate(Date attendanceDate) {
		this.attendanceDate = attendanceDate;
	}
	
	public Integer getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(Integer addedBy) {
		this.addedBy = addedBy;
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

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	public Set<StudentAttendanceVO> getStudentAttendances() {
		return studentAttendances;
	}

	public void setStudentAttendances(Set<StudentAttendanceVO> studentAttendances) {
		this.studentAttendances = studentAttendances;
	}
}
