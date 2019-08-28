package com.jerotoma.database.dao.users.impl;

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
import com.jerotoma.common.models.addresses.TeacherAddress;
import com.jerotoma.database.dao.users.TeacherAddressDao;

@Repository
@Transactional
public class TeacherAddressDaoImpl implements TeacherAddressDao {

	@PersistenceContext 
	private EntityManager entityManager;	
	
	@Override
	public TeacherAddress findObject(Integer primaryKey) {
		return entityManager.find(TeacherAddress.class, primaryKey);
	}

	@Override
	public TeacherAddress findObjectUniqueKey(String uniqueKey) {
		
		TeacherAddress teacherAddress = null;
		try {
			teacherAddress = entityManager.createQuery("FROM TeacherAddress WHERE teacherCode := ?", TeacherAddress.class)
						.setParameter("teacherCode", uniqueKey)
						.getSingleResult();	
		} catch (EntityExistsException  | HibernateException  | JDataAccessException e) {
			throw new JDataAccessException(e.getMessage(), e);
		}
		return teacherAddress;
	}

	@Override
	public TeacherAddress createObject(TeacherAddress object) {
		entityManager.persist(object);			
		return findObject(object.getId().intValue());
	}

	@Override
	public Boolean deleteObject(TeacherAddress object) {
		
		try {
			entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));		
		} catch (EntityExistsException  | HibernateException  | JDataAccessException e) {
			throw new JDataAccessException(e.getMessage(), e);
		}		
		return true;
	}

	@Override
	public List<TeacherAddress> loadList(QueryParam queryParam) {
		try {
			return entityManager.createQuery("FROM TeacherAddress ", TeacherAddress.class).getResultList();		
		} catch (EntityExistsException  | HibernateException  | JDataAccessException  e) {
			throw new JDataAccessException(e.getMessage(), e);
		}
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam){
		return null;
	}

	@Override
	public TeacherAddress updateObject(TeacherAddress object) {
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
