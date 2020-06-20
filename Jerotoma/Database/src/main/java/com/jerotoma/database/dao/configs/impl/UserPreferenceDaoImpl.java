package com.jerotoma.database.dao.configs.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.SystemConfigConstant;
import com.jerotoma.common.models.config.UserPreference;
import com.jerotoma.database.dao.configs.UserPreferenceDao;


@Repository
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
		entityManager.merge(object);
		return findObject(object.getId());
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
		List<UserPreference> userPreferences = entityManager.createQuery("FROM UserPreference", UserPreference.class).getResultList();		
		map.put(SystemConfigConstant.SYSTEM_CONFINGS, userPreferences);
		
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

	@Override
	public boolean doesUserPreferenceExist(Integer userId, String key) {		
		Long count = entityManager.createQuery("SELECT COUNT(*) AS count FROM UserPreference WHERE user_id =:userID AND name =:key", Long.class)
				.setParameter("userID", userId)
				.setParameter("key", key)
				.getSingleResult();
		return count > 0;
	}
}
