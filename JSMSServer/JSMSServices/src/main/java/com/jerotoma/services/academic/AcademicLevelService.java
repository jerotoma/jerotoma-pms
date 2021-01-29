package com.jerotoma.services.academic;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.models.academic.AcademicLevel;
import com.jerotoma.services.BaseService;

public interface AcademicLevelService extends BaseService<AcademicLevel> {
	List<AcademicLevel> getAllAcademicLevels() throws SQLException;

	AcademicLevel addStreamsToAcademicLevel(Integer accademicLevelId, List<Integer> streamIds) throws SQLException;

	boolean deleteStreamsFromAcademicLevel(Integer entityId, Integer streamId) throws SQLException;
}
