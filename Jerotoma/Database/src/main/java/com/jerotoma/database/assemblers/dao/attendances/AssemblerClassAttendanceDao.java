package com.jerotoma.database.assemblers.dao.attendances;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.viewobjects.AttendanceReport;
import com.jerotoma.common.viewobjects.ClassAttendanceVO;
import com.jerotoma.database.assemblers.AssemblerDao;

public interface AssemblerClassAttendanceDao extends AssemblerDao<ClassAttendanceVO> {
	public List<ClassAttendanceVO> getAll() throws SQLException;
	
	public List<AttendanceReport> loadAttendanceReportsByStudentID(Integer studentId, Integer academicLevelId) throws SQLException;

}
