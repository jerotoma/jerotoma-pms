package com.jerotoma.common.schedules;

import java.time.DayOfWeek;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.jerotoma.common.constants.DatabaseConstant;

@Entity
@Table(name = DatabaseConstant.TABLES.WORK_DAYS)
public class WorkDay {
	

	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE, 
			generator = DatabaseConstant.TABLES.WORK_DAYS + "_generator")
	@SequenceGenerator(
			name = DatabaseConstant.TABLES.WORK_DAYS + "_generator", 
			sequenceName = DatabaseConstant.TABLES.WORK_DAYS + "_id_seq", 
			allocationSize=1)
	@Column
	Integer id;
		
	@Column(name="day_id")
	Integer dayId;
	
	@Transient
	DayOfWeek day;
	
	@OneToMany(mappedBy = "workDay")
	private List<MeetingTime> meetingTimes;
		
	@Column(name="created_on")
	private Date createdOn;
	
	@Column(name="updated_on")
	private Date updatedOn;
	
	public WorkDay(Integer id, DayOfWeek day, Date createdOn, Date updatedOn) {
		super();
		this.id = id;
		this.day = day;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.dayId = day.getValue();
	}

	public WorkDay() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public DayOfWeek getDay() {
		return day;
	}

	public void setDay(DayOfWeek day) {
		this.day = day;
	}
	
	public Integer getDayId() {
		return dayId;
	}

	public void setDayId(Integer dayId) {
		this.dayId = dayId;
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
