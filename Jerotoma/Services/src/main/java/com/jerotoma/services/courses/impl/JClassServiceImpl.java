package com.jerotoma.services.courses.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.academic.JClass;
import com.jerotoma.database.dao.courses.JClassDao;
import com.jerotoma.services.courses.JClassService;

@Service
@Transactional
public class JClassServiceImpl implements JClassService{
	
	@Autowired JClassDao jClassDao;

	@Override
	public JClass findObject(Integer primaryKey) throws SQLException {
		return jClassDao.findObject(primaryKey);
	}

	@Override
	public JClass findObjectUniqueKey(String uniqueKey) throws SQLException {
		return jClassDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public JClass createObject(JClass object) throws SQLException {
		return jClassDao.createObject(object);
	}

	@Override
	public JClass updateObject(JClass object) throws SQLException {
		return jClassDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(JClass object) throws SQLException {
		return jClassDao.deleteObject(object);
	}

	@Override
	public List<JClass> loadList(QueryParam queryParam) throws SQLException {
		return jClassDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return jClassDao.loadMapList(queryParam);
	}

}
