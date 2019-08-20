package com.jerotoma.services.users.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.addresses.StudentAddress;
import com.jerotoma.database.dao.users.StudentAddressDao;
import com.jerotoma.services.users.StudentAddressService;

@Service
@Transactional
public class StudentAddressServiceImpl implements StudentAddressService {
	
	@Autowired StudentAddressDao studentDao;	

	@Override
	public StudentAddress findObject(Integer primaryKey) throws SQLException {
		return studentDao.findObject(primaryKey);
	}

	@Override
	public StudentAddress findObjectUniqueKey(String uniqueKey) throws SQLException {
		return studentDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public StudentAddress createObject(StudentAddress object) throws SQLException {
		return studentDao.createObject(object);
	}

	@Override
	public StudentAddress updateObject(StudentAddress object) throws SQLException {
		return studentDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(StudentAddress object) throws SQLException {
		return studentDao.deleteObject(object);
	}

	@Override
	public List<StudentAddress> loadList(QueryParam queryParam) throws SQLException {
		return studentDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return studentDao.loadMapList(queryParam);
	}
}
