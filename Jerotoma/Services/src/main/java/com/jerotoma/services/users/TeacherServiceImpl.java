package com.jerotoma.services.users;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.users.Teacher;
import com.jerotoma.database.dao.users.TeacherDao;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService{
	
	@Autowired TeacherDao teacherDao;

	@Override
	public Teacher findObject(Integer primaryKey) throws SQLException {
		return teacherDao.findObject(primaryKey);
	}

	@Override
	public Teacher findObjectUniqueKey(String uniqueKey) throws SQLException {
		return teacherDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public Teacher createObject(Teacher object) throws SQLException {
		return teacherDao.createObject(object);
	}

	@Override
	public Teacher updateObject(Teacher object) throws SQLException {
		return teacherDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(Teacher object) throws SQLException {
		return teacherDao.deleteObject(object);
	}

	@Override
	public List<Teacher> loadList(QueryParam queryParam) throws SQLException {
		return teacherDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return teacherDao.loadMapList(queryParam);
	}

}
