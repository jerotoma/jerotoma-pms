package com.jerotoma.database.dao.academic;

import java.sql.SQLException;

import com.jerotoma.common.models.academic.ProgramAcademicLevel;
import com.jerotoma.database.dao.BaseDao;

public interface ProgramAcademicLevelDao extends BaseDao<ProgramAcademicLevel> {
	public ProgramAcademicLevel findProgramAcademicLevelByIDs(Integer programId, Integer academicLevelId) throws SQLException;

}
