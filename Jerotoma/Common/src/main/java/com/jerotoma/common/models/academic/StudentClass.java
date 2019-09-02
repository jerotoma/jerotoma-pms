package com.jerotoma.common.models.academic;

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
import com.jerotoma.common.models.users.Student;

@Entity
@Table(name = DatabaseConstant.TABLES.STUDENT_CLASSES)
public class StudentClass {
	
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE, 
			generator = DatabaseConstant.TABLES.STUDENT_CLASSES + "_generator")
	@SequenceGenerator(
			name = DatabaseConstant.TABLES.STUDENT_CLASSES + "_generator", 
			sequenceName = DatabaseConstant.TABLES.STUDENT_CLASSES + "_id_seq", 
			allocationSize=1)
	@Column
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="academic_year_id")
	private AcademicYear academicYear;
	
	@ManyToOne
	@JoinColumn(name="student_id")
	private Student student;
	
	@ManyToOne
	@JoinColumn(name="class_id")
	private JClass jClass;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AcademicYear getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(AcademicYear academicYear) {
		this.academicYear = academicYear;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public JClass getjClass() {
		return jClass;
	}

	public void setjClass(JClass jClass) {
		this.jClass = jClass;
	}
	
}
