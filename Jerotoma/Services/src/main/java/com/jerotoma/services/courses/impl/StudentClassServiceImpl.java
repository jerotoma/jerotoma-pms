package com.jerotoma.services.courses.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.academic.StudentClass;
import com.jerotoma.database.dao.academic.StudentClassDao;
import com.jerotoma.services.courses.StudentClassService;

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
	public StudentClass findStudentClass(Integer classId, Integer studentAcademicLevelId, Integer academicYearId)
			throws SQLException {
		return studenClassDao.findStudentClass(classId, studentAcademicLevelId, academicYearId);
	}

	@Override
	public List<StudentClass> updateBatchObject(List<StudentClass> studentClasses) throws SQLException {
		return studenClassDao.updateBatchObject(studentClasses);
	}

}
