package com.jerotoma.services.roles;

import java.util.List;

import com.jerotoma.common.constants.RoleConstant.USER_ROLES;
import com.jerotoma.common.models.security.Role;
import com.jerotoma.services.BaseService;

public interface RoleService extends BaseService<Role> {
	public List<Role> loadList();
	public List<Role> loadListFromRoleNames(List<USER_ROLES> userRoles);

}
