package com.jerotoma.services.assemblers.media;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.viewobjects.MediaVO;
import com.jerotoma.services.AssemblerService;

public interface AssemblerMediaService extends AssemblerService<MediaVO> {

	public List<MediaVO> getMediaList() throws SQLException; 	

}
