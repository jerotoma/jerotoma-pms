package com.jerotoma.database.dao.users;

import com.jerotoma.common.models.users.UserRole;
import com.jerotoma.database.dao.BaseDao;

public interface UserRoleDao extends BaseDao<UserRole>{

	UserRole findUserRoleByUserIdAndRoleID(Integer userId, Integer roleId); 

}
