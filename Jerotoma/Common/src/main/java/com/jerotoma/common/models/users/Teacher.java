package com.jerotoma.common.models.users;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jerotoma.common.constants.DatabaseConstant;
import com.jerotoma.common.models.academic.Department;
import com.jerotoma.common.models.addresses.TeacherAddress;
import com.jerotoma.common.models.positions.Position;

@Entity
@Table(name = DatabaseConstant.TABLES.TEACHERS )
public class Teacher extends Person implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="teachers_generator")
	@SequenceGenerator(name="teachers_generator", sequenceName = "teachers_id_seq", allocationSize=1)
	@Column
	private Integer id;
	
	@OneToMany(mappedBy="teacher")
	@JsonManagedReference
	private Set<TeacherAddress> teacherAddresses;
		
	@ManyToOne
   	@JsonManagedReference
   	@JoinColumn(name="position_id")
	private Position position;
	
	@ManyToOne
   	@JsonManagedReference
   	@JoinColumn(name="department_id")
	private Department department;
			
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}	

	public Set<TeacherAddress> getTeacherAddresses() {
		return teacherAddresses;
	}

	public void setTeacherAddresses(Set<TeacherAddress> teacherAddresses) {
		this.teacherAddresses = teacherAddresses;
	}

	public void setDepartment(Department department) {
		this.department = department;		
	}

	public Department getDepartment() {
		return department;
	}
}
