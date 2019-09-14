package com.jerotoma.services.users.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.users.Staff;
import com.jerotoma.database.dao.users.StaffDao;
import com.jerotoma.services.users.StaffService;

@Service
@Transactional
public class StaffServiceImpl implements StaffService {
	
	@Autowired StaffDao otherStaffDao;

	@Override
	public Staff findObject(Integer primaryKey) throws SQLException {
		return otherStaffDao.findObject(primaryKey);
	}

	@Override
	public Staff findObjectUniqueKey(String uniqueKey) throws SQLException {
		return otherStaffDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public Staff createObject(Staff object) throws SQLException {
		return otherStaffDao.createObject(object);
	}

	@Override
	public Staff updateObject(Staff object) throws SQLException {
		return otherStaffDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(Staff object) throws SQLException {
		return otherStaffDao.deleteObject(object);
	}

	@Override
	public List<Staff> loadList(QueryParam queryParam) throws SQLException {
		return otherStaffDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return otherStaffDao.loadMapList(queryParam);
	}

}
