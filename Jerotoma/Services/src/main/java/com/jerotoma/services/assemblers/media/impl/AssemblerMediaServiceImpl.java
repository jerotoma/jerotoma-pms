package com.jerotoma.services.assemblers.media.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.viewobjects.MediaVO;
import com.jerotoma.database.assemblers.dao.media.AssemblerMediaDao;
import com.jerotoma.services.assemblers.media.AssemblerMediaService;

@Service
public class AssemblerMediaServiceImpl implements AssemblerMediaService{
	
	@Autowired AssemblerMediaDao assemblerMediaDao;

	@Override
	public MediaVO findObject(Integer primaryKey) throws SQLException {
		return assemblerMediaDao.findObject(primaryKey);
	}

	@Override
	public MediaVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		return assemblerMediaDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public List<MediaVO> loadList(QueryParam queryParam) throws SQLException {
		return assemblerMediaDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return assemblerMediaDao.loadMapList(queryParam);
	}

	@Override
	public Long countObject() throws SQLException {
		return assemblerMediaDao.countObject();
	}

	@Override
	public List<MediaVO> getMediaList() throws SQLException {
		return assemblerMediaDao.getMediaList();
	}

}
