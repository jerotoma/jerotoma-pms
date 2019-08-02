package com.jerotoma.common.models.users;

import java.io.Serializable;
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
@Table(name = DatabaseConstant.TABLES.TEACHERS )
public class Teacher extends Person implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="teachers_generator")
	@SequenceGenerator(name="teachers_generator", sequenceName = "seq_teachers", allocationSize=1)
	@Column
	private Long id;
	
	@Column(name="teacher_code")
	private String teacherCode;
	
	@Column
	private String position;
	
	@Column(name="created_on")
	private Date createdOn;
	
	@Column(name="updated_on")
	private Date updatedOn;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTeacherCode() {
		return teacherCode;
	}

	public void setTeacherCode(String teacherCode) {
		this.teacherCode = teacherCode;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
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
