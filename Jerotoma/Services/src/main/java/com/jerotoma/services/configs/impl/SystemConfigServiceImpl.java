package com.jerotoma.services.configs.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.config.SystemConfig;
import com.jerotoma.database.dao.configs.SystemConfigDao;
import com.jerotoma.services.configs.SystemConfigService;

@Service
@Transactional
public class SystemConfigServiceImpl implements SystemConfigService {
	
	@Autowired SystemConfigDao systemConfigDao;

	@Override
	public SystemConfig findObject(Integer primaryKey) throws SQLException {
		
		return systemConfigDao.findObject(primaryKey);
	}

	@Override
	public SystemConfig findObjectUniqueKey(String uniqueKey) throws SQLException {

		return systemConfigDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public SystemConfig createObject(SystemConfig object) throws SQLException {
		return systemConfigDao.createObject(object);
	}

	@Override
	public SystemConfig updateObject(SystemConfig object) throws SQLException {
		return systemConfigDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(SystemConfig object) throws SQLException {
		return systemConfigDao.deleteObject(object);
	}

	@Override
	public List<SystemConfig> loadList(QueryParam queryParam) throws SQLException {
		return systemConfigDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return systemConfigDao.loadMapList(queryParam);
	}

	@Override
	public Long countObject() throws SQLException {
		return systemConfigDao.countObject();
	}

}
