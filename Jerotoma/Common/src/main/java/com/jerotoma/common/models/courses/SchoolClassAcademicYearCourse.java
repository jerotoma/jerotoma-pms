package com.jerotoma.common.models.courses;

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
	
	@OneToOne
	@JoinColumn(name="academic_course_id")
	private AcademicYearCourse academicYearCourse; 
	
	@Column(name="created_on")
	private Date createdOn;
	
	@Column(name="updated_on")
	private Date updatedOn;
	
	@OneToOne
	@JoinColumn(name="school_class_id")
	private SchoolClass schoolClass; 
}
