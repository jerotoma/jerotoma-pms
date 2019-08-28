package com.jerotoma.database.dao.users.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.addresses.ParentAddress;
import com.jerotoma.database.dao.users.ParentAddressDao;

@Repository
@Transactional
public class ParentAddressDaoImpl implements ParentAddressDao {

	@PersistenceContext 
	private EntityManager entityManager;	
	

	@Override
	public ParentAddress findObject(Integer primaryKey) throws SQLException {
		return entityManager.find(ParentAddress.class, primaryKey);
	}

	@Override
	public ParentAddress findObjectUniqueKey(String uniqueKey) throws SQLException {
		return null;
	}

	@Override
	public ParentAddress createObject(ParentAddress object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId().intValue());
	}

	@Override
	public Boolean deleteObject(ParentAddress object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
		return true;
	}

	@Override
	public List<ParentAddress> loadList(QueryParam queryParam) throws SQLException {
		return entityManager.createQuery("FROM ParentAddress ", ParentAddress.class).getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParentAddress updateObject(ParentAddress object) throws SQLException {
		entityManager.merge(object);
		return findObject(object.getId().intValue());
	}

	@Override
	public Long countObject() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
