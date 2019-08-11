package com.jerotoma.common.models.positions;

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
@Table(name = DatabaseConstant.TABLES.POSITIONS)
public class Position implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="positions_generator")
	@SequenceGenerator(name="position_generator", sequenceName = "positions_id_seq", allocationSize=1)
	@Column
	private Integer id;
	
	private String name;
	
	private String code;
	
	private String description;
	
	
	@Column(name="created_on")
	private Date createdOn;
	
	@Column(name="updated_on")
	private Date updatedOn;

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
