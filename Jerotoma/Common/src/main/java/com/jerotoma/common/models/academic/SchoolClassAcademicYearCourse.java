package com.jerotoma.common.models.academic;

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

import com.jerotoma.common.constants.DatabaseConstant;


@Entity
@Table(name = DatabaseConstant.TABLES.SCHOOL_CLASS_ACADEMIC_YEARS_COURSES)
public class SchoolClassAcademicYearCourse {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="school_class_accademic_year_courses_generator")
	@SequenceGenerator(name="school_class_accademic_year_courses_generator", sequenceName = "school_class_accademic_year_courses_id_seq", allocationSize=1)
	@Column
	private Integer id;
	
	
	@Column(name="updated_by")
	private Integer updatedBy;
	
	@OneToOne
	@JoinColumn(name="academic_year_course_id")
	private AcademicYearCourse academicYearCourse; 
	
	@Column(name="created_on")
	private Date createdOn;
	
	@Column(name="updated_on")
	private Date updatedOn;
	
	@OneToOne
	@JoinColumn(name="school_class_id")
	private SchoolClass schoolClass;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AcademicYearCourse getAcademicYearCourse() {
		return academicYearCourse;
	}

	public void setAcademicYearCourse(AcademicYearCourse academicYearCourse) {
		this.academicYearCourse = academicYearCourse;
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

	public SchoolClass getSchoolClass() {
		return schoolClass;
	}

	public void setSchoolClass(SchoolClass schoolClass) {
		this.schoolClass = schoolClass;
	}

	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}
	
}
