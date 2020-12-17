package com.jerotoma.services.academic.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.users.CompletedAcademicLevel;
import com.jerotoma.database.dao.academic.CompletedAcademicLevelDao;
import com.jerotoma.services.academic.CompletedAcademicLevelService;

@Service
@Transactional
public class CompletedAcademicLevelServiceImpl implements CompletedAcademicLevelService {
	
	@Autowired CompletedAcademicLevelDao completedAcademicLevelDao;

	@Override
	public CompletedAcademicLevel findObject(Integer primaryKey) throws SQLException {
		return completedAcademicLevelDao.findObject(primaryKey);
	}

	@Override
	public CompletedAcademicLevel findObjectUniqueKey(String uniqueKey) throws SQLException {
		return completedAcademicLevelDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public CompletedAcademicLevel createObject(CompletedAcademicLevel object) throws SQLException {
		return completedAcademicLevelDao.createObject(object);
	}

	@Override
	public CompletedAcademicLevel updateObject(CompletedAcademicLevel object) throws SQLException {
		return completedAcademicLevelDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(CompletedAcademicLevel object) throws SQLException {
		return completedAcademicLevelDao.deleteObject(object);
	}

	@Override
	public List<CompletedAcademicLevel> loadList(QueryParam queryParam) throws SQLException {
		return completedAcademicLevelDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return completedAcademicLevelDao.loadMapList(queryParam);
	}

}
