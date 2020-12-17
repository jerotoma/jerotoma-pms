package com.jerotoma.services.users.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.users.CompletedAcademicLevel;
import com.jerotoma.database.dao.users.StudentCompletedAcademicLevelDao;
import com.jerotoma.services.users.StudentCompletedAcademicLevelService;

@Transactional
@Service
public class StudentCompletedAcademicLevelServiceImpl implements StudentCompletedAcademicLevelService {
	
	@Autowired StudentCompletedAcademicLevelDao studentCompletedAcademicLevelDao;

	@Override
	public CompletedAcademicLevel findObject(Integer primaryKey) throws SQLException {		
		return studentCompletedAcademicLevelDao.findObject(primaryKey);
	}

	@Override
	public CompletedAcademicLevel findObjectUniqueKey(String uniqueKey) throws SQLException {
		return studentCompletedAcademicLevelDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public CompletedAcademicLevel createObject(CompletedAcademicLevel object) throws SQLException {
		return studentCompletedAcademicLevelDao.createObject(object);
	}

	@Override
	public CompletedAcademicLevel updateObject(CompletedAcademicLevel object) throws SQLException {
		return studentCompletedAcademicLevelDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(CompletedAcademicLevel object) throws SQLException {
		return studentCompletedAcademicLevelDao.deleteObject(object);
	}

	@Override
	public List<CompletedAcademicLevel> loadList(QueryParam queryParam) throws SQLException {
		return studentCompletedAcademicLevelDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return studentCompletedAcademicLevelDao.loadMapList(queryParam);
	}
}
