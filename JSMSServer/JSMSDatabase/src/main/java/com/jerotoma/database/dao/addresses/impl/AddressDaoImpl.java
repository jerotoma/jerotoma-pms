package com.jerotoma.database.dao.addresses.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.addresses.Address;
import com.jerotoma.database.dao.addresses.AddressDao;

@Transactional
@Repository
public class AddressDaoImpl implements AddressDao {

	@PersistenceContext 
	private EntityManager entityManager;	
	

	@Override
	public Address findObject(Integer primaryKey) throws SQLException {		
		return entityManager.find(Address.class, primaryKey);
	}

	@Override
	public Address findObjectUniqueKey(String uniqueKey) throws SQLException {
		return entityManager.createQuery("FROM Address WHERE id = ? ", Address.class).setParameter("id", uniqueKey).getSingleResult();
	}

	@Override
	public Address createObject(Address object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId());
	}

	@Override
	public Boolean deleteObject(Address object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));		
		return true;
	}

	@Override
	public List<Address> loadList(QueryParam queryParam) throws SQLException {
		return entityManager.createQuery("FROM Address ", Address.class).getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		
		return null;
	}

	@Override
	public Address updateObject(Address object) throws SQLException {
		return entityManager.merge(object);
	}

	@Override
	public Long countObject() throws SQLException {
		
		return null;
	}


}
