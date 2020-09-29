package com.jerotoma.services.students.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.academic.StudentAcademicLevel;
import com.jerotoma.common.viewobjects.StudentVO;
import com.jerotoma.database.dao.academic.StudentAcademicLevelDao;
import com.jerotoma.services.students.StudentAcademicLevelService;

@Service
@Transactional
public class StudentAcademicLevelServiceImpl implements StudentAcademicLevelService {
	
	@Autowired StudentAcademicLevelDao studentAcademicLevelDao;

	@Override
	public StudentAcademicLevel findObject(Integer primaryKey) throws SQLException {
		return studentAcademicLevelDao.findObject(primaryKey);
	}

	@Override
	public StudentAcademicLevel findObjectUniqueKey(String uniqueKey) throws SQLException {
		return studentAcademicLevelDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public StudentAcademicLevel createObject(StudentAcademicLevel object) throws SQLException {
		return studentAcademicLevelDao.createObject(object);
	}

	@Override
	public StudentAcademicLevel updateObject(StudentAcademicLevel object) throws SQLException {
		return studentAcademicLevelDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(StudentAcademicLevel object) throws SQLException {
		return studentAcademicLevelDao.deleteObject(object);
	}

	@Override
	public List<StudentAcademicLevel> loadList(QueryParam queryParam) throws SQLException {
		return studentAcademicLevelDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return studentAcademicLevelDao.loadMapList(queryParam);
	}

	@Override
	public List<StudentAcademicLevel> createBatchObject(List<StudentAcademicLevel> studentClasses) throws SQLException {
		return studentAcademicLevelDao.createBatchObject(studentClasses);
	}

	@Override
	public StudentAcademicLevel findStudentAcademicLevel(Integer studentId, Integer academicLevelId) throws SQLException {
		return studentAcademicLevelDao.findStudentAcademicLevel(studentId, academicLevelId);
	}

}
