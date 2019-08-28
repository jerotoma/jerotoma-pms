package com.jerotoma.database.dao.courses.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.courses.Course;
import com.jerotoma.database.dao.courses.CourseDao;

@Repository
@Transactional
public class CourseDaoImpl implements CourseDao {
	
	@PersistenceContext 
	private EntityManager entityManager;
	
	
	@Override
	public Course findObject(Integer primaryKey) throws SQLException {
		return entityManager.find(Course.class, primaryKey);
	}

	@Override
	public Course findObjectUniqueKey(String uniqueKey) throws SQLException {
		return entityManager.createQuery("FROM Course WHERE code := ?", Course.class).setParameter("code", uniqueKey).getSingleResult();
	}

	@Override
	public Course createObject(Course object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId().intValue());
	}

	@Override
	public Boolean deleteObject(Course object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
		return true;
	}

	@Override
	public List<Course> loadList(QueryParam queryParam) throws SQLException {
		return entityManager.createQuery("FROM Course ", Course.class).getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return null;
	}

	@Override
	public Course updateObject(Course object) throws SQLException {
		entityManager.merge(object);
		return findObject(object.getId().intValue());
	}

	@Override
	public Long countObject() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
