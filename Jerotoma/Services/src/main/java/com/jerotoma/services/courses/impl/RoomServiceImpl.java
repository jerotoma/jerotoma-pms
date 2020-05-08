package com.jerotoma.services.courses.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.academic.Room;
import com.jerotoma.database.dao.courses.RoomDao;
import com.jerotoma.services.courses.RoomService;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {
	
	@Autowired RoomDao roomDao;

	@Override
	public Room findObject(Integer primaryKey) throws SQLException {
		return roomDao.findObject(primaryKey);
	}

	@Override
	public Room findObjectUniqueKey(String uniqueKey) throws SQLException {
		return roomDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public Room createObject(Room object) throws SQLException {
		return roomDao.createObject(object);
	}

	@Override
	public Room updateObject(Room object) throws SQLException {
		return roomDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(Room object) throws SQLException {
		return roomDao.deleteObject(object);
	}

	@Override
	public List<Room> loadList(QueryParam queryParam) throws SQLException {
		return roomDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return roomDao.loadMapList(queryParam);
	}

}
