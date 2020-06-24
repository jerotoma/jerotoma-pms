package com.jerotoma.services.assemblers.systemconfigs;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.viewobjects.UserPreferenceVO;
import com.jerotoma.database.assemblers.dao.systemconfig.AssemblerUserPreferenceDao;

@Service
public class AssemblerUserPreferenceServiceImpl implements AssemblerUserPreferenceService {
	
	@Autowired AssemblerUserPreferenceDao assemblerUserPreferenceDao;

	@Override
	public UserPreferenceVO findObject(Integer primaryKey) throws SQLException {
		return assemblerUserPreferenceDao.findObject(primaryKey);
	}

	@Override
	public UserPreferenceVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		return assemblerUserPreferenceDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public List<UserPreferenceVO> loadList(QueryParam queryParam) throws SQLException {
		return assemblerUserPreferenceDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return assemblerUserPreferenceDao.loadMapList(queryParam);
	}

	@Override
	public Long countObject() throws SQLException {
		return assemblerUserPreferenceDao.countObject();
	}

	@Override
	public List<UserPreferenceVO> loadUserPreferenceByUserId(Integer userId) throws SQLException {
		return assemblerUserPreferenceDao.loadUserPreferenceByUserId(userId);
	}

	@Override
	public UserPreferenceVO findUserPreferenceByKeyAndUserId(Integer userId, String userPreferenceKey)
			throws SQLException {
		return assemblerUserPreferenceDao.findUserPreferenceByKeyAndUserId(userId, userPreferenceKey);
	}

}
