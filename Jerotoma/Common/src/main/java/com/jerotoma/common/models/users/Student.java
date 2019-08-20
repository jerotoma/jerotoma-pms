package com.jerotoma.common.models.users;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jerotoma.common.constants.DatabaseConstant;


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
	private Long id;
	
	@Column
	private String position;
	
	@Column(name="student_code")
	private String studentCode;
	
	@OneToMany(mappedBy="student")
	@JsonManagedReference
	private Set<Parent> parents;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getStudentCode() {
		return studentCode;
	}

	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}
}
