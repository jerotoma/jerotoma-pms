package com.jerotoma.services.media;

import java.sql.SQLException;

import com.jerotoma.common.models.media.UserMedia;
import com.jerotoma.services.BaseService;

public interface UserMediaService extends BaseService<UserMedia> {
	public UserMedia findUserMediaByIDs(Integer mediaId, Integer userId) throws SQLException;

}
