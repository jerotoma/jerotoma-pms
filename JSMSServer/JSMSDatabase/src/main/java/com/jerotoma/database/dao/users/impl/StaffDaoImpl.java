package com.jerotoma.database.dao.users.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.users.Staff;
import com.jerotoma.database.dao.users.StaffDao;

@Repository
@Transactional
public class StaffDaoImpl implements StaffDao {
	
	@PersistenceContext 
	private EntityManager entityManager;	
	

	@Override
	public Staff findObject(Integer primaryKey) throws SQLException {		
		return entityManager.find(Staff.class, primaryKey);
	}

	@Override
	public Staff findObjectUniqueKey(String uniqueKey) throws SQLException {
		return entityManager.createQuery("FROM Staff WHERE id = ? ", Staff.class).setParameter("id", uniqueKey).getSingleResult();
	}

	@Override
	public Staff createObject(Staff object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId());
	}

	@Override
	public Boolean deleteObject(Staff object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));		
		return true;
	}

	@Override
	public List<Staff> loadList(QueryParam queryParam) throws SQLException {
		
		return null;
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		
		return null;
	}

	@Override
	public Staff updateObject(Staff object) throws SQLException {
		return entityManager.merge(object);
	}

	@Override
	public Long countObject() throws SQLException {		
		return entityManager.createQuery("SELECT count(*) FROM Staff", Long.class).getSingleResult();
	}

}
