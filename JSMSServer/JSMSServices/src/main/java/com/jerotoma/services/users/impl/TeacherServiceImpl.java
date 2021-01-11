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
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.constants.UserConstant;
import com.jerotoma.common.models.users.Teacher;
import com.jerotoma.database.dao.users.TeacherDao;
import com.jerotoma.services.users.TeacherService;
import com.jerotoma.services.utils.ServiceUtil;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {
	
	@Autowired TeacherDao teacherDao;
	Teacher teacher;

	@Override
	public Teacher findObject(Integer primaryKey) throws SQLException {
		return ServiceUtil.getEntity(teacherDao.findById(primaryKey));
		
	}

	@Override
	public Teacher findObjectUniqueKey(String uniqueKey) throws SQLException {
		throw new RuntimeException("Methon not implemented yet");
	}

	@Override
	public Teacher createObject(Teacher object) throws SQLException {		
		return teacherDao.save(object);
		
	}

	@Override
	public Teacher updateObject(Teacher object) throws SQLException {
		return teacherDao.save(object);
	}

	@Override
	public Boolean deleteObject(Teacher object) throws SQLException {
		teacherDao.delete(object);
		return true;	
	}

	@Override
	public List<Teacher> loadList(QueryParam queryParam) throws SQLException {
		if (queryParam == null) {
			return teacherDao.findAll();
		}		
		return teacherDao.findAll(ServiceUtil.getPageable(queryParam)).toList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		
		Map<String, Object> map = new HashMap<>();		
		Page<Teacher> pageTeacher = teacherDao.findAll(ServiceUtil.getPageable(queryParam));
		map.put(UserConstant.USERS, pageTeacher.toList());
		map.put(SystemConstant.COUNT, pageTeacher.getTotalElements());
		map.put(SystemConstant.PAGE_COUNT, pageTeacher.getTotalPages());	
		
		return map;
	}

}
