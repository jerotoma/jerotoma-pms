package com.jerotoma.services.academic;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.models.academic.Stream;
import com.jerotoma.services.BaseService;

public interface StreamService extends BaseService<Stream> {
	public List<Stream> getStreamsByAcademicLevelId(Integer entityId) throws SQLException;

}
