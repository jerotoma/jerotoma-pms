package com.jerotoma.database.assemblers.dao.academic;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.viewobjects.ProgramVO;
import com.jerotoma.database.assemblers.AssemblerDao;

public interface AssemblerProgramDao extends AssemblerDao<ProgramVO>{

	List<ProgramVO> getAllProgram() throws SQLException;

	boolean doesProgramAcademicLevelExist(Integer programId, Integer academicLevelId) throws SQLException;

}
