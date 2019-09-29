package com.jerotoma.database.dao.users;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.models.addresses.TeacherAddress;
import com.jerotoma.database.dao.BaseDao;

public interface TeacherAddressDao extends BaseDao<TeacherAddress> {

	TeacherAddress findObjectByAddressID(Integer addressId) throws SQLException;

	List<TeacherAddress> findObjectByTeacherID(Integer teacherId) throws SQLException;

}
