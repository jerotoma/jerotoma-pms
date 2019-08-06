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
import com.jerotoma.common.models.courses.Course;
import com.jerotoma.common.models.users.Student;
import com.jerotoma.common.models.users.Teacher;

@Entity
@Table(name = DatabaseConstant.TABLES.STUDENT_ADMISSIONS)
public class StudentAdmission implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="student_admissions_generator")
	@SequenceGenerator(name="student_admissions_generator", sequenceName = "student_admissions_id_seq", allocationSize=1)
	@Column
	private Long id;
	
	@Column(name="year_of_study")
	private String yearOfStudy;
	
	@Column(name="admission_code")
	private String admissionCode;
	
	@OneToOne
	@JoinColumn(name="student_id")
	private Student student;
	
	@OneToOne
	@JoinColumn(name="teacher_id")
	private Teacher teacher;
	
	@OneToOne
	@JoinColumn(name="course_id")
	private Course course;
	
	@Column(name="created_on")
	Date createdOn;
	
	@Column(name="updated_on")
	Date updatedOn;
	
	public StudentAdmission() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getYearOfStudy() {
		return yearOfStudy;
	}

	public void setYearOfStudy(String yearOfStudy) {
		this.yearOfStudy = yearOfStudy;
	}

	public String getAdmissionCode() {
		return admissionCode;
	}

	public void setAdmissionCode(String admissionCode) {
		this.admissionCode = admissionCode;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
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
