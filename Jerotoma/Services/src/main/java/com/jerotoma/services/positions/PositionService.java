package com.jerotoma.services.positions;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.models.positions.Position;
import com.jerotoma.services.BaseService;

public interface PositionService extends BaseService<Position> {
	public List<Position> loadList() throws SQLException;

	public Long countObject() throws SQLException;

}
