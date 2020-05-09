package com.jerotoma.services.assemblers.academic.impls;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.academic.Department;
import com.jerotoma.database.dao.courses.DepartmentDao;
import com.jerotoma.services.assemblers.academic.DepartmentService;

@Transactional
@Service
public class DepartmentServiceImpl implements DepartmentService {
	
	DepartmentDao departmentDao;

	@Override
	public Department findObject(Integer primaryKey) throws SQLException {
		return departmentDao.findObject(primaryKey);
	}

	@Override
	public Department findObjectUniqueKey(String uniqueKey) throws SQLException {
		return departmentDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public Department createObject(Department object) throws SQLException {
		return departmentDao.createObject(object);
	}

	@Override
	public Department updateObject(Department object) throws SQLException {
		return departmentDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(Department object) throws SQLException {
		return departmentDao.deleteObject(object);
	}

	@Override
	public List<Department> loadList(QueryParam queryParam) throws SQLException {
		return departmentDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return departmentDao.loadMapList(queryParam);
	}

}
