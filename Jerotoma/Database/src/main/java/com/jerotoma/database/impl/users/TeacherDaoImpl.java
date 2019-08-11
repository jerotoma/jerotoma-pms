package com.jerotoma.database.impl.users;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.models.users.Teacher;
import com.jerotoma.database.dao.users.TeacherDao;


@Repository
@Transactional
public class TeacherDaoImpl implements TeacherDao {
	
	@PersistenceContext 
	private EntityManager entityManager;	
	
	@Override
	public Teacher findObject(Integer primaryKey) {
		return entityManager.find(Teacher.class, primaryKey);
	}

	@Override
	public Teacher findObjectUniqueKey(String uniqueKey) {
		
		Teacher teacher = null;
		try {
			teacher = entityManager.createQuery("FROM Teacher WHERE teacherCode := ?", Teacher.class)
						.setParameter("teacherCode", uniqueKey)
						.getSingleResult();	
		} catch (EntityExistsException  | HibernateException  | JDataAccessException e) {
			throw new JDataAccessException(e.getMessage(), e);
		}
		return teacher;
	}

	@Override
	public Teacher createObject(Teacher object) {
		entityManager.persist(object);			
		return findObject(object.getId().intValue());
	}

	@Override
	public Boolean deleteObject(Teacher object) {
		
		try {
			entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));		
		} catch (EntityExistsException  | HibernateException  | JDataAccessException e) {
			throw new JDataAccessException(e.getMessage(), e);
		}		
		return true;
	}

	@Override
	public List<Teacher> loadList(QueryParam queryParam) {
		try {
			return entityManager.createQuery("FROM Teacher ", Teacher.class).getResultList();		
		} catch (EntityExistsException  | HibernateException  | JDataAccessException  e) {
			throw new JDataAccessException(e.getMessage(), e);
		}
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam){
		return null;
	}

	@Override
	public Teacher updateObject(Teacher object) {
		try {
			entityManager.merge(object);		
		} catch (EntityExistsException  | HibernateException | JDataAccessException e) {
			throw new JDataAccessException(e.getMessage(), e);
		}		
		return findObject(object.getId().intValue());
	}

	@Override
	public Long countObject() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
