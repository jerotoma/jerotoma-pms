package com.jerotoma.services.users.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.addresses.ParentAddress;
import com.jerotoma.database.dao.users.ParentAddressDao;
import com.jerotoma.services.users.ParentAddressService;

@Service
@Transactional
public class ParentAddressServiceImpl implements ParentAddressService {
	
	@Autowired ParentAddressDao parentDao;

	@Override
	public ParentAddress findObject(Integer primaryKey) throws SQLException {
		return parentDao.findObject(primaryKey);
	}

	@Override
	public ParentAddress findObjectUniqueKey(String uniqueKey) throws SQLException {
		return parentDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public ParentAddress createObject(ParentAddress object) throws SQLException {
		return parentDao.createObject(object);
	}

	@Override
	public ParentAddress updateObject(ParentAddress object) throws SQLException {
		return parentDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(ParentAddress object) throws SQLException {
		return parentDao.deleteObject(object);
	}

	@Override
	public List<ParentAddress> loadList(QueryParam queryParam) throws SQLException {
		return parentDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return parentDao.loadMapList(queryParam);
	}
}
