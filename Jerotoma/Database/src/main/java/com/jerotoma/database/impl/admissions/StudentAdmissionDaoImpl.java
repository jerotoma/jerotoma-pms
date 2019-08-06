package com.jerotoma.database.impl.admissions;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.admissions.StudentAdmission;
import com.jerotoma.database.dao.admissions.StudentAdmissionDao;

@Repository
@Transactional
public class StudentAdmissionDaoImpl implements StudentAdmissionDao {
	
	@PersistenceContext 
	private EntityManager entityManager;
	
	@Override
	public StudentAdmission findObject(Integer primaryKey) throws SQLException {		
		return entityManager.find(StudentAdmission.class, primaryKey);
	}

	@Override
	public StudentAdmission findObjectUniqueKey(String uniqueKey) throws SQLException {
		return entityManager.createQuery("FROM StudentAdmission WHERE admissionCode := ?", StudentAdmission.class).setParameter("admissionCode", uniqueKey).getSingleResult();
	}

	@Override
	public StudentAdmission createObject(StudentAdmission object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId().intValue());
	}

	@Override
	public Boolean deleteObject(StudentAdmission object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
		return true;
	}

	@Override
	public List<StudentAdmission> loadList(QueryParam queryParam) throws SQLException {
		return entityManager.createQuery("FROM StudentAdmission ", StudentAdmission.class).getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return null;
	}

	@Override
	public StudentAdmission updateObject(StudentAdmission object) throws SQLException {
		entityManager.merge(object);
		return findObject(object.getId().intValue());
	}

	

}
