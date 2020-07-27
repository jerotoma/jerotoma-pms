package com.jerotoma.database.assemblers.dao.attendances;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.viewobjects.AttendanceReport;

public interface AssemblerAttendanceReportDao {
	
	public List<AttendanceReport> loadAttendanceReportDetailsByStudentID(Integer studentId, Integer academicLevelId) throws SQLException;

	List<AttendanceReport> loadAttendanceReportsByStudentID(Integer studentId, Integer academicLevelId,	Integer attendanceStatusID) throws SQLException;

}
