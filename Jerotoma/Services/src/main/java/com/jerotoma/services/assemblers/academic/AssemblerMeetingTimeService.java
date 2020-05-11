package com.jerotoma.services.assemblers.academic;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.viewobjects.MeetingTimeVO;
import com.jerotoma.services.AssemblerService;

public interface AssemblerMeetingTimeService extends AssemblerService<MeetingTimeVO> {
	public List<MeetingTimeVO> findAllMeetingTimes() throws SQLException;

}
