package com.jerotoma.services.users;

import java.sql.SQLException;

import com.jerotoma.common.models.users.Staff;
import com.jerotoma.services.BaseService;

public interface StaffService extends BaseService<Staff> {
	public Long countObject() throws SQLException;

}
