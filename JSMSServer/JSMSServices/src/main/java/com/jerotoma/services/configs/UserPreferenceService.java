package com.jerotoma.services.configs;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.models.config.UserPreference;
import com.jerotoma.services.BaseService;

public interface UserPreferenceService extends BaseService<UserPreference> {
	public Long countObject() throws SQLException;
	public List<UserPreference> findUserPreferencesByUserID(Integer userId) throws SQLException;
	public UserPreference findUserPreferenceByKeyAndUserID(Integer userId, String key) throws SQLException;
	public boolean doesUserPreferenceExist(Integer userId, String key);
}
