package com.jerotoma.services.attendances.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.attendances.ClassAttendance;
import com.jerotoma.database.dao.attendances.ClassAttendanceDao;
import com.jerotoma.services.attendances.ClassAttendanceService;

@Transactional
@Service
public class ClassAttendanceServiceImpl implements ClassAttendanceService {

	@Autowired ClassAttendanceDao classAttendanceDao;
	
	@Override
	public ClassAttendance findObject(Integer primaryKey) throws SQLException {		
		return classAttendanceDao.findObject(primaryKey);
	}

	@Override
	public ClassAttendance findObjectUniqueKey(String uniqueKey) throws SQLException {
		return classAttendanceDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public ClassAttendance createObject(ClassAttendance object) throws SQLException {
		return classAttendanceDao.createObject(object);
	}

	@Override
	public ClassAttendance updateObject(ClassAttendance object) throws SQLException {
		return classAttendanceDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(ClassAttendance object) throws SQLException {
		return classAttendanceDao.deleteObject(object);
	}

	@Override
	public List<ClassAttendance> loadList(QueryParam queryParam) throws SQLException {
		return classAttendanceDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return classAttendanceDao.loadMapList(queryParam);
	}

}
