package com.jerotoma.services.assemblers.attendances.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.viewobjects.ClassAttendanceVO;
import com.jerotoma.database.assemblers.dao.attendances.AssemblerClassAttendanceDao;
import com.jerotoma.services.assemblers.attendances.AssemblerClassAttendanceService;

@Service
public class AssemblerClassAttendanceServiceImpl implements AssemblerClassAttendanceService {
	
	@Autowired AssemblerClassAttendanceDao assemblerClassAttendanceDao;

	@Override
	public ClassAttendanceVO findObject(Integer primaryKey) throws SQLException {
		return assemblerClassAttendanceDao.findObject(primaryKey);
	}

	@Override
	public ClassAttendanceVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		return assemblerClassAttendanceDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public List<ClassAttendanceVO> loadList() throws SQLException {
		return assemblerClassAttendanceDao.loadList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return assemblerClassAttendanceDao.loadMapList(queryParam);
	}

	@Override
	public Long countObject() throws SQLException {
		return assemblerClassAttendanceDao.countObject();
	}

	@Override
	public List<ClassAttendanceVO> getAll() throws SQLException {		
		return assemblerClassAttendanceDao.getAll();
	}

}
