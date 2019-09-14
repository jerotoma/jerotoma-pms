package com.jerotoma.database.dao.users.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Repository;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.models.users.UserRole;
import com.jerotoma.database.dao.users.UserRoleDao;

@Repository
@Transactional
public class UserRoleDaoImpl implements UserRoleDao {
	
	@PersistenceContext 
	private EntityManager entityManager;	
	
	@Override
	public UserRole findObject(Integer primaryKey) {
		return entityManager.find(UserRole.class, primaryKey);
	}
	
	@Override
	public UserRole findObjectUniqueKey(String uniqueKey) throws SQLException {
		throw new DataAccessResourceFailureException("findObjectUniqueKey not implemented yet");
	}

	@Override
	public UserRole createObject(UserRole object) throws SQLException {
		entityManager.persist(object);			
		return findObject(object.getId());
	}

	@Override
	public UserRole updateObject(UserRole object) throws SQLException {					
		return entityManager.merge(object);
	}

	@Override
	public Boolean deleteObject(UserRole object) throws SQLException {
		try {
			entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));		
		} catch (EntityExistsException  | HibernateException  | JDataAccessException e) {
			throw new JDataAccessException(e.getMessage(), e);
		}		
		return true;
	}

	@Override
	public List<UserRole> loadList(QueryParam queryParam) throws SQLException {
		try {
			return entityManager.createQuery("FROM UserRole ", UserRole.class).getResultList();		
		} catch (EntityExistsException  | HibernateException  | JDataAccessException  e) {
			throw new JDataAccessException(e.getMessage(), e);
		}
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		throw new DataAccessResourceFailureException("loadMapList not implemented yet");
	}

	@Override
	public Long countObject() throws SQLException {
		throw new DataAccessResourceFailureException("countObject not implemented yet");
	}

}
