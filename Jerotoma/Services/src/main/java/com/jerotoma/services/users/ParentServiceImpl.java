package com.jerotoma.services.users;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.users.Parent;
import com.jerotoma.database.dao.users.ParentDao;

@Service
@Transactional
public class ParentServiceImpl implements ParentService {
	
	@Autowired ParentDao parentDao;

	@Override
	public Parent findObject(Integer primaryKey) throws SQLException {
		return parentDao.findObject(primaryKey);
	}

	@Override
	public Parent findObjectUniqueKey(String uniqueKey) throws SQLException {
		return parentDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public Parent createObject(Parent object) throws SQLException {
		return parentDao.createObject(object);
	}

	@Override
	public Parent updateObject(Parent object) throws SQLException {
		return parentDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(Parent object) throws SQLException {
		return parentDao.deleteObject(object);
	}

	@Override
	public List<Parent> loadList(QueryParam queryParam) throws SQLException {
		return parentDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return parentDao.loadMapList(queryParam);
	}

}
