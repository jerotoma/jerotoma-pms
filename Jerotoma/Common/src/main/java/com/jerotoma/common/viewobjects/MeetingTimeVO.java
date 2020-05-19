package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.jerotoma.common.constants.MeetingTimeConstant;
import com.jerotoma.common.schedules.Time;

public class MeetingTimeVO {
	private Integer id;
	
	private Integer workDayId;
	
	private String time;
	
	private Time startTime;
	
	private Time endTime;
	
	private Date createdOn;
	
	private Date updatedOn;
	
	private WorkDayVO workDay;
	
	public MeetingTimeVO(Integer id, String time, Date createdOn, Date updatedOn) {
		super();
		this.id = id;
		this.time = time;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
	}
	
	public MeetingTimeVO(ResultSet rs) throws SQLException {
		this.id = rs.getInt(MeetingTimeConstant.ID);
		this.workDayId = rs.getInt(MeetingTimeConstant.WORK_DAY_ID);
		this.time =  rs.getString(MeetingTimeConstant.TIME);
		this.startTime =  new Time(rs.getString(MeetingTimeConstant.START_TIME));
		this.endTime =  new Time(rs.getString(MeetingTimeConstant.END_TIME));
		this.updatedOn = rs.getDate(MeetingTimeConstant.UPDATED_ON);
		this.createdOn = rs.getDate(MeetingTimeConstant.CREATED_ON);
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
	
	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
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

	public WorkDayVO getWorkDay() {
		return workDay;
	}

	public void setWorkDay(WorkDayVO workDay) {
		this.workDay = workDay;
	}

	public Integer getWorkDayId() {
		return workDayId;
	}

	public void setWorkDayId(Integer workDayId) {
		this.workDayId = workDayId;
	}
}
