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
import com.jerotoma.common.models.users.Teacher;

@Entity
@Table(name = DatabaseConstant.TABLES.TEACHER_COURSE_ADMISSIONS)
public class TeacherCourseAdmission implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="teacher_course_admissions_generator")
	@SequenceGenerator(name="teacher_course_admissions_generator", sequenceName = "teacher_course_admissions_id_seq", allocationSize=1)
	@Column
	private Integer id;
		
	@Column(name="code")
	private String code;
	
	@OneToOne
	@JoinColumn(name="teacher_id")
	private Teacher teacher;
	
	@OneToOne
	@JoinColumn(name="academic_year_offered_course_id")
	private AcademicYearCourse academicYearOfferedCourse;
	
	@Column(name="created_on")
	private Date createdOn;
	
	@Column(name="updated_on")
	private Date updatedOn;
	
	public TeacherCourseAdmission() {
		
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
	
	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
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
