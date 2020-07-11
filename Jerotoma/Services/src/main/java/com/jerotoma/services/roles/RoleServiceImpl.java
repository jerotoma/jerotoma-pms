package com.jerotoma.services.roles;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.RoleConstant.USER_ROLES;
import com.jerotoma.common.models.security.Role;
import com.jerotoma.database.dao.roles.RoleDao;

@Service
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

	@Override
	public Role updateObject(Role object) throws SQLException {
		return roleDao.updateObject(object);
	}

	@Override
	public List<Role> loadList() {		
		return roleDao.loadList();
	}

	@Override
	public List<Role> loadListFromRoleNames(List<USER_ROLES> userRoles) {
		
		try {
			return roleDao.loadListFromRoleNames(userRoles);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return new ArrayList<Role>();
	}

}
