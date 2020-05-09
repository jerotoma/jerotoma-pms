package com.jerotoma.services.assemblers.academic.impls;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.viewobjects.RoomVO;
import com.jerotoma.database.assemblers.dao.academic.AssemblerRoomDao;
import com.jerotoma.services.assemblers.academic.AssemblerRoomService;

@Service
public class AssemblerRoomServiceImpl implements AssemblerRoomService {
	
	@Autowired AssemblerRoomDao assemblerRoomDao;
	
	@Override
	public RoomVO findObject(Integer primaryKey) throws SQLException {
		return assemblerRoomDao.findObject(primaryKey);
	}

	@Override
	public RoomVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		return assemblerRoomDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public List<RoomVO> loadList(QueryParam queryParam) throws SQLException {
		return assemblerRoomDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return assemblerRoomDao.loadMapList(queryParam);
	}

	@Override
	public Long countObject() throws SQLException {
		return assemblerRoomDao.countObject();
	}

}
