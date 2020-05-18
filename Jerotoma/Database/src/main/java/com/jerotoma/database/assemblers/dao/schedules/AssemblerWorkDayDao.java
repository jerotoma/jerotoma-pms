package com.jerotoma.database.assemblers.dao.schedules;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.viewobjects.WorkDayVO;
import com.jerotoma.database.assemblers.AssemblerDao;

public interface AssemblerWorkDayDao extends AssemblerDao<WorkDayVO> {
	List<WorkDayVO> findAllWorkDays() throws SQLException;
}