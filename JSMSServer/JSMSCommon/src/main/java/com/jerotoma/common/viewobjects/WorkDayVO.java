package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.Date;
import java.util.List;

import com.jerotoma.common.constants.WorkDayConstant;
import com.jerotoma.common.schedules.MeetingTime;

public class WorkDayVO {
	private Integer id;
		
	private Integer dayId;
	
	private DayOfWeek day;
	
	private List<MeetingTime> meetingTimes;
		
	private Date createdOn;
	
	private Date updatedOn;
	
	public WorkDayVO(ResultSet rs) throws SQLException {	
		this.id = rs.getInt(WorkDayConstant.ID);
		this.day = DayOfWeek.of(rs.getInt(WorkDayConstant.DAY_ID));
		this.dayId = day.getValue();
		this.createdOn = rs.getDate(WorkDayConstant.CREATED_ON);
		this.updatedOn = rs.getDate(WorkDayConstant.UPDATED_ON);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDayId() {
		return dayId;
	}

	public void setDayId(Integer dayId) {
		this.dayId = dayId;
	}

	public DayOfWeek getDay() {
		return day;
	}

	public void setDay(DayOfWeek day) {
		this.day = day;
	}

	public List<MeetingTime> getMeetingTimes() {
		return meetingTimes;
	}

	public void setMeetingTimes(List<MeetingTime> meetingTimes) {
		this.meetingTimes = meetingTimes;
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
