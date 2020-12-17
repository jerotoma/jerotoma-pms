package com.jerotoma.services.assemblers.academic;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.viewobjects.ProgramVO;
import com.jerotoma.services.AssemblerService;

public interface AssemblerProgramService extends AssemblerService<ProgramVO>{

	List<ProgramVO> getAllProgram() throws SQLException;
	
	boolean doesProgramAcademicLevelExist(Integer programId, Integer academicLevelId);

}
