package com.jerotoma.database.assemblers.dao;

import java.sql.SQLException;

import com.jerotoma.common.viewobjects.AddressVO;
import com.jerotoma.database.assemblers.AssemblerDao;

public interface AssemblerAddressDao extends AssemblerDao<AddressVO>{
	public AddressVO findAddressByStudentId(Integer studentId) throws SQLException;
	public AddressVO findAddressByTeacherId(Integer teacherId) throws SQLException;
	public AddressVO findAddressByParentId(Integer parentId) throws SQLException;
	public AddressVO findAddressByStaffId(Integer staffId) throws SQLException;

}
