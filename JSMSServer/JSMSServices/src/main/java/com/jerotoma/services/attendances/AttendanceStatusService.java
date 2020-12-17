package com.jerotoma.services.attendances;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.models.attendances.AttendanceStatus;
import com.jerotoma.services.BaseService;

public interface AttendanceStatusService extends BaseService<AttendanceStatus> {
	public List<AttendanceStatus> getAll() throws SQLException;

}
