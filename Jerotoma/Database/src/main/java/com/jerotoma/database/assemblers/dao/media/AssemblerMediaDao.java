package com.jerotoma.database.assemblers.dao.media;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.viewobjects.MediaVO;
import com.jerotoma.common.viewobjects.ResultBuilder;
import com.jerotoma.database.assemblers.AssemblerDao;

public interface AssemblerMediaDao extends AssemblerDao<MediaVO> {

	List<MediaVO> getMediaList() throws SQLException;

	ResultBuilder<MediaVO> searchMedia(QueryParam queryParam) throws SQLException;

}
