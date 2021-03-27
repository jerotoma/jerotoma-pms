package com.jerotoma.services.academic.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.ClassConstant;
import com.jerotoma.common.constants.ErrorMessageConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.models.academic.Class;
import com.jerotoma.database.dao.academic.ClassDao;
import com.jerotoma.services.academic.ClassService;
import com.jerotoma.services.utils.ServiceUtil;

@Service
@Transactional
public class ClassServiceImpl implements ClassService{
	
	@Autowired ClassDao classDao;

	@Override
	public Class findObject(Integer primaryKey) throws SQLException {
		return ServiceUtil.getEntity(classDao.findById(primaryKey));
	}

	@Override
	public Class findObjectUniqueKey(String uniqueKey) throws SQLException {
		throw new RuntimeException(ErrorMessageConstant.METHOD_NOT_IMPLEMENTED);
	}

	@Override
	public Class createObject(Class object) throws SQLException {
		return classDao.save(object);
	}

	@Override
	public Class updateObject(Class object) throws SQLException {
		return classDao.save(object);
	}

	@Override
	public Boolean deleteObject(Class object) throws SQLException {
		classDao.delete(object);
		return true;
	}

	@Override
	public List<Class> loadList(QueryParam queryParam) throws SQLException {
		if (queryParam == null) {
			return classDao.findAll();
		}		
		return classDao.findAll(ServiceUtil.getPageable(queryParam)).toList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();		
		Page<Class> pageClass = classDao.findAll(ServiceUtil.getPageable(queryParam));
		map.put(ClassConstant.CLASSES, pageClass.toList());
		map.put(SystemConstant.COUNT, pageClass.getTotalElements());
		map.put(SystemConstant.PAGE_COUNT, pageClass.getTotalPages());	
		
		return map;
	}

	@Override
	public Integer countTeacherClasses(Integer teacherId) {
		
		return null;
	}

}
