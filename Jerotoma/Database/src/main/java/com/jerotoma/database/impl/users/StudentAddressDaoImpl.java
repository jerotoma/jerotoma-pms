package com.jerotoma.database.impl.users;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.addresses.StudentAddress;
import com.jerotoma.database.dao.users.StudentAddressDao;


@Repository
@Transactional
public class StudentAddressDaoImpl implements  StudentAddressDao {

	@PersistenceContext 
	private EntityManager entityManager;	
	
	@Override
	public StudentAddress findObject(Integer primaryKey) throws SQLException {
		return entityManager.find(StudentAddress.class, primaryKey);
	}

	@Override
	public StudentAddress findObjectUniqueKey(String uniqueKey) throws SQLException {
		return entityManager.createQuery("FROM StudentAddress WHERE studentCode := ?", StudentAddress.class).setParameter("studentCode", uniqueKey).getSingleResult();
	}

	@Override
	public StudentAddress createObject(StudentAddress object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId().intValue());
	}

	@Override
	public Boolean deleteObject(StudentAddress object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
		return true;
	}

	@Override
	public List<StudentAddress> loadList(QueryParam queryParam) throws SQLException {
		return entityManager.createQuery("FROM StudentAddress ", StudentAddress.class).getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StudentAddress updateObject(StudentAddress object) throws SQLException {
		entityManager.merge(object);
		return findObject(object.getId().intValue());
	}

	@Override
	public Long countObject() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}
