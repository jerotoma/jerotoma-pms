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
import com.jerotoma.common.models.config.UserPreference;
import com.jerotoma.database.dao.configs.UserPreferenceDao;


@Repository
@Transactional
public class UserPreferenceDaoImpl implements UserPreferenceDao{
	@PersistenceContext 
	private EntityManager entityManager;

	@Override
	public UserPreference findObject(Integer primaryKey) throws SQLException {
		return entityManager.find(UserPreference.class, primaryKey);
	}

	@Override
	public UserPreference findObjectUniqueKey(String uniqueKey) throws SQLException {
		return entityManager.createQuery("FROM UserPreference WHERE name =:name", UserPreference.class)
				.setParameter("name", uniqueKey)
				.getSingleResult();
	}

	@Override
	public UserPreference createObject(UserPreference object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId());
	}

	@Override
	public UserPreference updateObject(UserPreference object) throws SQLException {
		return entityManager.merge(object);
	}

	@Override
	public Boolean deleteObject(UserPreference object) throws SQLException {
		entityManager.remove(object);
		return true;
	}

	@Override
	public List<UserPreference> loadList(QueryParam queryParam) throws SQLException {
		return entityManager.createQuery("FROM UserPreference", UserPreference.class).getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		List<UserPreference> systemConfigs = entityManager.createQuery("FROM UserPreference", UserPreference.class).getResultList();		
		map.put(SystemConfigConstant.SYSTEM_CONFINGS, systemConfigs);
		
		return map;
	}

	@Override
	public Long countObject() throws SQLException {
		return entityManager.createQuery("SELECT count(*) FROM UserPreference", Long.class).getSingleResult();
	}

	@Override
	public List<UserPreference> findUserPreferencesByUserID(Integer userId) throws SQLException {
		return entityManager
				.createQuery("FROM UserPreference WHERE user_id =:userID", UserPreference.class)
				.setParameter("userID", userId)
				.getResultList();
	}

	@Override
	public UserPreference findUserPreferenceByKeyAndUserID(Integer userId, String key) throws SQLException {
		return entityManager
				.createQuery("FROM UserPreference WHERE user_id =:userID AND name =:key", UserPreference.class)
				.setParameter("userID", userId)
				.setParameter("key", key)
				.getSingleResult();
	}
}
