package com.jerotoma.services.courses.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.academic.Class;
import com.jerotoma.database.dao.courses.ClassDao;
import com.jerotoma.services.courses.ClassService;

@Service
@Transactional
public class ClassServiceImpl implements ClassService{
	
	@Autowired ClassDao ClassDao;

	@Override
	public Class findObject(Integer primaryKey) throws SQLException {
		return ClassDao.findObject(primaryKey);
	}

	@Override
	public Class findObjectUniqueKey(String uniqueKey) throws SQLException {
		return ClassDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public Class createObject(Class object) throws SQLException {
		return ClassDao.createObject(object);
	}

	@Override
	public Class updateObject(Class object) throws SQLException {
		return ClassDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(Class object) throws SQLException {
		return ClassDao.deleteObject(object);
	}

	@Override
	public List<Class> loadList(QueryParam queryParam) throws SQLException {
		return ClassDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return ClassDao.loadMapList(queryParam);
	}

}
