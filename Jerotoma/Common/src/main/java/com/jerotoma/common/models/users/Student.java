package com.jerotoma.common.models.users;

import java.io.Serializable;
import java.util.List;

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
	
	@Column(name="student_number")
	private Integer studentNumber;
	
	@ManyToOne
    @JoinColumn(name="parent_id", nullable=true)
	@JsonManagedReference
	private Parent parent;
	
	@OneToMany(mappedBy = "student")
	private List<StudentClass> studentClass;
	
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

	public Parent getParent() {
		return parent;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}
	

}
