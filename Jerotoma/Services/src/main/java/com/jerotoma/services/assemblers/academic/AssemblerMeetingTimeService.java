package com.jerotoma.services.assemblers.academic;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

import com.jerotoma.common.viewobjects.MeetingTimeVO;
import com.jerotoma.services.AssemblerService;

public interface AssemblerMeetingTimeService extends AssemblerService<MeetingTimeVO> {
	public List<MeetingTimeVO> findAllMeetingTimes() throws SQLException;
	public List<MeetingTimeVO> findAllMeetingTimesByWorkDay(Integer workDayID) throws SQLException;
	public List<MeetingTimeVO> findAllOverapsMeetingTimesByWorkDay(Integer dayId, LocalTime startTime, LocalTime endTime)  throws SQLException;

}
