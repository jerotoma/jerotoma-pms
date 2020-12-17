package com.jerotoma.services.assemblers.media.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.viewobjects.UserMediaVO;
import com.jerotoma.database.assemblers.dao.media.AssemblerUserMediaDao;
import com.jerotoma.services.assemblers.media.AssemblerUserMediaService;

@Service
public class AssemblerUserMediaServiceImpl implements AssemblerUserMediaService {
	
	@Autowired AssemblerUserMediaDao assemblerUserMediaDao;

	@Override
	public UserMediaVO findObject(Integer primaryKey) throws SQLException {
		return assemblerUserMediaDao.findObject(primaryKey);
	}

	@Override
	public UserMediaVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		return assemblerUserMediaDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public List<UserMediaVO> loadList() throws SQLException {
		return assemblerUserMediaDao.loadList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return assemblerUserMediaDao.loadMapList(queryParam);
	}

	@Override
	public Long countObject() throws SQLException {
		return assemblerUserMediaDao.countObject();
	}

	@Override
	public boolean doesUserMediaExist(Integer mediaId, Integer userId) {		
		return assemblerUserMediaDao.doesUserMediaExist(mediaId, userId);
	}

}
