package com.jerotoma.services.courses.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.academic.SchoolClassAcademicYearCourse;
import com.jerotoma.database.dao.courses.SchoolClassAcademicYearCourseDao;
import com.jerotoma.services.courses.SchoolClassAcademicYearCourseService;

@Service
@Transactional
public class SchoolClassAcademicYearCourseServiceImpl implements SchoolClassAcademicYearCourseService{
	
	@Autowired SchoolClassAcademicYearCourseDao schoolClassAcademicYearCourseDao;

	@Override
	public SchoolClassAcademicYearCourse findObject(Integer primaryKey) throws SQLException {
		return schoolClassAcademicYearCourseDao.findObject(primaryKey);
	}

	@Override
	public SchoolClassAcademicYearCourse findObjectUniqueKey(String uniqueKey) throws SQLException {
		return schoolClassAcademicYearCourseDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public SchoolClassAcademicYearCourse createObject(SchoolClassAcademicYearCourse object) throws SQLException {
		return schoolClassAcademicYearCourseDao.createObject(object);
	}

	@Override
	public SchoolClassAcademicYearCourse updateObject(SchoolClassAcademicYearCourse object) throws SQLException {
		return schoolClassAcademicYearCourseDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(SchoolClassAcademicYearCourse object) throws SQLException {
		return schoolClassAcademicYearCourseDao.deleteObject(object);
	}

	@Override
	public List<SchoolClassAcademicYearCourse> loadList(QueryParam queryParam) throws SQLException {
		return schoolClassAcademicYearCourseDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return schoolClassAcademicYearCourseDao.loadMapList(queryParam);
	}

}
