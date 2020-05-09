package com.jerotoma.services.assemblers.academic.impls;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.viewobjects.DepartmentVO;
import com.jerotoma.database.assemblers.dao.academic.AssemblerDepartmentDao;
import com.jerotoma.services.assemblers.academic.AssemblerDepartmentService;

@Service
public class AssemblerDepartmentServiceImpl implements AssemblerDepartmentService {
	
	AssemblerDepartmentDao assemblerDepartmentDao;

	@Override
	public DepartmentVO findObject(Integer primaryKey) throws SQLException {		
		return assemblerDepartmentDao.findObject(primaryKey);
	}

	@Override
	public DepartmentVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		return assemblerDepartmentDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public List<DepartmentVO> loadList(QueryParam queryParam) throws SQLException {
		return assemblerDepartmentDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return assemblerDepartmentDao.loadMapList(queryParam);
	}

	@Override
	public Long countObject() throws SQLException {
		return assemblerDepartmentDao.countObject();
	}

}
