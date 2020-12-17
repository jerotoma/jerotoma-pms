package com.jerotoma.services.configs.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.config.UserPreference;
import com.jerotoma.database.dao.configs.UserPreferenceDao;
import com.jerotoma.services.configs.UserPreferenceService;

@Service
@Transactional
public class UserPreferenceServiceImpl implements UserPreferenceService {
	
	@Autowired UserPreferenceDao systemPreferenceDao;

	@Override
	public UserPreference findObject(Integer primaryKey) throws SQLException {
		
		return systemPreferenceDao.findObject(primaryKey);
	}

	@Override
	public UserPreference findObjectUniqueKey(String uniqueKey) throws SQLException {
		return systemPreferenceDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public UserPreference createObject(UserPreference object) throws SQLException {
		return systemPreferenceDao.createObject(object);
	}

	@Override
	public UserPreference updateObject(UserPreference object) throws SQLException {
		if(!doesUserPreferenceExist(object.getUserId(), object.getName())) {
			createObject(object);
		}	
		UserPreference userPreference = findUserPreferenceByKeyAndUserID(object.getUserId(), object.getName());
		userPreference.setValue(object.getValue());
		return systemPreferenceDao.updateObject(userPreference);
	}

	@Override
	public Boolean deleteObject(UserPreference object) throws SQLException {
		return systemPreferenceDao.deleteObject(object);
	}

	@Override
	public List<UserPreference> loadList(QueryParam queryParam) throws SQLException {
		return systemPreferenceDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return systemPreferenceDao.loadMapList(queryParam);
	}

	@Override
	public Long countObject() throws SQLException {
		return systemPreferenceDao.countObject();
	}

	@Override
	public List<UserPreference> findUserPreferencesByUserID(Integer userId) throws SQLException {
		return systemPreferenceDao.findUserPreferencesByUserID(userId);
	}

	@Override
	public UserPreference findUserPreferenceByKeyAndUserID(Integer userId, String key) throws SQLException {
		return systemPreferenceDao.findUserPreferenceByKeyAndUserID(userId, key);
	}

	@Override
	public boolean doesUserPreferenceExist(Integer userId, String key) {		
		return systemPreferenceDao.doesUserPreferenceExist(userId, key);
	}

}
