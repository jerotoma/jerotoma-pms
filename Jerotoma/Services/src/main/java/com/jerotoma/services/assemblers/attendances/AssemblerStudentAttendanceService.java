package com.jerotoma.services.assemblers.attendances;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.viewobjects.StudentAttendanceVO;
import com.jerotoma.services.AssemblerService;

public interface AssemblerStudentAttendanceService extends AssemblerService<StudentAttendanceVO> {
	List<StudentAttendanceVO> loadStudentClassAttendanceReportsByStudentID(Integer studentId, Integer classId) throws SQLException;

}
