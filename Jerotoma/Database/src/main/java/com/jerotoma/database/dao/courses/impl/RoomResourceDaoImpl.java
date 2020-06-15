package com.jerotoma.database.dao.courses.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.ClassRoomResourceConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.models.academic.RoomResource;
import com.jerotoma.database.dao.DaoUtil;
import com.jerotoma.database.dao.courses.RoomResourceDao;

@Repository
@Transactional
public class RoomResourceDaoImpl implements RoomResourceDao {

	@PersistenceContext 
	private EntityManager entityManager;
	
	@Override
	public RoomResource findObject(Integer primaryKey) throws SQLException {
		
		return entityManager.find(RoomResource.class, primaryKey);
	}

	@Override
	public RoomResource findObjectUniqueKey(String uniqueKey) throws SQLException {
		String  query = "FROM RoomResource WHERE code =:code ";
		
		return entityManager.createQuery(query, RoomResource.class)
				.setParameter("code", uniqueKey)
				.getSingleResult();
	}

	@Override
	public RoomResource createObject(RoomResource object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId());
	}

	@Override
	public RoomResource updateObject(RoomResource object) throws SQLException {
		return entityManager.merge(object);
	}

	@Override
	public Boolean deleteObject(RoomResource object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
		return true;
	}

	@Override
	public List<RoomResource> loadList(QueryParam queryParam) throws SQLException {
		return entityManager.createQuery("FROM RoomResource", RoomResource.class)				
				.getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		
		Long countResults = countObject();
		int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		List<RoomResource> classRoomResource = entityManager.createQuery("FROM RoomResource", RoomResource.class)
				.setMaxResults(limit)
				.setFirstResult(offset)
				.getResultList();
		map.put(ClassRoomResourceConstant.CLASS_ROOM_RESOURCES, classRoomResource);
		map.put(SystemConstant.COUNT, countResults);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}

	@Override
	public Long countObject() throws SQLException {
		return entityManager.createQuery("SELECT count(*) FROM RoomResource", Long.class)				
				.getSingleResult();
	}

}
