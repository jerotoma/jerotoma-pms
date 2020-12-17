package com.jerotoma.services.assemblers.attendances.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.viewobjects.AttendanceReport;
import com.jerotoma.database.assemblers.dao.attendances.AssemblerAttendanceReportDao;
import com.jerotoma.services.assemblers.attendances.AssemblerAttendanceReportService;

@Service
public class AssemblerAttendanceReportServiceImpl implements AssemblerAttendanceReportService  {

	@Autowired AssemblerAttendanceReportDao assemblerAttendanceReportDao;
	@Override
	public List<AttendanceReport> loadAttendanceReportDetailsByStudentID(Integer studentId, Integer academicLevelId)
			throws SQLException {
		return assemblerAttendanceReportDao.loadAttendanceReportDetailsByStudentID(studentId, academicLevelId);
	}

	@Override
	public List<AttendanceReport> loadAttendanceReportsByStudentID(Integer studentId, Integer academicLevelId,
			Integer attendanceStatusID) throws SQLException {
		return assemblerAttendanceReportDao.loadAttendanceReportsByStudentID(studentId, academicLevelId, attendanceStatusID);
	}

}
