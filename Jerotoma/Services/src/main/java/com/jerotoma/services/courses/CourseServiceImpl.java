package com.jerotoma.services.courses;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.courses.Course;
import com.jerotoma.database.dao.courses.CourseDao;

@Service
public class CourseServiceImpl implements CourseService {
	
	@Autowired CourseDao courseDao;

	@Override
	public Course findObject(Integer primaryKey) throws SQLException {
		return courseDao.findObject(primaryKey);
	}

	@Override
	public Course findObjectUniqueKey(String uniqueKey) throws SQLException {
		return courseDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public Course createObject(Course object) throws SQLException {
		return courseDao.createObject(object);
	}

	@Override
	public Course updateObject(Course object) throws SQLException {
		return courseDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(Course object) throws SQLException {
		return courseDao.deleteObject(object);
	}

	@Override
	public List<Course> loadList(QueryParam queryParam) throws SQLException {
		return courseDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return courseDao.loadMapList(queryParam);
	}

}
