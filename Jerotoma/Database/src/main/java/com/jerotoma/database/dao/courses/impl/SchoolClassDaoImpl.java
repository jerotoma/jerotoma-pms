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
import com.jerotoma.common.constants.AcademicConstants;
import com.jerotoma.common.models.academic.SchoolClass;
import com.jerotoma.database.dao.courses.SchoolClassDao;

@Repository
@Transactional
public class SchoolClassDaoImpl implements SchoolClassDao {
	
	@PersistenceContext 
	private EntityManager entityManager;
	
	
	@Override
	public SchoolClass findObject(Integer primaryKey) throws SQLException {
		return entityManager.find(SchoolClass.class, primaryKey);
	}

	@Override
	public SchoolClass findObjectUniqueKey(String uniqueKey) throws SQLException {
		return entityManager.createQuery("FROM SchoolClass WHERE code := ?", SchoolClass.class).setParameter("code", uniqueKey).getSingleResult();
	}

	@Override
	public SchoolClass createObject(SchoolClass object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId());
	}

	@Override
	public Boolean deleteObject(SchoolClass object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
		return true;
	}

	@Override
	public List<SchoolClass> loadList(QueryParam queryParam) throws SQLException {
		return entityManager.createQuery("FROM SchoolClass ", SchoolClass.class).getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		List<SchoolClass> courses = entityManager.createQuery("FROM SchoolClass", SchoolClass.class)				
				.getResultList();
		map.put(AcademicConstants.SCHOOL_CLASSES, courses);
		
		return map;
	}

	@Override
	public SchoolClass updateObject(SchoolClass object) throws SQLException {
		entityManager.merge(object);
		return findObject(object.getId().intValue());
	}

	@Override
	public Long countObject() throws SQLException {
		return entityManager.createQuery("SELECT count(*) FROM Course", Long.class)				
				.getSingleResult();
	}

}
