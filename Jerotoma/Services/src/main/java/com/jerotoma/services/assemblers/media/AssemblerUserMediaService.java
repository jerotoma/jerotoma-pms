package com.jerotoma.services.assemblers.media;

import com.jerotoma.common.viewobjects.UserMediaVO;
import com.jerotoma.services.AssemblerService;

public interface AssemblerUserMediaService extends AssemblerService<UserMediaVO> { 	
	public boolean doesUserMediaExist(Integer mediaId, Integer userId);

}


