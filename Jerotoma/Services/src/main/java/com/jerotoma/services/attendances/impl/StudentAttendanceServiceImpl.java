package com.jerotoma.services.attendances.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.attendances.StudentAttendance;
import com.jerotoma.database.dao.attendances.StudentAttendanceDao;
import com.jerotoma.services.attendances.StudentAttendanceService;

@Transactional
@Service
public class StudentAttendanceServiceImpl implements StudentAttendanceService {

	@Autowired StudentAttendanceDao studentAttendanceDao;
	
	@Override
	public StudentAttendance findObject(Integer primaryKey) throws SQLException {		
		return studentAttendanceDao.findObject(primaryKey);
	}

	@Override
	public StudentAttendance findObjectUniqueKey(String uniqueKey) throws SQLException {
		return studentAttendanceDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public StudentAttendance createObject(StudentAttendance object) throws SQLException {
		return studentAttendanceDao.createObject(object);
	}

	@Override
	public StudentAttendance updateObject(StudentAttendance object) throws SQLException {
		return studentAttendanceDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(StudentAttendance object) throws SQLException {
		return studentAttendanceDao.deleteObject(object);
	}

	@Override
	public List<StudentAttendance> loadList(QueryParam queryParam) throws SQLException {
		return studentAttendanceDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return studentAttendanceDao.loadMapList(queryParam);
	}
	
	@Override
	public List<StudentAttendance> createBatch(List<StudentAttendance> studentAttendances) throws SQLException {
		return studentAttendanceDao.createBatch(studentAttendances);
	}

	@Override
	public List<StudentAttendance> updateBatch(List<StudentAttendance> studentAttendances) throws SQLException {
		return studentAttendanceDao.updateBatch(studentAttendances);
	}

	@Override
	public StudentAttendance getStudentAttendanceByStudentIdAndClassAttendanceId(Integer studentId,
			Integer classAttendanceId) throws SQLException {
		return studentAttendanceDao.getStudentAttendanceByStudentIdAndClassAttendanceId(studentId, classAttendanceId);
	}

}
