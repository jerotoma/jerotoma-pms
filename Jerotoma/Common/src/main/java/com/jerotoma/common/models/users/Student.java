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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jerotoma.common.constants.DatabaseConstant;
import com.jerotoma.common.models.academic.JClass;
import com.jerotoma.common.models.academic.StudentClass;


@Entity
@Table(name = DatabaseConstant.TABLES.STUDENTS)
public class Student extends Person implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="students_generator")
	@SequenceGenerator(name="students_generator", sequenceName = "students_id_seq", allocationSize=1)
	@Column
	private Integer id;
	
	@Column
	private String position;
	
	@Transient
	List<Integer> parentIds;
	
	@Column(name="student_number")
	private Integer studentNumber;
	
	@ManyToMany
    @JoinTable(
    	name = DatabaseConstant.TABLES.STUDENT_PARENTS,
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "parent_id"))
	@JsonBackReference
	private Set<Parent> parents = new HashSet<>();
	
	@OneToMany(mappedBy ="student")
	@JsonManagedReference
	private Set<StudentClass> studentClasses;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Integer getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(Integer studentNumber) {
		this.studentNumber = studentNumber;
	}

	public Set<StudentClass> getStudentClases() {
		return studentClasses;
	}

	public void setStudentClases(Set<StudentClass> studentClases) {
		this.studentClasses = studentClases;
	}

	public List<Integer> getParentIds() {
		return parentIds;
	}

	public void setParentIds(List<Integer> parentIds) {
		this.parentIds = parentIds;
	}

	public Set<Parent> getParents() {
		return parents;
	}

	public void setParents(Set<Parent> parents) {
		this.parents = parents;
	}
	
}
