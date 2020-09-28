package com.jerotoma.common.models.users;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jerotoma.common.constants.DatabaseConstant;
import com.jerotoma.common.models.addresses.ParentAddress;
import com.jerotoma.common.models.students.Student;

@Entity
@Table(name = DatabaseConstant.TABLES.PARENTS)
public class Parent extends Person implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="parents_generator")
	@SequenceGenerator(name="parents_generator", sequenceName = "parents_id_seq", allocationSize=1)
	@Column
	private Integer id;
	
	@Transient
	List<Integer> studentIds;
	
	@ManyToMany(mappedBy="parents")
	@JsonManagedReference
	private Set<Student> students = new HashSet<>();
	
	@OneToMany(mappedBy ="parent")
	@JsonManagedReference
	private List<ParentAddress> parentAddresses;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	public List<Integer> getStudentIds() {
		return studentIds;
	}

	public void setStudentIds(List<Integer> studentIds) {
		this.studentIds = studentIds;
	}

	public List<ParentAddress> getParentAddresses() {
		return parentAddresses;
	}

	public void setParentAddresses(List<ParentAddress> parentAddresses) {
		this.parentAddresses = parentAddresses;
	}

	@Override
	public int hashCode() {		
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj instanceof Parent ) {
			Parent p = (Parent)obj;			
			if (p.id == this.id) {
				return true;
			}
		}
		return super.equals(obj);
	}
	
	
}
