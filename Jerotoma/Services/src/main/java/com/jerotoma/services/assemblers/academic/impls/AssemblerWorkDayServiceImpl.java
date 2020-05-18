package com.jerotoma.services.assemblers.academic.impls;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.viewobjects.WorkDayVO;
import com.jerotoma.database.assemblers.dao.schedules.AssemblerWorkDayDao;
import com.jerotoma.services.assemblers.academic.AssemblerWorkDayService;

@Service
public class AssemblerWorkDayServiceImpl implements AssemblerWorkDayService {
	
	@Autowired  AssemblerWorkDayDao assemblerWorkDayDao;

	@Override
	public WorkDayVO findObject(Integer primaryKey) throws SQLException {
		return assemblerWorkDayDao.findObject(primaryKey);
	}

	@Override
	public WorkDayVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		return assemblerWorkDayDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public List<WorkDayVO> loadList(QueryParam queryParam) throws SQLException {
		return assemblerWorkDayDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return assemblerWorkDayDao.loadMapList(queryParam);
	}

	@Override
	public Long countObject() throws SQLException {
		return assemblerWorkDayDao.countObject();
	}

	@Override
	public List<WorkDayVO> findAllWorkDays() throws SQLException {
		return assemblerWorkDayDao.findAllWorkDays();
	}

}
