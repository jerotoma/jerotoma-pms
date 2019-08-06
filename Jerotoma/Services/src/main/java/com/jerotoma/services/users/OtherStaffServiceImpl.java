package com.jerotoma.services.users;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.users.OtherStaff;
import com.jerotoma.database.dao.users.OtherStaffDao;

@Service
@Transactional
public class OtherStaffServiceImpl implements OtherStaffService {
	
	@Autowired OtherStaffDao otherStaffDao;

	@Override
	public OtherStaff findObject(Integer primaryKey) throws SQLException {
		return otherStaffDao.findObject(primaryKey);
	}

	@Override
	public OtherStaff findObjectUniqueKey(String uniqueKey) throws SQLException {
		return otherStaffDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public OtherStaff createObject(OtherStaff object) throws SQLException {
		return otherStaffDao.createObject(object);
	}

	@Override
	public OtherStaff updateObject(OtherStaff object) throws SQLException {
		return otherStaffDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(OtherStaff object) throws SQLException {
		return otherStaffDao.deleteObject(object);
	}

	@Override
	public List<OtherStaff> loadList(QueryParam queryParam) throws SQLException {
		return otherStaffDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return otherStaffDao.loadMapList(queryParam);
	}

}
