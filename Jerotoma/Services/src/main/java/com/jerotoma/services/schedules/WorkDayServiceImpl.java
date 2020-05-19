package com.jerotoma.services.schedules;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.schedules.WorkDay;
import com.jerotoma.database.dao.schedules.WorkDayDao;

@Transactional
@Service
public class WorkDayServiceImpl implements WorkDayService {
	
	@Autowired WorkDayDao  workDayDao;

	@Override
	public WorkDay findObject(Integer primaryKey) throws SQLException {
		return workDayDao.findObject(primaryKey);
	}

	@Override
	public WorkDay findObjectUniqueKey(String uniqueKey) throws SQLException {
		return workDayDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public WorkDay createObject(WorkDay object) throws SQLException {
		return workDayDao.createObject(object);
	}

	@Override
	public WorkDay updateObject(WorkDay object) throws SQLException {
		return workDayDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(WorkDay object) throws SQLException {
		return workDayDao.deleteObject(object);
	}

	@Override
	public List<WorkDay> loadList(QueryParam queryParam) throws SQLException {
		return workDayDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return workDayDao.loadMapList(queryParam);
	}

}
