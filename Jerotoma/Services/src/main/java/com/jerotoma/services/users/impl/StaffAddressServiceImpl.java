package com.jerotoma.services.users.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.addresses.StaffAddress;
import com.jerotoma.database.dao.users.StaffAddressDao;
import com.jerotoma.services.users.StaffAddressService;

@Service
@Transactional
public class StaffAddressServiceImpl implements StaffAddressService{
	
	@Autowired StaffAddressDao staffAddressDao;

	@Override
	public StaffAddress findObject(Integer primaryKey) throws SQLException {
		return staffAddressDao.findObject(primaryKey);
	}

	@Override
	public StaffAddress findObjectUniqueKey(String uniqueKey) throws SQLException {
		return staffAddressDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public StaffAddress createObject(StaffAddress object) throws SQLException {
		return staffAddressDao.createObject(object);
	}

	@Override
	public StaffAddress updateObject(StaffAddress object) throws SQLException {
		StaffAddress staffAddress = staffAddressDao.getStaffAddressByStaffId(object.getStaff().getId());
		if (staffAddress == null) {
			return staffAddressDao.createObject(object);
		} else {			
			object.setId(staffAddress.getId());
			return staffAddressDao.updateObject(object);	
		}
	}

	@Override
	public Boolean deleteObject(StaffAddress object) throws SQLException {
		return staffAddressDao.deleteObject(object);
	}

	@Override
	public List<StaffAddress> loadList(QueryParam queryParam) throws SQLException {
		return staffAddressDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return staffAddressDao.loadMapList(queryParam);
	}

	@Override
	public StaffAddress getStaffAddressByStaffId(Integer staffId) {
		return staffAddressDao.getStaffAddressByStaffId(staffId);
	}
}
