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
import com.jerotoma.common.constants.ErrorMessageConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.constants.UserConstant;
import com.jerotoma.common.models.users.Parent;
import com.jerotoma.database.dao.users.ParentDao;
import com.jerotoma.services.users.ParentService;
import com.jerotoma.services.utils.ServiceUtil;

@Service
@Transactional
public class ParentServiceImpl implements ParentService {
	
	@Autowired ParentDao parentDao;

	@Override
	public Parent findObject(Integer primaryKey) throws SQLException {
		return ServiceUtil.getEntity(parentDao.findById(primaryKey));
	}

	@Override
	public Parent findObjectUniqueKey(String uniqueKey) throws SQLException {
		throw new RuntimeException(ErrorMessageConstant.METHOD_NOT_IMPLEMENTED);
	}

	@Override
	public Parent createObject(Parent object) throws SQLException {
		return parentDao.save(object);
	}

	@Override
	public Parent updateObject(Parent object) throws SQLException {
		return parentDao.save(object);
	}

	@Override
	public Boolean deleteObject(Parent object) throws SQLException {
		parentDao.delete(object);
		return true;
	}

	@Override
	public List<Parent> loadList(QueryParam queryParam) throws SQLException {
		if (queryParam == null) {
			return parentDao.findAll();
		}		
		return parentDao.findAll(ServiceUtil.getPageable(queryParam)).toList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();		
		Page<Parent> pageParent = parentDao.findAll(ServiceUtil.getPageable(queryParam));
		map.put(UserConstant.USERS, pageParent.toList());
		map.put(SystemConstant.COUNT, pageParent.getTotalElements());
		map.put(SystemConstant.PAGE_COUNT, pageParent.getTotalPages());	
		
		return map;
	}

}
