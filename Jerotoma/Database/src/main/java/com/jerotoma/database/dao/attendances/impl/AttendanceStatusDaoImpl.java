package com.jerotoma.database.dao.attendances.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.AttendanceConstant;
import com.jerotoma.common.models.attendances.AttendanceStatus;
import com.jerotoma.database.dao.attendances.AttendanceStatusDao;


@Transactional
@Repository
public class AttendanceStatusDaoImpl  implements AttendanceStatusDao {
	@PersistenceContext 
	private EntityManager entityManager;

	@Override
	public AttendanceStatus findObject(Integer primaryKey) throws SQLException {
		return entityManager.find(AttendanceStatus.class, primaryKey);
	}

	@Override
	public AttendanceStatus findObjectUniqueKey(String uniqueKey) throws SQLException {
		return entityManager.createQuery("FROM AttendanceStatus WHERE name =:name", AttendanceStatus.class)
				.setParameter("name", uniqueKey)
				.getSingleResult();
	}

	@Override
	public AttendanceStatus createObject(AttendanceStatus object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId());
	}

	@Override
	public AttendanceStatus updateObject(AttendanceStatus object) throws SQLException {
		return entityManager.merge(object);
	}

	@Override
	public Boolean deleteObject(AttendanceStatus object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
		return true;
	}

	@Override
	public List<AttendanceStatus> loadList(QueryParam queryParam) throws SQLException {
		return entityManager.createQuery("FROM AttendanceStatus", AttendanceStatus.class).getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		List<AttendanceStatus> systemConfigs = entityManager.createQuery("FROM AttendanceStatus", AttendanceStatus.class).getResultList();		
		map.put(AttendanceConstant.CLASS_ATTENDANCES, systemConfigs);
		
		return map;
	}

	@Override
	public Long countObject() throws SQLException {
		return entityManager.createQuery("SELECT count(*) FROM AttendanceStatus", Long.class).getSingleResult();
	}
}
