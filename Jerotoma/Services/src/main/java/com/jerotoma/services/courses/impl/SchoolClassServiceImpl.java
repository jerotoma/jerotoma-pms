package com.jerotoma.services.courses.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.academic.SchoolClass;
import com.jerotoma.database.dao.courses.SchoolClassDao;
import com.jerotoma.services.courses.SchoolClassService;

@Service
@Transactional
public class SchoolClassServiceImpl implements SchoolClassService {
	
	@Autowired SchoolClassDao schoolClassDao;

	@Override
	public SchoolClass findObject(Integer primaryKey) throws SQLException {
		return schoolClassDao.findObject(primaryKey);
	}

	@Override
	public SchoolClass findObjectUniqueKey(String uniqueKey) throws SQLException {
		return schoolClassDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public SchoolClass createObject(SchoolClass object) throws SQLException {
		return schoolClassDao.createObject(object);
	}

	@Override
	public SchoolClass updateObject(SchoolClass object) throws SQLException {
		return schoolClassDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(SchoolClass object) throws SQLException {
		return schoolClassDao.deleteObject(object);
	}

	@Override
	public List<SchoolClass> loadList(QueryParam queryParam) throws SQLException {
		return schoolClassDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return schoolClassDao.loadMapList(queryParam);
	}

}
