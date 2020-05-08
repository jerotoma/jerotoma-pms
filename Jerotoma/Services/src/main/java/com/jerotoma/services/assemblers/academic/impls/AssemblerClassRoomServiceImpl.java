package com.jerotoma.services.assemblers.academic.impls;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.viewobjects.RoomVO;
import com.jerotoma.database.assemblers.dao.academic.AssemblerClassRoomDao;
import com.jerotoma.services.assemblers.academic.AssemblerClassRoomService;

@Service
public class AssemblerClassRoomServiceImpl implements AssemblerClassRoomService {
	
	@Autowired AssemblerClassRoomDao assemblerClassRoomDao;
	
	@Override
	public RoomVO findObject(Integer primaryKey) throws SQLException {
		return assemblerClassRoomDao.findObject(primaryKey);
	}

	@Override
	public RoomVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		return assemblerClassRoomDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public List<RoomVO> loadList(QueryParam queryParam) throws SQLException {
		return assemblerClassRoomDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return assemblerClassRoomDao.loadMapList(queryParam);
	}

	@Override
	public Long countObject() throws SQLException {
		return assemblerClassRoomDao.countObject();
	}

}
