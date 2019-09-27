package com.jerotoma.services.assemblers.impls;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.viewobjects.ParentVO;
import com.jerotoma.database.assemblers.dao.AssemblerParentDao;
import com.jerotoma.services.assemblers.AssemblerParentService;


@Service
public class AssemblerParentServiceImpl implements AssemblerParentService {
	
	@Autowired AssemblerParentDao assemblerParentDao;

	@Override
	public ParentVO findObject(Integer primaryKey) throws SQLException {
		return assemblerParentDao.findObject(primaryKey);
	}

	@Override
	public ParentVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		return assemblerParentDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public List<ParentVO> loadList(QueryParam queryParam) throws SQLException {
		return assemblerParentDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return assemblerParentDao.loadMapList(queryParam);
	}

	@Override
	public Long countObject() throws SQLException {
		return assemblerParentDao.countObject();
	}

	@Override
	public List<ParentVO> search(QueryParam queryParam) throws SQLException {
		return assemblerParentDao.search(queryParam);
	}

}
