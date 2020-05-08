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
import com.jerotoma.common.constants.ClassRoomConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.models.academic.Room;
import com.jerotoma.database.dao.DaoUtil;
import com.jerotoma.database.dao.courses.RoomDao;

@Repository
@Transactional
public class RoomDaoImpl implements RoomDao {
	
	@PersistenceContext 
	private EntityManager entityManager;
	
	
	@Override
	public Room findObject(Integer primaryKey) throws SQLException {
		return entityManager.find(Room.class, primaryKey);
	}

	@Override
	public Room findObjectUniqueKey(String uniqueKey) throws SQLException {
		return entityManager.createQuery("FROM Room WHERE code := ?", Room.class).setParameter("code", uniqueKey).getSingleResult();
	}

	@Override
	public Room createObject(Room object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId());
	}

	@Override
	public Boolean deleteObject(Room object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
		return true;
	}

	@Override
	public List<Room> loadList(QueryParam queryParam) throws SQLException {
		return entityManager.createQuery("FROM Room ", Room.class).getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		
		Long countResults = countObject();
		int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		List<Room> classRoom = entityManager.createQuery("FROM Room", Room.class)				
				.setMaxResults(limit)
				.setFirstResult(offset)
				.getResultList();
		map.put(ClassRoomConstant.CLASS_ROOMS, classRoom);
		map.put(SystemConstant.SYSTEM_COUNT, countResults);
		map.put(SystemConstant.PAGE_COUNT, pageCount);		
		return map;
	}

	@Override
	public Room updateObject(Room object) throws SQLException {
		entityManager.merge(object);
		return findObject(object.getId().intValue());
	}

	@Override
	public Long countObject() throws SQLException {
		return entityManager.createQuery("SELECT count(*) FROM Room", Long.class)				
				.getSingleResult();
	}

}
