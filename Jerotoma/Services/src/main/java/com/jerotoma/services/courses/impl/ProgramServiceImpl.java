package com.jerotoma.services.courses.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.academic.Program;
import com.jerotoma.database.dao.courses.ProgramDao;
import com.jerotoma.services.courses.ProgramService;


@Transactional
@Service
public class ProgramServiceImpl implements ProgramService {
	
	@Autowired ProgramDao programDao;

	@Override
	public Program findObject(Integer primaryKey) throws SQLException {
		return programDao.findObject(primaryKey);
	}

	@Override
	public Program findObjectUniqueKey(String uniqueKey) throws SQLException {
		return programDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public Program createObject(Program object) throws SQLException {
		return programDao.createObject(object);
	}

	@Override
	public Program updateObject(Program object) throws SQLException {
		return programDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(Program object) throws SQLException {
		return programDao.deleteObject(object);
	}

	@Override
	public List<Program> loadList(QueryParam queryParam) throws SQLException {
		return programDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return programDao.loadMapList(queryParam);
	}
}
