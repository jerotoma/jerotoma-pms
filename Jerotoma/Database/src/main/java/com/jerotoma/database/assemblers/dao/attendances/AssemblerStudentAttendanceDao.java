package com.jerotoma.database.assemblers.dao.attendances;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.viewobjects.StudentAttendanceVO;
import com.jerotoma.database.assemblers.AssemblerDao;

public interface AssemblerStudentAttendanceDao extends AssemblerDao<StudentAttendanceVO> {
	List<StudentAttendanceVO> loadStudentClassAttendanceReportsByStudentID(Integer studentId, Integer classId) throws SQLException;
}
