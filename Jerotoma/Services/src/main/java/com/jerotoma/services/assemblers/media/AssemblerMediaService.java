package com.jerotoma.services.assemblers.media;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.viewobjects.MediaVO;
import com.jerotoma.common.viewobjects.ResultBuilder;
import com.jerotoma.services.AssemblerService;

public interface AssemblerMediaService extends AssemblerService<MediaVO> {

	public List<MediaVO> getMediaList() throws SQLException;

	public ResultBuilder<MediaVO> searchMedia(QueryParam queryParam) throws SQLException;

}
