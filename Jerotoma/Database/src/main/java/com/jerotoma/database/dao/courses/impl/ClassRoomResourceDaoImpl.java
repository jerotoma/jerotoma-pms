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
import com.jerotoma.common.models.academic.ClassRoomResource;
import com.jerotoma.database.dao.DaoUtil;
import com.jerotoma.database.dao.courses.ClassRoomResourceDao;

@Repository
@Transactional
public class ClassRoomResourceDaoImpl implements ClassRoomResourceDao {

	@PersistenceContext 
	private EntityManager entityManager;
	
	@Override
	public ClassRoomResource findObject(Integer primaryKey) throws SQLException {
		
		return entityManager.find(ClassRoomResource.class, primaryKey);
	}

	@Override
	public ClassRoomResource findObjectUniqueKey(String uniqueKey) throws SQLException {
		String  query = "FROM ClassRoomResource WHERE code =:code ";
		
		return entityManager.createQuery(query, ClassRoomResource.class)
				.setParameter("code", uniqueKey)
				.getSingleResult();
	}

	@Override
	public ClassRoomResource createObject(ClassRoomResource object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId());
	}

	@Override
	public ClassRoomResource updateObject(ClassRoomResource object) throws SQLException {
		return entityManager.merge(object);
	}

	@Override
	public Boolean deleteObject(ClassRoomResource object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
		return true;
	}

	@Override
	public List<ClassRoomResource> loadList(QueryParam queryParam) throws SQLException {
		return entityManager.createQuery("FROM ClassRoomResource", ClassRoomResource.class)				
				.getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		
		Long countResults = countObject();
		int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		List<ClassRoomResource> classRoomResource = entityManager.createQuery("FROM ClassRoomResource", ClassRoomResource.class)
				.setMaxResults(limit)
				.setFirstResult(offset)
				.getResultList();
		map.put(ClassRoomResourceConstant.CLASS_ROOM_RESOURCES, classRoomResource);
		map.put(SystemConstant.SYSTEM_COUNT, countResults);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}

	@Override
	public Long countObject() throws SQLException {
		return entityManager.createQuery("SELECT count(*) FROM ClassRoomResource", Long.class)				
				.getSingleResult();
	}

}
