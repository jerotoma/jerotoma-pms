package com.jerotoma.database.dao.attendances;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.models.attendances.AttendanceStatus;
import com.jerotoma.database.dao.BaseDao;

public interface AttendanceStatusDao extends BaseDao<AttendanceStatus>  {

	List<AttendanceStatus> getAll() throws SQLException;

}
