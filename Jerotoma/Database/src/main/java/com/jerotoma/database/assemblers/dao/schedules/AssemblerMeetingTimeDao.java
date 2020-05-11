package com.jerotoma.database.assemblers.dao.schedules;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.viewobjects.MeetingTimeVO;
import com.jerotoma.database.assemblers.AssemblerDao;

public interface AssemblerMeetingTimeDao extends AssemblerDao<MeetingTimeVO> {
	List<MeetingTimeVO> findAllMeetingTimes() throws SQLException;
}
