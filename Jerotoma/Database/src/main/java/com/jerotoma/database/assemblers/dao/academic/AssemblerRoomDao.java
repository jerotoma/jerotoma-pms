package com.jerotoma.database.assemblers.dao.academic;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.viewobjects.RoomVO;
import com.jerotoma.database.assemblers.AssemblerDao;

public interface AssemblerRoomDao extends AssemblerDao<RoomVO>{
	public List<RoomVO> findList() throws SQLException;
	public List<RoomVO> getRoomsByCapacity(Integer capacity) throws SQLException;

}
