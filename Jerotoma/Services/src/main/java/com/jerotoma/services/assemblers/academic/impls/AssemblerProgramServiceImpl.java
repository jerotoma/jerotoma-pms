package com.jerotoma.services.assemblers.academic.impls;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.viewobjects.ProgramVO;
import com.jerotoma.database.assemblers.dao.academic.AssemblerProgramDao;
import com.jerotoma.services.assemblers.academic.AssemblerProgramService;


@Service
public class AssemblerProgramServiceImpl implements AssemblerProgramService {
	
	@Autowired AssemblerProgramDao assemblerProgramDao;

	@Override
	public ProgramVO findObject(Integer primaryKey) throws SQLException {
		return assemblerProgramDao.findObject(primaryKey);
	}

	@Override
	public ProgramVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		return assemblerProgramDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public List<ProgramVO> loadList() throws SQLException {
		return assemblerProgramDao.loadList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return assemblerProgramDao.loadMapList(queryParam);
	}

	@Override
	public Long countObject() throws SQLException {
		return assemblerProgramDao.countObject();
	}
	
	@Override
	public List<ProgramVO> getAllProgram() throws SQLException {
		return assemblerProgramDao.getAllProgram();
	}

	@Override
	public boolean doesProgramAcademicLevelExist(Integer programId, Integer academicLevelId) {
		try {
			return assemblerProgramDao.doesProgramAcademicLevelExist(programId, academicLevelId);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return false;
	}
}
