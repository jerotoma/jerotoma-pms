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
		return null;
	}

	@Override
	public WorkDay findObjectUniqueKey(String uniqueKey) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WorkDay createObject(WorkDay object) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WorkDay updateObject(WorkDay object) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteObject(WorkDay object) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkDay> loadList(QueryParam queryParam) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
