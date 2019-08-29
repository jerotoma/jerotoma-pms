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
import com.jerotoma.common.models.academic.AcademicYearCourse;
import com.jerotoma.database.dao.courses.AcademicYearCourseDao;

@Repository
@Transactional
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
		Map<String, Object> map = new HashMap<>();
		List<AcademicYearCourse> academiYearCourses = entityManager.createQuery("FROM AcademicYearCourse", AcademicYearCourse.class)				
				.getResultList();
		map.put(AcademicConstants.ACADEMIC_YEAR_COURSES, academiYearCourses);
		
		 return map;
	}

	@Override
	public Long countObject() throws SQLException {		
		return entityManager.createQuery("SELECT count(*) FROM AcademicYearCourse", Long.class)				
				.getSingleResult();
	}
}
