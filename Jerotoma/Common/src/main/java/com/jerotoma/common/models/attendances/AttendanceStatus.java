package com.jerotoma.common.models.attendances;

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
@Table(name = DatabaseConstant.TABLES.ATTENDANCE_STATUSES)
public class AttendanceStatus {
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE, 
			generator = DatabaseConstant.TABLES.ATTENDANCE_STATUSES + "_generator")
	@SequenceGenerator(
			name = DatabaseConstant.TABLES.ATTENDANCE_STATUSES + "_generator", 
			sequenceName = DatabaseConstant.TABLES.ATTENDANCE_STATUSES + "_id_seq", 
			allocationSize=1)
	@Column
	private Integer id;
	
	@Column
	private String status;
	
	@Column
	private String description;
	
	@Column(name="added_by")
	private Integer addedBy;
	
	@Column(name="created_on")
	private Date createdOn;
	
	@Column(name="updated_on")
	private Date updatedOn;	
	
	public AttendanceStatus() {}

	public AttendanceStatus(Integer id, String status, String description, Integer addedBy, Date createdOn,
			Date updatedOn) {
		this.id = id;
		this.status = status;
		this.description = description;
		this.addedBy = addedBy;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
	}
	
	public AttendanceStatus(String status, String description, Integer addedBy, Date createdOn,
			Date updatedOn) {		
		this.status = status;
		this.description = description;
		this.addedBy = addedBy;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(Integer addedBy) {
		this.addedBy = addedBy;
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

