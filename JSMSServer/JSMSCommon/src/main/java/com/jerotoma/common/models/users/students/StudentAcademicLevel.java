package com.jerotoma.common.models.users.students;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.jerotoma.common.constants.CompletionStatus;
import com.jerotoma.common.constants.DatabaseConstant;
import com.jerotoma.common.models.academic.AcademicLevel;
import com.jerotoma.common.models.academic.AcademicYear;

@Entity
@Table(name = DatabaseConstant.TABLES.STUDENT_ACADEMIC_LEVELS)
public class StudentAcademicLevel {
	
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE, 
			generator = DatabaseConstant.TABLES.STUDENT_ACADEMIC_LEVELS + "_generator")
	@SequenceGenerator(
			name = DatabaseConstant.TABLES.STUDENT_ACADEMIC_LEVELS + "_generator", 
			sequenceName = DatabaseConstant.TABLES.STUDENT_ACADEMIC_LEVELS + "_id_seq", 
			allocationSize=1)
	@Column
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="academic_level_id")
	private AcademicLevel academicLevel;
	
	@ManyToOne
	@JoinColumn(name="student_id")
	@JsonBackReference
	private Student student;
	
	@ManyToOne
	@JoinColumn(name="academic_year_id")
	private AcademicYear academicYear;
	
	@Column(name="completion_status_id")
	private Integer completionStatusId;
	
	@Transient
	private CompletionStatus completionStatus;
	
	@OneToMany(mappedBy ="studentAcademicLevel")
	@JsonBackReference
	private Set<StudentClass> studentClasses;
		
	@Column(name="updated_by")
	private Integer updatedBy;
	
	@Column(name="created_on")
	private Date createdOn;
	
	@Column(name="updated_on")
	private Date updatedOn;
	
	public StudentAcademicLevel() {}
	
	public StudentAcademicLevel(Student student, AcademicLevel academicLevel, AcademicYear academicYear, CompletionStatus completionStatus) {
		super();
		this.student = student;
		this.academicLevel = academicLevel;
		this.completionStatus = completionStatus;
		this.completionStatusId = completionStatus.getID();
		this.academicYear = academicYear;
	}

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
	

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
		
	public AcademicLevel getAcademicLevel() {
		return academicLevel;
	}

	public void setAcademicLevel(AcademicLevel academicLevel) {
		this.academicLevel = academicLevel;
	}
	
	public Integer getCompletionStatusId() {
		return completionStatusId;
	}

	public void setCompletionStatusId(Integer completionStatusId) {
		this.completionStatusId = completionStatusId;
	}

	public CompletionStatus getCompletionStatus() {
		return completionStatus;
	}

	public void setCompletionStatus(CompletionStatus completionStatus) {
		this.completionStatus = completionStatus;
	}

	public Set<StudentClass> getStudentClasses() {
		return studentClasses;
	}

	public void setStudentClasses(Set<StudentClass> studentClasses) {
		this.studentClasses = studentClasses;
	}

	public AcademicYear getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(AcademicYear academicYear) {
		this.academicYear = academicYear;
	}

	public static class Fields {
		Integer studentId = null;
		Integer academicLevelId = null;
		Integer academicYearId = null;
		Integer commpletionStatusId = null;
		Boolean isCurrentStudentAcademicLevel = Boolean.FALSE;
		List<Integer> classIds = new ArrayList<>();	
		Integer id = null;
			
		public Fields(Integer id, Integer studentId, Integer commpletionStatusId, Integer academicLevelId, Integer academicYearId, List<Integer> classIds, Boolean isCurrentStudentAcademicLevel) {
			this.id = id;
			this.studentId = studentId;
			this.commpletionStatusId = commpletionStatusId;			
			this.academicLevelId = academicLevelId;
			this.academicYearId = academicYearId;
			this.classIds = classIds;
			this.isCurrentStudentAcademicLevel = isCurrentStudentAcademicLevel;
		}
		
		public Integer getId() {
			return id;
		}
		
		public void setId(Integer id) {
			this.id = id;
		}
				
		public void setAcademicLevelId(Integer academicLevelId) {
			this.academicLevelId = academicLevelId;
		}
		
		public Integer getAcademicLevelId() {			
			return academicLevelId;
		}
		
		public Integer getStudentId() {
			return studentId;
		}
		
		public void setStudentId(Integer studentId) {
			this.studentId = studentId;
		}
		
		public Integer getCommpletionStatusId() {
			return commpletionStatusId;
		}
		
		public void setCommpletionStatusId(Integer commpletionStatusId) {
			this.commpletionStatusId = commpletionStatusId;
		}

		public Integer getAcademicYearId() {
			return academicYearId;
		}

		public void setAcademicYearId(Integer academicYearId) {
			this.academicYearId = academicYearId;
		}

		public List<Integer> getClassIds() {
			return classIds;
		}

		public void setClassIds(List<Integer> classIds) {
			this.classIds = classIds;
		}

		public Boolean getIsCurrentStudentAcademicLevel() {
			return isCurrentStudentAcademicLevel;
		}

		public void setIsCurrentStudentAcademicLevel(Boolean isCurrentStudentAcademicLevel) {
			this.isCurrentStudentAcademicLevel = isCurrentStudentAcademicLevel;
		}
	}
	
}
