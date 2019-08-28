package com.jerotoma.database.dao.users.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.users.Student;
import com.jerotoma.database.dao.users.StudentDao;

@Repository
@Transactional
public class StudentDaoImpl implements StudentDao {
	
	@PersistenceContext 
	private EntityManager entityManager;	
	
	@Override
	public Student findObject(Integer primaryKey) throws SQLException {
		return entityManager.find(Student.class, primaryKey);
	}

	@Override
	public Student findObjectUniqueKey(String uniqueKey) throws SQLException {
		return entityManager.createQuery("FROM Student WHERE studentCode := ?", Student.class).setParameter("studentCode", uniqueKey).getSingleResult();
	}

	@Override
	public Student createObject(Student object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId().intValue());
	}

	@Override
	public Boolean deleteObject(Student object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
		return true;
	}

	@Override
	public List<Student> loadList(QueryParam queryParam) throws SQLException {
		return entityManager.createQuery("FROM Student ", Student.class).getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Student updateObject(Student object) throws SQLException {
		entityManager.merge(object);
		return findObject(object.getId().intValue());
	}

	@Override
	public Long countObject() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
