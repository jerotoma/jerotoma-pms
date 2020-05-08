package com.jerotoma.services.schedules;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.schedules.MeetingTime;
import com.jerotoma.database.dao.schedules.MeetingTimeDao;

@Service
@Transactional
public class MeetingTimeServiceImpl implements MeetingTimeService {
	
	@Autowired MeetingTimeDao  meetingTimeDao;
	
	@Override
	public MeetingTime findObject(Integer primaryKey) throws SQLException {
		return meetingTimeDao.findObject(primaryKey);
	}

	@Override
	public MeetingTime findObjectUniqueKey(String uniqueKey) throws SQLException {
		return meetingTimeDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public MeetingTime createObject(MeetingTime object) throws SQLException {
		return meetingTimeDao.createObject(object);
	}

	@Override
	public MeetingTime updateObject(MeetingTime object) throws SQLException {
		return meetingTimeDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(MeetingTime object) throws SQLException {
		return meetingTimeDao.deleteObject(object);
	}

	@Override
	public List<MeetingTime> loadList(QueryParam queryParam) throws SQLException {
		return meetingTimeDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return meetingTimeDao.loadMapList(queryParam);
	}

}
