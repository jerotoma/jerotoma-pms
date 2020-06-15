package com.jerotoma.services.assemblers.attendances;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.viewobjects.ClassAttendanceVO;
import com.jerotoma.services.AssemblerService;

public interface AssemblerClassAttendanceService extends AssemblerService<ClassAttendanceVO>{
	public List<ClassAttendanceVO> getAll() throws SQLException;

}
