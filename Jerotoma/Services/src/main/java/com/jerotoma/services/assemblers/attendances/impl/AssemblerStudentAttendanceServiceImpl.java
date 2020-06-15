package com.jerotoma.services.assemblers.attendances.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.viewobjects.StudentAttendanceVO;
import com.jerotoma.database.assemblers.dao.attendances.AssemblerStudentAttendanceDao;
import com.jerotoma.services.assemblers.attendances.AssemblerStudentAttendanceService;

@Service
public class AssemblerStudentAttendanceServiceImpl implements AssemblerStudentAttendanceService {
	
	@Autowired AssemblerStudentAttendanceDao assemblerStudentAttendanceDao;

	@Override
	public StudentAttendanceVO findObject(Integer primaryKey) throws SQLException {
		return assemblerStudentAttendanceDao.findObject(primaryKey);
	}

	@Override
	public StudentAttendanceVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		return assemblerStudentAttendanceDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public List<StudentAttendanceVO> loadList(QueryParam queryParam) throws SQLException {
		return assemblerStudentAttendanceDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return assemblerStudentAttendanceDao.loadMapList(queryParam);
	}

	@Override
	public Long countObject() throws SQLException {
		return assemblerStudentAttendanceDao.countObject();
	}

}
