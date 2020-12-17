package com.jerotoma.services.academic;

import java.sql.SQLException;

import com.jerotoma.common.models.academic.ProgramAcademicLevel;
import com.jerotoma.services.BaseService;

public interface ProgramAcademicLevelService extends BaseService<ProgramAcademicLevel> {

	public ProgramAcademicLevel findProgramAcademicLevelByIDs(Integer programId, Integer academicLevelId) throws SQLException;

}
