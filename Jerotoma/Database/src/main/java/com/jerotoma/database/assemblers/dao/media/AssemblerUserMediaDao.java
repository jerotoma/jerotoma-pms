package com.jerotoma.database.assemblers.dao.media;


import com.jerotoma.common.viewobjects.UserMediaVO;
import com.jerotoma.database.assemblers.AssemblerDao;

public interface AssemblerUserMediaDao extends AssemblerDao<UserMediaVO> {
	public boolean doesUserMediaExist(Integer mediaId, Integer userId);

}
