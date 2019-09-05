package com.jerotoma.common.models.academic;

import java.util.Date;

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
	
	@Column(name="updated_by")
	private Integer updatedBy;
	
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

	public JClass getJClass() {
		return jClass;
	}

	public void setJClass(JClass jClass) {
		this.jClass = jClass;
	}
	
	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
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



	public static class Fields {
		Integer Id;
		Integer studentId;
		Integer jclassId;
		Integer academicYearId;
			
		
		public Fields(Integer id, Integer studentId, Integer jclassId, Integer academicYearId) {
			super();
			Id = id;
			this.studentId = studentId;
			this.jclassId = jclassId;
			this.academicYearId = academicYearId;
		}
		public Integer getId() {
			return Id;
		}
		public void setId(Integer id) {
			Id = id;
		}
		public Integer getStudentId() {
			return studentId;
		}
		public void setStudentId(Integer studentId) {
			this.studentId = studentId;
		}
		public Integer getJclassId() {
			return jclassId;
		}
		public void setJclassId(Integer jclassId) {
			this.jclassId = jclassId;
		}
		public Integer getAcademicYearId() {
			return academicYearId;
		}
		public void setAcademicYearId(Integer academicYearId) {
			this.academicYearId = academicYearId;
		}
		
		
	}
	
}
