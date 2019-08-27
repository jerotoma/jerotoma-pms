package com.jerotoma.database.impl.users;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.addresses.StaffAddress;
import com.jerotoma.database.dao.users.StaffAddressDao;

@Repository
@Transactional
public class StaffAddressDaoImpl implements StaffAddressDao {

	@PersistenceContext 
	private EntityManager entityManager;	
	

	@Override
	public StaffAddress findObject(Integer primaryKey) throws SQLException {
		
		return entityManager.find(StaffAddress.class, primaryKey);
	}

	@Override
	public StaffAddress findObjectUniqueKey(String uniqueKey) throws SQLException {
		return entityManager.createQuery("FROM StaffAddress WHERE id = ? ", StaffAddress.class).setParameter("id", uniqueKey).getSingleResult();
	}

	@Override
	public StaffAddress createObject(StaffAddress object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId());
	}

	@Override
	public Boolean deleteObject(StaffAddress object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));		
		return true;
	}

	@Override
	public List<StaffAddress> loadList(QueryParam queryParam) throws SQLException {
		
		return null;
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		
		return null;
	}

	@Override
	public StaffAddress updateObject(StaffAddress object) throws SQLException {
		entityManager.merge(object);		
		return findObject(object.getId().intValue());
	}

	@Override
	public Long countObject() throws SQLException {
		
		return null;
	}

}
