package com.jerotoma.common.models.admissions;

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

import com.jerotoma.common.constants.DatabaseConstant;
import com.jerotoma.common.models.courses.AcademicYearCourse;
import com.jerotoma.common.models.users.Student;

@Entity
@Table(name = DatabaseConstant.TABLES.STUDENT_COURSE_ADMISSIONS)
public class StudentCourseAdmission implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="student_course_admissions_generator")
	@SequenceGenerator(name="student_course_admissions_generator", sequenceName = "student_course_admissions_id_seq", allocationSize=1)
	@Column
	private Integer id;
	
	@Column(name="code")
	private String code;
	
	@OneToOne
	@JoinColumn(name="academic_year_course_id")
	private AcademicYearCourse academicYearCourse;
		
	@OneToOne
	@JoinColumn(name="student_id")
	private Student student;
	
	@Column(name="created_on")
	private Date createdOn;
	
	@Column(name="updated_on")
	private Date updatedOn;
	
	public StudentCourseAdmission() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
 
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
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
