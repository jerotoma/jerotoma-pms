package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.jerotoma.common.constants.AttendanceConstant;

public class StudentAttendanceVO {
	
	private Integer id;
	private Integer studentId;
	private String fullName;	
	private Integer classAttendanceId;
	private Integer statusId;	
	private String statusName;
	
	private Integer academicYearId;
	private String academicYearName;	
	private String yearOfStudy;
	
	private Integer classId;
	private Integer courseId;
	private String courseName;
	
	private Integer addedBy;
	
	private Date attendanceDate;	
	private Date createdOn;	
	private Date updatedOn;

	public StudentAttendanceVO(ResultSet rs) throws SQLException {
		this.id = rs.getInt(AttendanceConstant.ID);
		if (rs.wasNull()) {
			this.id = null;
		}
		this.classAttendanceId = rs.getInt(AttendanceConstant.CLASS_ATTENDANCE_ID);
		this.statusId = rs.getInt(AttendanceConstant.ATTENDANCE_STATUS_ID);
		if (rs.wasNull()) {
			this.statusId = null;
		}
		this.statusName = rs.getString("statusName");
		
		this.studentId = rs.getInt(AttendanceConstant.STUDENT_ID);		
		this.fullName = rs.getString("firstName") + " " + rs.getString("lastName");
		
		this.academicYearId = rs.getInt("academicYearId");
		this.academicYearName = rs.getString("academicYearName");
		this.yearOfStudy = rs.getString("yearOfStudy");
		
		this.classId = rs.getInt("classId");
		this.courseName = rs.getString("courseName");
		this.courseId = rs.getInt("courseId");
		
		this.addedBy = rs.getInt(AttendanceConstant.ADDED_BY);
		if (rs.wasNull()) {
			this.addedBy = null;
		}
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

	public Integer getClassAttendanceId() {
		return classAttendanceId;
	}

	public void setClassAttendanceId(Integer classAttendenceId) {
		this.classAttendanceId = classAttendenceId;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	public Date getAttendanceDate() {
		return attendanceDate;
	}

	public void setAttendanceDate(Date attendanceDate) {
		this.attendanceDate = attendanceDate;
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
	
	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
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

	public Integer getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(Integer addedBy) {
		this.addedBy = addedBy;
	}

	@Override
	public int hashCode() {		
		int result = 0;
		if (id != null) {
			result = (int) (id / 11) * 34 * 12;
		}
		return result ;
	}

	@Override
	public boolean equals(Object obj) {
		// null check
		if (obj == null) {
			return false;
		}
		 
		// this instance check
		if (this == obj) {
			return true;
		}
 
		// instanceof Check and actual id check
		if (obj instanceof StudentAttendanceVO) {
			StudentAttendanceVO studentAttendance =	(StudentAttendanceVO)obj;			
			return this.id == studentAttendance.id;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return super.toString();
	}	
}
