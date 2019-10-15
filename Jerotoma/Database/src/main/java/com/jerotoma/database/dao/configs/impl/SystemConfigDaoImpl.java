package com.jerotoma.database.dao.configs.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.SystemConfigConstant;
import com.jerotoma.common.models.config.SystemConfig;
import com.jerotoma.database.dao.configs.SystemConfigDao;

@Repository
@Transactional
public class SystemConfigDaoImpl implements SystemConfigDao {
	
	@PersistenceContext 
	private EntityManager entityManager;

	@Override
	public SystemConfig findObject(Integer primaryKey) throws SQLException {
		return entityManager.find(SystemConfig.class, primaryKey);
	}

	@Override
	public SystemConfig findObjectUniqueKey(String uniqueKey) throws SQLException {
		return entityManager.createQuery("FROM SystemConfig WHERE name =:name", SystemConfig.class)
				.setParameter("name", uniqueKey)
				.getSingleResult();
	}

	@Override
	public SystemConfig createObject(SystemConfig object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId());
	}

	@Override
	public SystemConfig updateObject(SystemConfig object) throws SQLException {
		return entityManager.merge(object);
	}

	@Override
	public Boolean deleteObject(SystemConfig object) throws SQLException {
		entityManager.remove(object);
		return true;
	}

	@Override
	public List<SystemConfig> loadList(QueryParam queryParam) throws SQLException {
		return entityManager.createQuery("FROM SystemConfig", SystemConfig.class).getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		List<SystemConfig> systemConfigs = entityManager.createQuery("FROM SystemConfig", SystemConfig.class).getResultList();		
		map.put(SystemConfigConstant.SYSTEM_CONFINGS, systemConfigs);
		
		return map;
	}

	@Override
	public Long countObject() throws SQLException {
		return entityManager.createQuery("SELECT count(*) FROM SystemConfig", Long.class).getSingleResult();
	}
}
