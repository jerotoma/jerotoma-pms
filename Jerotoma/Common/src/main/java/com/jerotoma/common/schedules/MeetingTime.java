package com.jerotoma.common.schedules;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.jerotoma.common.constants.DatabaseConstant;
import com.jerotoma.common.constants.MeetingTimeConstant;

@Entity
@Table(name = DatabaseConstant.TABLES.MEETING_TIMES)
public class MeetingTime {
	
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE, 
			generator = DatabaseConstant.TABLES.MEETING_TIMES + "_generator")
	@SequenceGenerator(
			name = DatabaseConstant.TABLES.MEETING_TIMES + "_generator", 
			sequenceName = DatabaseConstant.TABLES.MEETING_TIMES + "_id_seq", 
			allocationSize=1)
	@Column
	Integer id;
	
	@Transient
	Integer workDayId;
	
	@ManyToOne
	@JoinColumn(name="work_day_id")
	WorkDay workDay;
	
	@Column
	String time;
	
	@Column(name = "start_time")
	LocalTime startTime;
	
	@Column(name = "end_time")
	LocalTime endTime;
	
	@Column(name="created_on")
	private Date createdOn;
	
	@Column(name="updated_on")
	private Date updatedOn;
	
	public MeetingTime() {}
		
	public MeetingTime(Integer id, String time, LocalTime startTime,  LocalTime endTime, WorkDay workDay) {
		this.id = id;
		this.time =  time;
		this.workDay = workDay;
		this.startTime =  startTime;
		this.endTime =  endTime;
	}
	
	public MeetingTime(ResultSet rs) throws SQLException {
		this.id = rs.getInt(MeetingTimeConstant.ID);
		this.time =  rs.getString(MeetingTimeConstant.TIME);
		this.startTime =  LocalTime.parse(rs.getString(MeetingTimeConstant.START_TIME));
		this.endTime =  LocalTime.parse(rs.getString(MeetingTimeConstant.END_TIME));
		this.updatedOn = rs.getDate(MeetingTimeConstant.UPDATED_ON);
		this.createdOn = rs.getDate(MeetingTimeConstant.CREATED_ON);
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
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

	public WorkDay getWorkDay() {
		return workDay;
	}

	public void setWorkDay(WorkDay workDay) {
		this.workDay = workDay;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public Integer getWorkDayId() {
		return this.workDayId;		
	}
	
	public void setWorkDayId(Integer workDayId) {
		this.workDayId = workDayId;		
	}
}
