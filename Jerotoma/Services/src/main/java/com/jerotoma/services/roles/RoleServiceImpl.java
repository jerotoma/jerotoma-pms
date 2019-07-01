package com.jerotoma.services.roles;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.roles.Role;
import com.jerotoma.database.roles.dao.RoleDao;

public class RoleServiceImpl  implements RoleService {
	
	@Autowired RoleDao roleDao;

	@Override
	public Role findObject(Integer primaryKey) throws SQLException {
		return roleDao.findObject(primaryKey);
	}

	@Override
	public Role findObjectUniqueKey(String uniqueKey) throws SQLException {
		return roleDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public Role createObject(Role object) throws SQLException {
		return roleDao.createObject(object);
	}

	@Override
	public Boolean deleteObject(Role object) throws SQLException {
		return roleDao.deleteObject(object);
	}

	@Override
	public List<Role> loadList(QueryParam queryParam) throws SQLException {
		return roleDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return roleDao.loadMapList(queryParam);
	}

}
