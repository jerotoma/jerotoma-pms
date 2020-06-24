package com.jerotoma.services.assemblers.systemconfigs;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.viewobjects.UserPreferenceVO;
import com.jerotoma.services.AssemblerService;

public interface AssemblerUserPreferenceService extends AssemblerService<UserPreferenceVO> {
	List<UserPreferenceVO> loadUserPreferenceByUserId(Integer userId) throws SQLException;
	UserPreferenceVO findUserPreferenceByKeyAndUserId(Integer userId, String userPreferenceKey) throws SQLException;

}
