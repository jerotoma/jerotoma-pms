package com.jerotoma.services.assemblers.academic.impls;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.viewobjects.MeetingTimeVO;
import com.jerotoma.database.assemblers.dao.schedules.AssemblerMeetingTimeDao;
import com.jerotoma.services.assemblers.academic.AssemblerMeetingTimeService;

@Service
public class AssemblerMeetingTimeServiceImpl implements AssemblerMeetingTimeService {
	
	@Autowired AssemblerMeetingTimeDao assemblerMeetingTimeDao;

	@Override
	public MeetingTimeVO findObject(Integer primaryKey) throws SQLException {
		return assemblerMeetingTimeDao.findObject(primaryKey);
	}

	@Override
	public MeetingTimeVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		return assemblerMeetingTimeDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public List<MeetingTimeVO> loadList(QueryParam queryParam) throws SQLException {
		return assemblerMeetingTimeDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return assemblerMeetingTimeDao.loadMapList(queryParam);
	}

	@Override
	public Long countObject() throws SQLException {
		return assemblerMeetingTimeDao.countObject();
	}

	@Override
	public List<MeetingTimeVO> findAllMeetingTimes() throws SQLException {
		return assemblerMeetingTimeDao.findAllMeetingTimes();
	}

	@Override
	public List<MeetingTimeVO> findAllMeetingTimesByWorkDay(Integer workDayId) throws SQLException {
		return assemblerMeetingTimeDao.findAllMeetingTimesByWorkDay(workDayId);
	}

	@Override
	public List<MeetingTimeVO> findAllOverapsMeetingTimesByWorkDay(Integer workDayId, LocalTime startTime,
			LocalTime endTime) throws SQLException {
		return assemblerMeetingTimeDao.findAllOverapsMeetingTimesByWorkDay(workDayId, startTime, endTime);
	}

	
}
