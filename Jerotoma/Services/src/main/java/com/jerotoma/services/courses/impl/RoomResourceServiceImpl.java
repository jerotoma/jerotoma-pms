package com.jerotoma.services.courses.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.academic.RoomResource;
import com.jerotoma.database.dao.courses.RoomResourceDao;
import com.jerotoma.services.courses.RoomResourceService;


@Service
@Transactional
public class RoomResourceServiceImpl implements RoomResourceService {
	
	@Autowired RoomResourceDao classRoomResoureceDao;

	@Override
	public RoomResource findObject(Integer primaryKey) throws SQLException {	
		return classRoomResoureceDao.findObject(primaryKey);
	}

	@Override
	public RoomResource findObjectUniqueKey(String uniqueKey) throws SQLException {
		return classRoomResoureceDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public RoomResource createObject(RoomResource object) throws SQLException {
		return classRoomResoureceDao.createObject(object);
	}

	@Override
	public RoomResource updateObject(RoomResource object) throws SQLException {
		return classRoomResoureceDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(RoomResource object) throws SQLException {
		return classRoomResoureceDao.deleteObject(object);
	}

	@Override
	public List<RoomResource> loadList(QueryParam queryParam) throws SQLException {
		return classRoomResoureceDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return classRoomResoureceDao.loadMapList(queryParam);
	}

}
