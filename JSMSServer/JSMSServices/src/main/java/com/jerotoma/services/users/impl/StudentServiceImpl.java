package com.jerotoma.services.users.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.StudentConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.models.users.students.Student;
import com.jerotoma.database.dao.users.StudentDao;
import com.jerotoma.services.users.StudentService;
import com.jerotoma.services.utils.ServiceUtil;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
	
	@Autowired StudentDao studentDao;	

	@Override
	public Student findObject(Integer primaryKey) throws SQLException {
		return studentDao.getOne(primaryKey);
	}

	@Override
	public Student findObjectUniqueKey(String uniqueKey) throws SQLException {
		throw new RuntimeException("This methond has not been implemented yet");
	}

	@Override
	public Student createObject(Student object) throws SQLException {
		return studentDao.save(object);
	}

	@Override
	public Student updateObject(Student object) throws SQLException {
		return studentDao.save(object);
	}

	@Override
	public Boolean deleteObject(Student object) throws SQLException {
		studentDao.delete(object);
		return true;
	}

	@Override
	public List<Student> loadList(QueryParam queryParam) throws SQLException {
		if (queryParam == null) {
			return studentDao.findAll();
		}		
		return studentDao.findAll(ServiceUtil.getPageable(queryParam)).toList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();		
		Page<Student> pageStudent = studentDao.findAll(ServiceUtil.getPageable(queryParam));
		map.put(StudentConstant.STUDENTS, pageStudent.toList());
		map.put(SystemConstant.COUNT, pageStudent.getTotalElements());
		map.put(SystemConstant.PAGE_COUNT, pageStudent.getTotalPages());	
		
		return map;
	}

}
