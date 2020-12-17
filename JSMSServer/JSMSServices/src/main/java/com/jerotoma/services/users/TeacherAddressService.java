package com.jerotoma.services.users;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.models.addresses.TeacherAddress;
import com.jerotoma.services.BaseService;

public interface TeacherAddressService extends BaseService<TeacherAddress> {
	
	public List<TeacherAddress> findObjectByTeacherID(Integer teacherId) throws SQLException;
	public TeacherAddress findObjectByAddressID(Integer addressId) throws SQLException;

}
