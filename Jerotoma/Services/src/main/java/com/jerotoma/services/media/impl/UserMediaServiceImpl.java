package com.jerotoma.services.media.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.media.UserMedia;
import com.jerotoma.database.dao.media.UserMediaDao;
import com.jerotoma.services.media.UserMediaService;

@Transactional
@Service
public class UserMediaServiceImpl  implements UserMediaService {
	
	@Autowired UserMediaDao userMediaDao;

	@Override
	public UserMedia findObject(Integer primaryKey) throws SQLException {
		return userMediaDao.findObject(primaryKey);
	}

	@Override
	public UserMedia findObjectUniqueKey(String uniqueKey) throws SQLException {
		return userMediaDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public UserMedia createObject(UserMedia object) throws SQLException {
		return userMediaDao.createObject(object);
	}

	@Override
	public UserMedia updateObject(UserMedia object) throws SQLException {
		return userMediaDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(UserMedia object) throws SQLException {
		return userMediaDao.deleteObject(object);
	}

	@Override
	public List<UserMedia> loadList(QueryParam queryParam) throws SQLException {
		return userMediaDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return userMediaDao.loadMapList(queryParam);
	}

}
