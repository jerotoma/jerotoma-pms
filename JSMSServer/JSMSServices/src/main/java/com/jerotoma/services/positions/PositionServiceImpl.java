package com.jerotoma.services.positions;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.positions.Position;
import com.jerotoma.database.dao.positions.PositionDao;

@Service
@Transactional
public class PositionServiceImpl implements PositionService{

	@Autowired PositionDao positionDao;
	
	@Override
	public Position findObject(Integer primaryKey) throws SQLException {
		return positionDao.findObject(primaryKey);
	}

	@Override
	public Position findObjectUniqueKey(String uniqueKey) throws SQLException {
		return positionDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public Position createObject(Position object) throws SQLException {
		return positionDao.createObject(object);
	}

	@Override
	public Position updateObject(Position object) throws SQLException {
		return positionDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(Position object) throws SQLException {
		return positionDao.deleteObject(object);
	}

	@Override
	public List<Position> loadList(QueryParam queryParam) throws SQLException {
		return positionDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return positionDao.loadMapList(queryParam);
	}

	@Override
	public List<Position> loadList() throws SQLException {
		return positionDao.loadList();
	}

	@Override
	public Long countObject() throws SQLException {
		return positionDao.countObject();
	}

}
