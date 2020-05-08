package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class MeetingTimeVO {
	private Integer id;
	
	private String time;
	
	private Date createdOn;
	
	private Date updatedOn;
	
	public MeetingTimeVO(Integer id, String time, Date createdOn, Date updatedOn) {
		super();
		this.id = id;
		this.time = time;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
	}
	
	public MeetingTimeVO(ResultSet rs) throws SQLException {
		this.id = rs.getInt("id");
		this.time =  rs.getString("time");		
		this.createdOn = rs.getDate("created_on");
		this.updatedOn = rs.getDate("updated_on");
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
}
