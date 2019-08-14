package com.jerotoma.common.models.users;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jerotoma.common.constants.DatabaseConstant;
import com.jerotoma.common.models.fieldOfStudies.AcademicDiscipline;
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
	
	@Column(name="user_id")
	private Integer userId;
	
	@Column(name="teacher_code")
	private String teacherCode;
	
	@OneToOne
   	@JsonManagedReference
   	@JoinColumn(name="position_id")
	private Position position;
	
	@OneToOne
   	@JsonManagedReference
   	@JoinColumn(name="academic_discipline_id")
	private AcademicDiscipline academicDiscipline;
	
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

	public String getTeacherCode() {
		return teacherCode;
	}

	public void setTeacherCode(String teacherCode) {
		this.teacherCode = teacherCode;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public AcademicDiscipline getAcademicDiscipline() {
		return academicDiscipline;
	}

	public void setAcademicDiscipline(AcademicDiscipline academicDiscipline) {
		this.academicDiscipline = academicDiscipline;
	}
}
