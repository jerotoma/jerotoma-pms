package com.jerotoma.services.users.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.users.UserRole;
import com.jerotoma.database.dao.users.UserRoleDao;
import com.jerotoma.services.users.UserRoleService;

@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService{

	@Autowired UserRoleDao userRoleDao;
	
	@Override
	public UserRole findObject(Integer primaryKey) throws SQLException {		
		return userRoleDao.findObject(primaryKey);
	}

	@Override
	public UserRole findObjectUniqueKey(String uniqueKey) throws SQLException {
		return userRoleDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public UserRole createObject(UserRole object) throws SQLException {
		return userRoleDao.createObject(object);
	}

	@Override
	public UserRole updateObject(UserRole object) throws SQLException {
		return userRoleDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(UserRole object) throws SQLException {
		return userRoleDao.deleteObject(object);
	}

	@Override
	public List<UserRole> loadList(QueryParam queryParam) throws SQLException {
		return userRoleDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return userRoleDao.loadMapList(queryParam);
	}

	@Override
	public UserRole findUserRoleByUserIdAndRoleID(Integer userId, Integer roleId) {
		return userRoleDao.findUserRoleByUserIdAndRoleID(userId, roleId);
	}

}
