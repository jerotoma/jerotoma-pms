package com.jerotoma.database.assemblers.dao.attendances;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.models.attendances.AttendanceStatus;
import com.jerotoma.database.assemblers.AssemblerDao;

public interface AssemblerAttendanceStatusDao extends AssemblerDao<AttendanceStatus> {

	public List<AttendanceStatus> loadAllList() throws SQLException;

}
