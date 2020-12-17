package com.jerotoma.services.users;

import com.jerotoma.common.models.users.UserRole;
import com.jerotoma.services.BaseService;

public interface UserRoleService  extends BaseService<UserRole> {

	UserRole findUserRoleByUserIdAndRoleID(Integer userId, Integer roleId);

}
