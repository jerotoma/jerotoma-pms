package com.jerotoma.common.models.academic;

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

import com.jerotoma.common.constants.DatabaseConstant;
import com.jerotoma.common.models.users.Teacher;
import com.jerotoma.common.viewobjects.DepartmentVO;

@Entity
@Table(name = DatabaseConstant.TABLES.DEPARTMENTS)
public class Department {
	
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE, 
			generator = DatabaseConstant.TABLES.DEPARTMENTS + "_generator")
	@SequenceGenerator(
			name = DatabaseConstant.TABLES.DEPARTMENTS + "_generator", 
			sequenceName = DatabaseConstant.TABLES.DEPARTMENTS + "_id_seq", 
			allocationSize=1)
	@Column
	Integer id;
	
	@Column
	String name;
	
	@OneToMany(mappedBy = "department")
	private List<Course> courses;
	
	@OneToMany(mappedBy = "department")
	private List<Teacher> teachers;
	
	@Column(name="created_on")
	private Date createdOn;
	
	@Column(name="updated_on")
	private Date updatedOn;

	public Department(DepartmentVO department) {
		this.id = department.getId();
		this.name = department.getName();
		this.createdOn = department.getCreatedOn();
		this.updatedOn = department.getUpdatedOn();
	}

	public Department() {
		
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
