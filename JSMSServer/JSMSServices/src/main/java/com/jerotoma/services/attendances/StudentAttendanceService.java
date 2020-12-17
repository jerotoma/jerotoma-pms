package com.jerotoma.services.attendances;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.models.attendances.StudentAttendance;
import com.jerotoma.services.BaseService;

public interface StudentAttendanceService extends BaseService<StudentAttendance> {
	public List<StudentAttendance> createBatch(List<StudentAttendance> studentAttendances) throws SQLException;
	public List<StudentAttendance> updateBatch(List<StudentAttendance> studentAttendances) throws SQLException;
	public StudentAttendance getStudentAttendanceByStudentIdAndClassAttendanceId(Integer studentId, Integer classAttendanceId) throws SQLException;

}
