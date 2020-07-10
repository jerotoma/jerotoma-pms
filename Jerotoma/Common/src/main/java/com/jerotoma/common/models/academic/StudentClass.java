package com.jerotoma.common.models.academic;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
	@JsonBackReference
	private Student student;
	
	
	@ManyToMany
	@JoinTable(
    	name = DatabaseConstant.TABLES.STUDENT_REGISTERED_CLASSES,
        joinColumns = @JoinColumn(name = "student_class_id"),
        inverseJoinColumns = @JoinColumn(name = "class_id"))
	@JoinColumn(name="class_id")
	private Set<Class> classes;
	
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
	
	public Set<Class> getClasses() {
		return classes;
	}

	public void setClasses(Set<Class> classes) {
		this.classes = classes;
	}

	public static class Fields {
		Integer Id;
		List<Integer> studentIds;
		List<Integer> classIds;
		Integer academicYearId;
			
		
		public Fields(Integer id, List<Integer> studentIds, List<Integer> classIds, Integer academicYearId) {
			super();
			Id = id;
			this.studentIds = studentIds;
			this.classIds = classIds;
			this.academicYearId = academicYearId;
		}
		public Integer getId() {
			return Id;
		}
		public void setId(Integer id) {
			Id = id;
		}
		
		public List<Integer> getStudentIds() {
			return studentIds;
		}
		
		public void setStudentIds(List<Integer> studentIds) {
			this.studentIds = studentIds;
		}
		
		public List<Integer> getClassIds() {
			return classIds;
		}
		
		public void setClassIds(List<Integer> classIds) {
			this.classIds = classIds;
		}
		
		public Integer getAcademicYearId() {
			return academicYearId;
		}
		
		public void setAcademicYearId(Integer academicYearId) {
			this.academicYearId = academicYearId;
		}		
	}
	
}
