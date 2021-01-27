package com.jerotoma.services.positions;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.ErrorMessageConstant;
import com.jerotoma.common.constants.PositionConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.models.positions.Position;
import com.jerotoma.database.dao.positions.PositionDao;
import com.jerotoma.services.utils.ServiceUtil;

@Service
@Transactional
public class PositionServiceImpl implements PositionService{

	@Autowired PositionDao positionDao;
	
	@Override
	public Position findObject(Integer primaryKey) throws SQLException {
		return ServiceUtil.getEntity(positionDao.findById(primaryKey));
	}

	@Override
	public Position findObjectUniqueKey(String uniqueKey) throws SQLException {
		throw new RuntimeException(ErrorMessageConstant.METHOD_NOT_IMPLEMENTED);
	}

	@Override
	public Position createObject(Position object) throws SQLException {
		return positionDao.save(object);
	}

	@Override
	public Position updateObject(Position object) throws SQLException {
		return positionDao.save(object);
	}

	@Override
	public Boolean deleteObject(Position object) throws SQLException {
		positionDao.delete(object);
		return true;
	}

	@Override
	public List<Position> loadList(QueryParam queryParam) throws SQLException {
		if (queryParam == null) {
			return positionDao.findAll();
		}		
		return positionDao.findAll(ServiceUtil.getPageable(queryParam)).toList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();		
		Page<Position> pagePosition = positionDao.findAll(ServiceUtil.getPageable(queryParam));
		map.put(PositionConstant.POSITIONS, pagePosition.getContent());
		map.put(SystemConstant.COUNT, pagePosition.getTotalElements());
		map.put(SystemConstant.PAGE_COUNT, pagePosition.getTotalPages());	
		map.put(SystemConstant.SUCCESS, true);
		return map;	
	}

	@Override
	public List<Position> loadList() throws SQLException {
		return loadList(null);
	}

	@Override
	public Long countObject() throws SQLException {
		return positionDao.count();
	}

}
