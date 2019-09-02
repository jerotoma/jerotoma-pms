package com.jerotoma.services.courses.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.academic.ClassRoom;
import com.jerotoma.database.dao.courses.ClassRoomDao;
import com.jerotoma.services.courses.ClassRoomService;

@Service
@Transactional
public class ClassRoomServiceImpl implements ClassRoomService {
	
	@Autowired ClassRoomDao classRoomDao;

	@Override
	public ClassRoom findObject(Integer primaryKey) throws SQLException {
		return classRoomDao.findObject(primaryKey);
	}

	@Override
	public ClassRoom findObjectUniqueKey(String uniqueKey) throws SQLException {
		return classRoomDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public ClassRoom createObject(ClassRoom object) throws SQLException {
		return classRoomDao.createObject(object);
	}

	@Override
	public ClassRoom updateObject(ClassRoom object) throws SQLException {
		return classRoomDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(ClassRoom object) throws SQLException {
		return classRoomDao.deleteObject(object);
	}

	@Override
	public List<ClassRoom> loadList(QueryParam queryParam) throws SQLException {
		return classRoomDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return classRoomDao.loadMapList(queryParam);
	}

}
