package com.jerotoma.database.dao.admissions.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.admissions.TeacherCourseAdmission;
import com.jerotoma.database.dao.admissions.TeacherCourseAdmissionDao;

public class TeacherCourseAdmissionDaoImpl implements TeacherCourseAdmissionDao {
	
	@PersistenceContext 
	private EntityManager entityManager;
	
	@Override
	public TeacherCourseAdmission findObject(Integer primaryKey) throws SQLException {		
		return entityManager.find(TeacherCourseAdmission.class, primaryKey);
	}

	@Override
	public TeacherCourseAdmission findObjectUniqueKey(String uniqueKey) throws SQLException {
		return entityManager.createQuery("FROM StudentAdmission WHERE admissionCode := ?", TeacherCourseAdmission.class).setParameter("admissionCode", uniqueKey).getSingleResult();
	}

	@Override
	public TeacherCourseAdmission createObject(TeacherCourseAdmission object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId().intValue());
	}

	@Override
	public Boolean deleteObject(TeacherCourseAdmission object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
		return true;
	}

	@Override
	public List<TeacherCourseAdmission> loadList(QueryParam queryParam) throws SQLException {
		return entityManager.createQuery("FROM StudentAdmission ", TeacherCourseAdmission.class).getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return null;
	}

	@Override
	public TeacherCourseAdmission updateObject(TeacherCourseAdmission object) throws SQLException {
		entityManager.merge(object);
		return findObject(object.getId().intValue());
	}

	@Override
	public Long countObject() throws SQLException {
		return entityManager.createQuery("SELECT count(*) FROM StudentAdmission", Long.class)
				.getSingleResult();
	}

	

}
