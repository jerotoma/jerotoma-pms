package com.jerotoma.database.impl.users;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.users.Parent;
import com.jerotoma.database.dao.users.ParentDao;

@Repository
public class ParentDaoImpl implements ParentDao {
	
	@PersistenceContext 
	private EntityManager entityManager;	
	

	@Override
	public Parent findObject(Integer primaryKey) throws SQLException {
		return entityManager.find(Parent.class, primaryKey);
	}

	@Override
	public Parent findObjectUniqueKey(String uniqueKey) throws SQLException {
		return null;
	}

	@Override
	public Parent createObject(Parent object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId().intValue());
	}

	@Override
	public Boolean deleteObject(Parent object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
		return true;
	}

	@Override
	public List<Parent> loadList(QueryParam queryParam) throws SQLException {
		return entityManager.createQuery("FROM Parent ", Parent.class).getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Parent updateObject(Parent object) throws SQLException {
		entityManager.merge(object);
		return findObject(object.getId().intValue());
	}

}
