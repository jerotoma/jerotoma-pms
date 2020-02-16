package com.jerotoma.services.roles;

import java.util.List;

import com.jerotoma.common.models.security.Role;
import com.jerotoma.services.BaseService;

public interface RoleService extends BaseService<Role> {
	public List<Role> loadList();

}
