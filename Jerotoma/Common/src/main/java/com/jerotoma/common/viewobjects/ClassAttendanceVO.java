package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.jerotoma.common.constants.AttendanceConstant;

public class ClassAttendanceVO {
	private Integer id;
	private ClassVO mClass;
	private AcademicYearVO academicYear;
	private Date attendanceDate;
	private Integer addedBy;	
	private Date createdOn;	
	private Date updatedOn;
		
	public ClassAttendanceVO(ResultSet rs) throws SQLException {
		this.id = rs.getInt(AttendanceConstant.ID);
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
	
	public ClassVO getMClass() {
		return mClass;
	}
	
	public void setMClass(ClassVO mClass) {
		this.mClass = mClass;
	}
	
	public AcademicYearVO getAcademicYear() {
		return academicYear;
	}
	
	public void setAcademicYear(AcademicYearVO academicYear) {
		this.academicYear = academicYear;
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
}
