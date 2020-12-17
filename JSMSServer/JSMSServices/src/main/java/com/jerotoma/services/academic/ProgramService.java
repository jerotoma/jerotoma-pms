package com.jerotoma.services.academic;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.models.academic.Program;
import com.jerotoma.services.BaseService;

public interface ProgramService extends BaseService<Program>{	
	
	public Program createProgramAndAssociateAcademicLevels(Program program, List<Integer> academicLevelIDs) throws SQLException;
	public Program updateProgramAndAssociateAcademicLevels(Program program, List<Integer> academicLevelIDs) throws SQLException;
}
