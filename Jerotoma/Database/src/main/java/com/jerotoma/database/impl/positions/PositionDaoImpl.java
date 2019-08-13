package com.jerotoma.database.impl.positions;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.PositionConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.models.positions.Position;
import com.jerotoma.database.dao.DaoUtil;
import com.jerotoma.database.dao.positions.PositionDao;

@Repository
@Transactional
public class PositionDaoImpl implements PositionDao{

	@PersistenceContext 
	private EntityManager entityManager;
	
	@Override
	public Position findObject(Integer primaryKey) throws SQLException {		
		return entityManager.find(Position.class, primaryKey);
	}

	@Override
	public Position findObjectUniqueKey(String uniqueKey) throws SQLException {
		return entityManager.createQuery("FROM Position WHERE code := ?", Position.class).setParameter("code", uniqueKey).getSingleResult();
	}

	@Override
	public Position createObject(Position object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId().intValue());
	}

	@Override
	public Boolean deleteObject(Position object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
		return true;
	}

	@Override
	public List<Position> loadList(QueryParam queryParam) throws SQLException {
		Long countResults = countObject();
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		return entityManager.createQuery("FROM Position ", Position.class)
				.setFirstResult(offset)
				.setMaxResults(limit)
				.getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		Long countResults = countObject();
		int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		List<Position> positions =  entityManager.createQuery("FROM Position ", Position.class)
				.setFirstResult(offset)
				.setMaxResults(limit)
				.getResultList();
		
		map.put(PositionConstant.POSITIONS, positions);
		map.put(PositionConstant.POSITION_COUNTS, countResults);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		map.put(SystemConstant.SUCCESS, true);
		
		return map;
	}

	@Override
	public Position updateObject(Position object) throws SQLException {
		entityManager.merge(object);
		return findObject(object.getId().intValue());
	}

	@Override
	public Long countObject() throws SQLException {
		return entityManager.createQuery("SELECT count(*) FROM Position ", Long.class)
				.getSingleResult();
	}


}
