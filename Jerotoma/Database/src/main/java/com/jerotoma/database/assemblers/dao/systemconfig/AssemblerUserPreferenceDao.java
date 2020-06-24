package com.jerotoma.database.assemblers.dao.systemconfig;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.viewobjects.UserPreferenceVO;
import com.jerotoma.database.assemblers.AssemblerDao;

public interface AssemblerUserPreferenceDao  extends AssemblerDao<UserPreferenceVO> {
	List<UserPreferenceVO> loadUserPreferenceByUserId(Integer userId) throws SQLException;
	UserPreferenceVO findUserPreferenceByKeyAndUserId(Integer userId, String userPreferenceKey) throws SQLException;
}
