package com.jerotoma.common.models.academicDisciplines;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.jerotoma.common.constants.DatabaseConstant;
import com.jerotoma.common.models.academic.Course;

@Entity
@Table(name = DatabaseConstant.TABLES.ACADEMIC_DISCIPLINE)
public class AcademicDiscipline implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE, 
			generator = DatabaseConstant.TABLES.ACADEMIC_DISCIPLINE + "_generator")
	@SequenceGenerator(
			name = DatabaseConstant.TABLES.ACADEMIC_DISCIPLINE + "_generator", 
			sequenceName = DatabaseConstant.TABLES.ACADEMIC_DISCIPLINE + "_id_seq", 
			allocationSize=1)
	@Column
	private Integer id;
	
	@Column
	private String name;
	
	@Column
	private String code;
	
	@Column
	private String description;
		
	@ManyToMany(mappedBy="academicDisciplines")
	@JsonBackReference
	private Set<Course> courses = new HashSet<>();;
	
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
