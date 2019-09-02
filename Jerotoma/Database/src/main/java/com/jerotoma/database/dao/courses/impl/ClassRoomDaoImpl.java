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
import com.jerotoma.common.models.academic.ClassRoom;
import com.jerotoma.database.dao.DaoUtil;
import com.jerotoma.database.dao.courses.ClassRoomDao;

@Repository
@Transactional
public class ClassRoomDaoImpl implements ClassRoomDao {
	
	@PersistenceContext 
	private EntityManager entityManager;
	
	
	@Override
	public ClassRoom findObject(Integer primaryKey) throws SQLException {
		return entityManager.find(ClassRoom.class, primaryKey);
	}

	@Override
	public ClassRoom findObjectUniqueKey(String uniqueKey) throws SQLException {
		return entityManager.createQuery("FROM ClassRoom WHERE code := ?", ClassRoom.class).setParameter("code", uniqueKey).getSingleResult();
	}

	@Override
	public ClassRoom createObject(ClassRoom object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId());
	}

	@Override
	public Boolean deleteObject(ClassRoom object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
		return true;
	}

	@Override
	public List<ClassRoom> loadList(QueryParam queryParam) throws SQLException {
		return entityManager.createQuery("FROM ClassRoom ", ClassRoom.class).getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		
		Long countResults = countObject();
		int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		List<ClassRoom> classRoom = entityManager.createQuery("FROM ClassRoom", ClassRoom.class)				
				.setMaxResults(limit)
				.setFirstResult(offset)
				.getResultList();
		map.put(ClassRoomConstant.CLASS_ROOMS, classRoom);
		map.put(SystemConstant.SYSTEM_COUNT, countResults);
		map.put(SystemConstant.PAGE_COUNT, pageCount);		
		return map;
	}

	@Override
	public ClassRoom updateObject(ClassRoom object) throws SQLException {
		entityManager.merge(object);
		return findObject(object.getId().intValue());
	}

	@Override
	public Long countObject() throws SQLException {
		return entityManager.createQuery("SELECT count(*) FROM ClassRoom", Long.class)				
				.getSingleResult();
	}

}
