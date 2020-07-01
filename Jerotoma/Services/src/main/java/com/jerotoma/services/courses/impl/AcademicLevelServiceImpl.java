package com.jerotoma.services.courses.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.academic.AcademicLevel;
import com.jerotoma.database.dao.courses.AcademicLevelDao;
import com.jerotoma.services.courses.AcademicLevelService;

@Transactional
@Service
public class AcademicLevelServiceImpl implements AcademicLevelService {
	
	@Autowired AcademicLevelDao academicLevelDao;

	@Override
	public AcademicLevel findObject(Integer primaryKey) throws SQLException {
		return academicLevelDao.findObject(primaryKey);
	}

	@Override
	public AcademicLevel findObjectUniqueKey(String uniqueKey) throws SQLException {
		return academicLevelDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public AcademicLevel createObject(AcademicLevel object) throws SQLException {
		return academicLevelDao.createObject(object);
	}

	@Override
	public AcademicLevel updateObject(AcademicLevel object) throws SQLException {
		return academicLevelDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(AcademicLevel object) throws SQLException {
		return academicLevelDao.deleteObject(object);
	}

	@Override
	public List<AcademicLevel> loadList(QueryParam queryParam) throws SQLException {
		return academicLevelDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return academicLevelDao.loadMapList(queryParam);
	}

	@Override
	public List<AcademicLevel> getAllAcademicLevels() throws SQLException {
		return academicLevelDao.getAllAcademicLevels();
	}

}
