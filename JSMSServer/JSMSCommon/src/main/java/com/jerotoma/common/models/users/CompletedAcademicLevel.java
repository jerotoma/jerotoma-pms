package com.jerotoma.common.models.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jerotoma.common.constants.DatabaseConstant;
import com.jerotoma.common.models.users.students.Student;


@Entity
@Table(name = DatabaseConstant.TABLES.COMPLETED_ACADEMIC_LEVELS)
public class CompletedAcademicLevel {
	

	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE, 
			generator = DatabaseConstant.TABLES.COMPLETED_ACADEMIC_LEVELS + "_generator")
	@SequenceGenerator(
			name= DatabaseConstant.TABLES.COMPLETED_ACADEMIC_LEVELS + "_generator", 
			sequenceName = DatabaseConstant.TABLES.COMPLETED_ACADEMIC_LEVELS + "_id_seq", 
			allocationSize=1)
	@Column
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="student_id")
	private Student student;
	
	@Column(name="academic_level_id")
	private Integer academicLevelId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Integer getAcademicLevelId() {
		return academicLevelId;
	}

	public void setAcademicLevelId(Integer academicLevelId) {
		this.academicLevelId = academicLevelId;
	}
}
