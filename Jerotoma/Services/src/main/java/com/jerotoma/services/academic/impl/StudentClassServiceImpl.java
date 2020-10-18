package com.jerotoma.services.academic.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.users.students.StudentClass;
import com.jerotoma.database.dao.academic.StudentClassDao;
import com.jerotoma.services.academic.StudentClassService;

@Service
@Transactional
public class StudentClassServiceImpl implements StudentClassService {
	
	@Autowired StudentClassDao studenClassDao;

	@Override
	public StudentClass findObject(Integer primaryKey) throws SQLException {
		return studenClassDao.findObject(primaryKey);
	}

	@Override
	public StudentClass findObjectUniqueKey(String uniqueKey) throws SQLException {
		return studenClassDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public StudentClass createObject(StudentClass object) throws SQLException {
		return studenClassDao.createObject(object);
	}

	@Override
	public StudentClass updateObject(StudentClass object) throws SQLException {
		return studenClassDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(StudentClass object) throws SQLException {
		return studenClassDao.deleteObject(object);
	}

	@Override
	public List<StudentClass> loadList(QueryParam queryParam) throws SQLException {
		return studenClassDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return studenClassDao.loadMapList(queryParam);
	}

	@Override
	public List<StudentClass> createBatchObject(List<StudentClass> studentClasses) throws SQLException {
		return studenClassDao.createBatchObject(studentClasses);
	}

	@Override
	public StudentClass findStudentClass(Integer classId, Integer studentAcademicLevelId)
			throws SQLException {
		return studenClassDao.findStudentClass(classId, studentAcademicLevelId);
	}

	@Override
	public List<StudentClass> updateBatchObject(List<StudentClass> studentClasses) throws SQLException {
		return studenClassDao.updateBatchObject(studentClasses);
	}

	@Override
	public boolean deleteStudentClass(Integer studentAcademicLevelId, Integer jClassId) throws SQLException {
		return studenClassDao.deleteStudentClass(studentAcademicLevelId, jClassId);
	}

}
