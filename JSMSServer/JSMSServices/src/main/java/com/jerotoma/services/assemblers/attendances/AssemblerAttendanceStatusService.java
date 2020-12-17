package com.jerotoma.services.assemblers.attendances;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.models.attendances.AttendanceStatus;
import com.jerotoma.services.AssemblerService;

public interface AssemblerAttendanceStatusService extends AssemblerService<AttendanceStatus>{
	public List<AttendanceStatus> loadAllList() throws SQLException;

	public AttendanceStatus loadAttendanceStatusMarkedAsPresent() throws SQLException;
}
