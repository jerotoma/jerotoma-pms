package com.jerotoma.services.courses.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.academic.ClassRoomResource;
import com.jerotoma.database.dao.courses.ClassRoomResourceDao;
import com.jerotoma.services.courses.ClassRoomResourceService;


@Service
@Transactional
public class ClassRoomResourceServiceImpl implements ClassRoomResourceService {
	
	@Autowired ClassRoomResourceDao classRoomResoureceDao;

	@Override
	public ClassRoomResource findObject(Integer primaryKey) throws SQLException {	
		return classRoomResoureceDao.findObject(primaryKey);
	}

	@Override
	public ClassRoomResource findObjectUniqueKey(String uniqueKey) throws SQLException {
		return classRoomResoureceDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public ClassRoomResource createObject(ClassRoomResource object) throws SQLException {
		return classRoomResoureceDao.createObject(object);
	}

	@Override
	public ClassRoomResource updateObject(ClassRoomResource object) throws SQLException {
		return classRoomResoureceDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(ClassRoomResource object) throws SQLException {
		return classRoomResoureceDao.deleteObject(object);
	}

	@Override
	public List<ClassRoomResource> loadList(QueryParam queryParam) throws SQLException {
		return classRoomResoureceDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return classRoomResoureceDao.loadMapList(queryParam);
	}

}
