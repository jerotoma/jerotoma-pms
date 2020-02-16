package com.jerotoma.database.dao.roles;

import java.util.List;

import com.jerotoma.common.models.security.Role;
import com.jerotoma.database.dao.BaseDao;

public interface RoleDao extends BaseDao<Role> {
	public List<Role> loadList();

}
