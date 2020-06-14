package com.jerotoma.services.attendances.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.attendances.AttendanceStatus;
import com.jerotoma.database.dao.attendances.AttendanceStatusDao;
import com.jerotoma.services.attendances.AttendanceStatusService;


@Transactional
@Service
public class AttendanceStatusServiceImpl implements AttendanceStatusService {

	@Autowired AttendanceStatusDao attendanceStatusDao;
	
	@Override
	public AttendanceStatus findObject(Integer primaryKey) throws SQLException {		
		return attendanceStatusDao.findObject(primaryKey);
	}

	@Override
	public AttendanceStatus findObjectUniqueKey(String uniqueKey) throws SQLException {
		return attendanceStatusDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public AttendanceStatus createObject(AttendanceStatus object) throws SQLException {
		return attendanceStatusDao.createObject(object);
	}

	@Override
	public AttendanceStatus updateObject(AttendanceStatus object) throws SQLException {
		return attendanceStatusDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(AttendanceStatus object) throws SQLException {
		return attendanceStatusDao.deleteObject(object);
	}

	@Override
	public List<AttendanceStatus> loadList(QueryParam queryParam) throws SQLException {
		return attendanceStatusDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return attendanceStatusDao.loadMapList(queryParam);
	}

	@Override
	public List<AttendanceStatus> getAll() throws SQLException {
		return attendanceStatusDao.getAll();
	}

}
