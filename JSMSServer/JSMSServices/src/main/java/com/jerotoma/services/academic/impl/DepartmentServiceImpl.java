package com.jerotoma.services.academic.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.ClassConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.models.academic.Department;
import com.jerotoma.database.dao.academic.DepartmentDao;
import com.jerotoma.services.academic.DepartmentService;
import com.jerotoma.services.utils.ServiceUtil;

@Transactional
@Service
public class DepartmentServiceImpl implements DepartmentService {
	
	@Autowired DepartmentDao departmentDao;

	@Override
	public Department findObject(Integer primaryKey) throws SQLException {
		return ServiceUtil.getEntity(departmentDao.findById(primaryKey));
	}

	@Override
	public Department findObjectUniqueKey(String uniqueKey) throws SQLException {
		throw new RuntimeException("Methon not implemented yet");
	}

	@Override
	public Department createObject(Department object) throws SQLException {
		return departmentDao.save(object);
	}

	@Override
	public Department updateObject(Department object) throws SQLException {
		return departmentDao.save(object);
	}

	@Override
	public Boolean deleteObject(Department object) throws SQLException {
		departmentDao.delete(object);
		return true;
	}

	@Override
	public List<Department> loadList(QueryParam queryParam) throws SQLException {
		if (queryParam == null) {
			return departmentDao.findAll();
		}		
		return departmentDao.findAll(ServiceUtil.getPageable(queryParam)).toList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		
		Map<String, Object> map = new HashMap<>();		
		Page<Department> pageDepartment = departmentDao.findAll(ServiceUtil.getPageable(queryParam));
		map.put(ClassConstant.CLASSES, pageDepartment.toList());
		map.put(SystemConstant.COUNT, pageDepartment.getTotalElements());
		map.put(SystemConstant.PAGE_COUNT, pageDepartment.getTotalPages());	
		
		return map;
	}

}
