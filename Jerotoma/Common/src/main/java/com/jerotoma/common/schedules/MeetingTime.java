package com.jerotoma.common.schedules;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jerotoma.common.constants.DatabaseConstant;

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
	
	@Column
	String time;
	
	@Column(name="created_on")
	private Date createdOn;
	
	@Column(name="updated_on")
	private Date updatedOn;
	
	public MeetingTime() {}
		
	public MeetingTime(Integer id, String time) {
		this.id = id;
		this.time = time;
	}
	
	public MeetingTime(ResultSet rs) throws SQLException {
		this.id = rs.getInt("id");
		this.time =  rs.getString("time");;
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
