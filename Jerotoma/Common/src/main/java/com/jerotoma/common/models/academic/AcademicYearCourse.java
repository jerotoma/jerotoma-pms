package com.jerotoma.common.models.academic;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jerotoma.common.constants.DatabaseConstant;


@Entity
@Table(name = DatabaseConstant.TABLES.ACADEMIC_YEARS_COURSES)
public class AcademicYearCourse {
		
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="accademic_year_courses_generator")
	@SequenceGenerator(name="accademic_year_courses_generator", sequenceName = "accademic_year_courses_id_seq", allocationSize=1)
	@Column
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="course_id")
	private Course course; 
	
	@OneToOne
	@JoinColumn(name="accademic_year_id")
	private AcademicYear accademicYear; 
	
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

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public AcademicYear getAccademicYear() {
		return accademicYear;
	}

	public void setAccademicYear(AcademicYear accademicYear) {
		this.accademicYear = accademicYear;
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
