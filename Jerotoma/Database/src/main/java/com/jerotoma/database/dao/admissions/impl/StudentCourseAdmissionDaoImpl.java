package com.jerotoma.database.dao.admissions.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.admissions.StudentCourseAdmission;
import com.jerotoma.database.dao.admissions.StudentCourseAdmissionDao;

@Repository
@Transactional
public class StudentCourseAdmissionDaoImpl implements StudentCourseAdmissionDao {
	
	@PersistenceContext 
	private EntityManager entityManager;
	
	@Override
	public StudentCourseAdmission findObject(Integer primaryKey) throws SQLException {		
		return entityManager.find(StudentCourseAdmission.class, primaryKey);
	}

	@Override
	public StudentCourseAdmission findObjectUniqueKey(String uniqueKey) throws SQLException {
		return entityManager.createQuery("FROM StudentAdmission WHERE admissionCode := ?", StudentCourseAdmission.class).setParameter("admissionCode", uniqueKey).getSingleResult();
	}

	@Override
	public StudentCourseAdmission createObject(StudentCourseAdmission object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId().intValue());
	}

	@Override
	public Boolean deleteObject(StudentCourseAdmission object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
		return true;
	}

	@Override
	public List<StudentCourseAdmission> loadList(QueryParam queryParam) throws SQLException {
		return entityManager.createQuery("FROM StudentAdmission ", StudentCourseAdmission.class).getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return null;
	}

	@Override
	public StudentCourseAdmission updateObject(StudentCourseAdmission object) throws SQLException {
		entityManager.merge(object);
		return findObject(object.getId().intValue());
	}

	@Override
	public Long countObject() throws SQLException {
		return entityManager.createQuery("SELECT count(*) FROM StudentAdmission", Long.class)
				.getSingleResult();
	}

	

}
