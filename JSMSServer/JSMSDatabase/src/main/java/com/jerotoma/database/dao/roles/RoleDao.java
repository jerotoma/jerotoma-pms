package com.jerotoma.database.dao.roles;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.constants.RoleConstant.USER_ROLES;
import com.jerotoma.common.models.security.Role;
import com.jerotoma.database.dao.BaseDao;

public interface RoleDao extends BaseDao<Role> {
	public List<Role> loadList();
	public List<Role> loadListFromRoleNames(List<USER_ROLES> userRoles) throws SQLException;

}
