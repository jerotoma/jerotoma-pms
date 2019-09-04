package com.jerotoma.services.assemblers.academic.impls;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.viewobjects.ClassRoomResourceVO;
import com.jerotoma.database.assemblers.dao.academic.AssemblerClassRoomResourceDao;
import com.jerotoma.services.assemblers.academic.AssemblerClassRoomResourceService;

@Service
public class AssemblerClassRoomResourceServiceImpl implements AssemblerClassRoomResourceService {
	
	@Autowired AssemblerClassRoomResourceDao assemblerClassRoomResourceDao;
	
	@Override
	public ClassRoomResourceVO findObject(Integer primaryKey) throws SQLException {		
		return assemblerClassRoomResourceDao.findObject(primaryKey);
	}

	@Override
	public ClassRoomResourceVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		return assemblerClassRoomResourceDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public List<ClassRoomResourceVO> loadList(QueryParam queryParam) throws SQLException {
		return assemblerClassRoomResourceDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return assemblerClassRoomResourceDao.loadMapList(queryParam);
	}

	@Override
	public Long countObject() throws SQLException {
		return assemblerClassRoomResourceDao.countObject();
	}

}
