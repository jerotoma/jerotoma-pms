package com.jerotoma.services.assemblers.academic;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.viewobjects.RoomVO;
import com.jerotoma.services.AssemblerService;

public interface AssemblerRoomService extends AssemblerService<RoomVO>{

	public List<RoomVO> findList() throws SQLException;

}
