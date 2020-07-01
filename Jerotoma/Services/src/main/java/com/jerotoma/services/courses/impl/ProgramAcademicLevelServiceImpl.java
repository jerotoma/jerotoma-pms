package com.jerotoma.services.courses.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.academic.ProgramAcademicLevel;
import com.jerotoma.database.dao.courses.ProgramAcademicLevelDao;
import com.jerotoma.services.courses.ProgramAcademicLevelService;

@Service
@Transactional
public class ProgramAcademicLevelServiceImpl implements ProgramAcademicLevelService {
	
	@Autowired ProgramAcademicLevelDao programAcademicLevelDao;

	@Override
	public ProgramAcademicLevel findObject(Integer primaryKey) throws SQLException {
		return programAcademicLevelDao.findObject(primaryKey);
	}

	@Override
	public ProgramAcademicLevel findObjectUniqueKey(String uniqueKey) throws SQLException {
		return programAcademicLevelDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public ProgramAcademicLevel createObject(ProgramAcademicLevel object) throws SQLException {
		return programAcademicLevelDao.createObject(object);
	}

	@Override
	public ProgramAcademicLevel updateObject(ProgramAcademicLevel object) throws SQLException {
		return programAcademicLevelDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(ProgramAcademicLevel object) throws SQLException {
		return programAcademicLevelDao.deleteObject(object);
	}

	@Override
	public List<ProgramAcademicLevel> loadList(QueryParam queryParam) throws SQLException {
		return programAcademicLevelDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return programAcademicLevelDao.loadMapList(queryParam);
	}

	@Override
	public ProgramAcademicLevel findProgramAcademicLevelByIDs(Integer programId, Integer academicLevelId)
			throws SQLException {
		return programAcademicLevelDao.findProgramAcademicLevelByIDs(programId, academicLevelId);
	}

}
