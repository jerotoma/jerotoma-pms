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
import com.jerotoma.common.models.academic.Course;
import com.jerotoma.common.models.academic.SchoolClassAcademicYearCourse;
import com.jerotoma.database.dao.courses.SchoolClassAcademicYearCourseDao;

@Repository
@Transactional
public class SchoolClassAcademicYearCourseDaoImpl  implements SchoolClassAcademicYearCourseDao {
	
	@PersistenceContext 
	private EntityManager entityManager;
	
	
	@Override
	public SchoolClassAcademicYearCourse findObject(Integer primaryKey) throws SQLException {
		return entityManager.find(SchoolClassAcademicYearCourse.class, primaryKey);
	}

	@Override
	public SchoolClassAcademicYearCourse findObjectUniqueKey(String uniqueKey) throws SQLException {
		return entityManager.createQuery("FROM SchoolClassAcademicYear WHERE code := ?", SchoolClassAcademicYearCourse.class).setParameter("code", uniqueKey).getSingleResult();
	}

	@Override
	public SchoolClassAcademicYearCourse createObject(SchoolClassAcademicYearCourse object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId());
	}

	@Override
	public Boolean deleteObject(SchoolClassAcademicYearCourse object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
		return true;
	}

	@Override
	public List<SchoolClassAcademicYearCourse> loadList(QueryParam queryParam) throws SQLException {
		return entityManager.createQuery("FROM SchoolClassAcademicYearCourse ", SchoolClassAcademicYearCourse.class).getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		List<Course> courses = entityManager.createQuery("FROM SchoolClassAcademicYearCourse", Course.class)				
				.getResultList();
		map.put(AcademicConstants.COURSES, courses);
		
		return map;
	}

	@Override
	public SchoolClassAcademicYearCourse updateObject(SchoolClassAcademicYearCourse object) throws SQLException {
		entityManager.merge(object);
		return findObject(object.getId().intValue());
	}

	@Override
	public Long countObject() throws SQLException {
		return entityManager.createQuery("SELECT count(*) FROM Course", Long.class)				
				.getSingleResult();
	}

}
