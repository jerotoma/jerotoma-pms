package com.jerotoma.services.assemblers.academic.impls;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.viewobjects.AcademicLevelVO;
import com.jerotoma.database.assemblers.dao.academic.AssemblerAcademicLevelDao;
import com.jerotoma.services.assemblers.academic.AssemblerAcademicLevelService;

@Service
public class AssemblerAcademicLevelServiceImpl implements AssemblerAcademicLevelService {
	
	@Autowired AssemblerAcademicLevelDao assemblerAcademicLevelDao;

	@Override
	public AcademicLevelVO findObject(Integer primaryKey) throws SQLException {
		return assemblerAcademicLevelDao.findObject(primaryKey);
	}

	@Override
	public AcademicLevelVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		return assemblerAcademicLevelDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public List<AcademicLevelVO> loadList() throws SQLException {
		return assemblerAcademicLevelDao.loadList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return assemblerAcademicLevelDao.loadMapList(queryParam);
	}

	@Override
	public Long countObject() throws SQLException {
		return assemblerAcademicLevelDao.countObject();
	}

	@Override
	public List<AcademicLevelVO> getAllAcademicLevels() throws SQLException {
		return assemblerAcademicLevelDao.getAllAcademicLevel();
	}

	@Override
	public List<AcademicLevelVO> loadUnAddedAcademicLevelByProgram(Integer programId) throws SQLException {
		return assemblerAcademicLevelDao.loadUnAddedAcademicLevelByProgram(programId);
	}

	@Override
	public List<AcademicLevelVO> loadAcademicLevelByProgram(Integer programId) throws SQLException {
		return assemblerAcademicLevelDao.loadAcademicLevelByProgram(programId);
	}
}
