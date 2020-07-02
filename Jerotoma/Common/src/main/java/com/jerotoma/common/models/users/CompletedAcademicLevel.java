package com.jerotoma.common.models.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jerotoma.common.constants.DatabaseConstant;


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
	
	@Column(name="student_id")
	private Integer studentId;
	
	@Column(name="academic_level_id")
	private Integer academicLevelId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Integer getAcademicLevelId() {
		return academicLevelId;
	}

	public void setAcademicLevelId(Integer academicLevelId) {
		this.academicLevelId = academicLevelId;
	}
}
