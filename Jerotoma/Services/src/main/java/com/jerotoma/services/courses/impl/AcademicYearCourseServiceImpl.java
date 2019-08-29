package com.jerotoma.services.courses.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.academic.AcademicYearCourse;
import com.jerotoma.database.dao.courses.AcademicYearCourseDao;
import com.jerotoma.services.courses.AcademicYearCourseService;

@Service
@Transactional
public class AcademicYearCourseServiceImpl implements AcademicYearCourseService {
	
	@Autowired AcademicYearCourseDao academicYearCourseDao;

	@Override
	public AcademicYearCourse findObject(Integer primaryKey) throws SQLException {
		return academicYearCourseDao.findObject(primaryKey);
	}

	@Override
	public AcademicYearCourse findObjectUniqueKey(String uniqueKey) throws SQLException {
		return academicYearCourseDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public AcademicYearCourse createObject(AcademicYearCourse object) throws SQLException {
		return academicYearCourseDao.createObject(object);
	}

	@Override
	public AcademicYearCourse updateObject(AcademicYearCourse object) throws SQLException {
		return academicYearCourseDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(AcademicYearCourse object) throws SQLException {
		return academicYearCourseDao.deleteObject(object);
	}

	@Override
	public List<AcademicYearCourse> loadList(QueryParam queryParam) throws SQLException {
		return academicYearCourseDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return academicYearCourseDao.loadMapList(queryParam);
	}

}
