package com.jerotoma.database.dao.courses;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.models.academic.AcademicLevel;
import com.jerotoma.database.dao.BaseDao;

public interface AcademicLevelDao extends BaseDao<AcademicLevel> {
	List<AcademicLevel> getAllAcademicLevels() throws SQLException;

}
