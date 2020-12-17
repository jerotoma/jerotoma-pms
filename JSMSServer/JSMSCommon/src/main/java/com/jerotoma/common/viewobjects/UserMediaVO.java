package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMediaVO {
	Integer id;
	Integer userId;
	Integer mediaId;
	
	public UserMediaVO(ResultSet rs) throws SQLException {
		this.id = rs.getInt("id");
		this.userId = rs.getInt("user_id");
		this.mediaId = rs.getInt("media_id");
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getMediaId() {
		return mediaId;
	}
	public void setMediaId(Integer mediaId) {
		this.mediaId = mediaId;
	}
	
}
