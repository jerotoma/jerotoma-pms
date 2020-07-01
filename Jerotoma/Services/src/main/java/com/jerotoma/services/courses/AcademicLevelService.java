package com.jerotoma.services.courses;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.models.academic.AcademicLevel;
import com.jerotoma.services.BaseService;

public interface AcademicLevelService extends BaseService<AcademicLevel> {
	List<AcademicLevel> getAllAcademicLevels() throws SQLException;

}
