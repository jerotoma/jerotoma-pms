package com.jerotoma.database.impl.users;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.users.OtherStaff;
import com.jerotoma.database.dao.users.OtherStaffDao;

@Repository
@Transactional
public class OtherStaffDaoImpl implements OtherStaffDao {
	
	@PersistenceContext 
	private EntityManager entityManager;	
	

	@Override
	public OtherStaff findObject(Integer primaryKey) throws SQLException {
		
		return entityManager.find(OtherStaff.class, primaryKey);
	}

	@Override
	public OtherStaff findObjectUniqueKey(String uniqueKey) throws SQLException {
		return entityManager.createQuery("FROM OtherStaff WHERE id = ? ", OtherStaff.class).setParameter("id", uniqueKey).getSingleResult();
	}

	@Override
	public OtherStaff createObject(OtherStaff object) throws SQLException {
		entityManager.merge(object);
		return findObject(object.getId().intValue());
	}

	@Override
	public Boolean deleteObject(OtherStaff object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));		
		return true;
	}

	@Override
	public List<OtherStaff> loadList(QueryParam queryParam) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OtherStaff updateObject(OtherStaff object) throws SQLException {
		entityManager.merge(object);		
		return findObject(object.getId().intValue());
	}

	@Override
	public Long countObject() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
