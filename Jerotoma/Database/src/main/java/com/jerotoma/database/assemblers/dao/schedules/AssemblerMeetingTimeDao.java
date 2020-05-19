package com.jerotoma.database.assemblers.dao.schedules;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

import com.jerotoma.common.viewobjects.MeetingTimeVO;
import com.jerotoma.database.assemblers.AssemblerDao;

public interface AssemblerMeetingTimeDao extends AssemblerDao<MeetingTimeVO> {
	List<MeetingTimeVO> findAllMeetingTimes() throws SQLException;
	public List<MeetingTimeVO> findAllMeetingTimesByWorkDay(Integer workDayId) throws SQLException;
	public List<MeetingTimeVO> findAllOverapsMeetingTimesByWorkDay(Integer workDayId, LocalTime startTime,
			LocalTime endTime) throws SQLException;
}
