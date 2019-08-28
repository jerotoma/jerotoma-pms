package com.jerotoma.services.addresses.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.addresses.Address;
import com.jerotoma.database.dao.addresses.AddressDao;
import com.jerotoma.services.AddressService;


@Service
@Transactional
public class AddressServiceImpl implements AddressService {

	@Autowired AddressDao addressDao; 
	
	@Override
	public Address findObject(Integer primaryKey) throws SQLException {
		return addressDao.findObject(primaryKey);
	}

	@Override
	public Address findObjectUniqueKey(String uniqueKey) throws SQLException {
		return addressDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public Address createObject(Address object) throws SQLException {
		return addressDao.createObject(object);
	}

	@Override
	public Address updateObject(Address object) throws SQLException {
		return addressDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(Address object) throws SQLException {
		return addressDao.deleteObject(object);
	}

	@Override
	public List<Address> loadList(QueryParam queryParam) throws SQLException {
		return addressDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return addressDao.loadMapList(queryParam);
	}

}
