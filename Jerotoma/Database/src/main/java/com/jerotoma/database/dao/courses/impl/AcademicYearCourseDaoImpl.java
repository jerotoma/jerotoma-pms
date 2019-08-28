package com.jerotoma.database.dao.courses.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.courses.AcademicYearCourse;
import com.jerotoma.database.dao.courses.AcademicYearCourseDao;

public class AcademicYearCourseDaoImpl implements AcademicYearCourseDao {

	@PersistenceContext 
	private EntityManager entityManager;

	@Override
	public AcademicYearCourse findObject(Integer primaryKey) throws SQLException {
		return entityManager.find(AcademicYearCourse.class, primaryKey);
	}

	@Override
	public AcademicYearCourse findObjectUniqueKey(String uniqueKey) throws SQLException {
		return entityManager.createQuery("FROM AcademicYearCourse WHERE code = ?", AcademicYearCourse.class)
				.setParameter("code", uniqueKey)
				.getSingleResult();
	}

	@Override
	public AcademicYearCourse createObject(AcademicYearCourse object) throws SQLException {
			entityManager.persist(object);
		return findObject(object.getId());
	}

	@Override
	public AcademicYearCourse updateObject(AcademicYearCourse object) throws SQLException {
		return entityManager.merge(object);
	}

	@Override
	public Boolean deleteObject(AcademicYearCourse object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
		return true;
	}

	@Override
	public List<AcademicYearCourse> loadList(QueryParam queryParam) throws SQLException {
		return entityManager.createQuery("FROM AcademicYearCourse", AcademicYearCourse.class)				
				.getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countObject() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}
