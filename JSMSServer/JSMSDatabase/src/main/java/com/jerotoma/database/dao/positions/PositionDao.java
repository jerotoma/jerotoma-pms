package com.jerotoma.database.dao.positions;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.models.positions.Position;
import com.jerotoma.database.dao.BaseDao;

public interface PositionDao extends BaseDao<Position>  {
	public List<Position> loadList() throws SQLException;
}
