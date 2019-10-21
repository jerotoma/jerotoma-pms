package com.jerotoma.database.dao.configs;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.models.config.UserPreference;
import com.jerotoma.database.dao.BaseDao;

public interface UserPreferenceDao extends BaseDao<UserPreference> {
	public Long countObject() throws SQLException;
	public List<UserPreference> findUserPreferencesByUserID(Integer userId) throws SQLException;
	public UserPreference findUserPreferenceByKeyAndUserID(Integer userId, String key) throws SQLException;

}
