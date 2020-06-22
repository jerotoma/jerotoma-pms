package com.jerotoma.services.media.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.media.Media;
import com.jerotoma.database.dao.media.MediaDao;
import com.jerotoma.services.media.MediaService;

@Transactional
@Service
public class MediaServiceImpl implements MediaService {
	
	@Autowired MediaDao mediaDao;

	@Override
	public Media findObject(Integer primaryKey) throws SQLException {
		return mediaDao.findObject(primaryKey);
	}

	@Override
	public Media findObjectUniqueKey(String uniqueKey) throws SQLException {
		return mediaDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public Media createObject(Media object) throws SQLException {
		return mediaDao.createObject(object);
	}

	@Override
	public Media updateObject(Media object) throws SQLException {
		return mediaDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(Media object) throws SQLException {
		return mediaDao.deleteObject(object);
	}

	@Override
	public List<Media> loadList(QueryParam queryParam) throws SQLException {
		return mediaDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return mediaDao.loadMapList(queryParam);
	}

}
