package com.jerotoma.services.users;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.users.Student;
import com.jerotoma.database.dao.users.StudentDao;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
	
	@Autowired StudentDao studentDao;	

	@Override
	public Student findObject(Integer primaryKey) throws SQLException {
		return studentDao.findObject(primaryKey);
	}

	@Override
	public Student findObjectUniqueKey(String uniqueKey) throws SQLException {
		return studentDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public Student createObject(Student object) throws SQLException {
		return studentDao.createObject(object);
	}

	@Override
	public Student updateObject(Student object) throws SQLException {
		return studentDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(Student object) throws SQLException {
		return studentDao.deleteObject(object);
	}

	@Override
	public List<Student> loadList(QueryParam queryParam) throws SQLException {
		return studentDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return studentDao.loadMapList(queryParam);
	}

}
