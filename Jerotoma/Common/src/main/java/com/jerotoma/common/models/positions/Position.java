package com.jerotoma.common.models.positions;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.jerotoma.common.constants.DatabaseConstant;
import com.jerotoma.common.models.users.Staff;
import com.jerotoma.common.models.users.Teacher;
import com.jerotoma.common.viewobjects.PositionVO;

@Entity
@Table(name = DatabaseConstant.TABLES.POSITIONS)
public class Position implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="positions_generator")
	@SequenceGenerator(name="positions_generator", sequenceName = "positions_id_seq", allocationSize=1)
	@Column
	private Integer id;
	
	@Column
	private String name;
	
	@Column
	private String code;
	
	@Column
	private String description;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="position")
	@JsonBackReference
	private List<Teacher> teachers;	
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="position")
	@JsonBackReference
	private List<Staff> staffs;	
	
	@Column(name="created_on")
	private Date createdOn;
	
	@Column(name="updated_on")
	private Date updatedOn;

	public Position(PositionVO position) {
		this.id = position.getId();
		this.name = position.getName();
		this.description = position.getDescription();
		this.createdOn = position.getCreatedOn();
		this.updatedOn = position.getUpdatedOn();
	}

	public Position() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
