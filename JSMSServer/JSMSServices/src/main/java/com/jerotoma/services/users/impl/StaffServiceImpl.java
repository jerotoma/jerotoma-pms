package com.jerotoma.services.users.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.ErrorMessageConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.constants.UserConstant;
import com.jerotoma.common.models.users.Staff;
import com.jerotoma.database.dao.users.StaffDao;
import com.jerotoma.services.users.StaffService;
import com.jerotoma.services.utils.ServiceUtil;

@Service
@Transactional
public class StaffServiceImpl implements StaffService {
	
	@Autowired StaffDao staffDao;

	@Override
	public Staff findObject(Integer primaryKey) throws SQLException {
		return ServiceUtil.getEntity(staffDao.findById(primaryKey));
	}

	@Override
	public Staff findObjectUniqueKey(String uniqueKey) throws SQLException {
		throw new RuntimeException(ErrorMessageConstant.METHOD_NOT_IMPLEMENTED);
	}

	@Override
	public Staff createObject(Staff object) throws SQLException {
		return staffDao.save(object);
	}

	@Override
	public Staff updateObject(Staff object) throws SQLException {
		return staffDao.save(object);
	}

	@Override
	public Boolean deleteObject(Staff object) throws SQLException {
		staffDao.delete(object);
		return true;
	}

	@Override
	public List<Staff> loadList(QueryParam queryParam) throws SQLException {
		if (queryParam == null) {
			return staffDao.findAll();
		}		
		return staffDao.findAll(ServiceUtil.getPageable(queryParam)).toList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();		
		Page<Staff> pageStaff = staffDao.findAll(ServiceUtil.getPageable(queryParam));
		map.put(UserConstant.USERS, pageStaff.toList());
		map.put(SystemConstant.COUNT, pageStaff.getTotalElements());
		map.put(SystemConstant.PAGE_COUNT, pageStaff.getTotalPages());	
		
		return map;
	}

	@Override
	public Long countObject() throws SQLException {
		return staffDao.count();
	}

}
