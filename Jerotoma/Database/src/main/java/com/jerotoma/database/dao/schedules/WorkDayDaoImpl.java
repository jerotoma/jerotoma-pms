package com.jerotoma.database.dao.schedules;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.schedules.WorkDay;
import com.jerotoma.common.utils.StringUtility;

@Repository
@Transactional
public class WorkDayDaoImpl implements WorkDayDao {
	
	@PersistenceContext 
	private EntityManager entityManager;

	@Override
	public WorkDay findObject(Integer primaryKey) throws SQLException {
		return entityManager.find(WorkDay.class, primaryKey);
	}

	@Override
	public WorkDay findObjectUniqueKey(String uniqueKey) throws SQLException {
		if (!StringUtility.isNumeric(uniqueKey)) {
			return null;
		}		
		return findObject(Integer.valueOf(uniqueKey));
	}

	@Override
	public WorkDay createObject(WorkDay object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId());
	}

	@Override
	public WorkDay updateObject(WorkDay object) throws SQLException {
		return entityManager.merge(object);
	}

	@Override
	public Boolean deleteObject(WorkDay object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));		
		return true;
	}

	@Override
	public List<WorkDay> loadList(QueryParam queryParam) throws SQLException {
		return entityManager.createQuery("FROM WorkDay ", WorkDay.class).getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		throw new RuntimeException("Not implemented for now");
	}

	@Override
	public Long countObject() throws SQLException {
		throw new RuntimeException("Not implemented for now");
	}

}
