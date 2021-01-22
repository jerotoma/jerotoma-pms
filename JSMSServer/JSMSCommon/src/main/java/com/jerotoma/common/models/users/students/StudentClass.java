package com.jerotoma.common.models.users.students;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.jerotoma.common.constants.DatabaseConstant;
import com.jerotoma.common.models.academic.Class;
import com.jerotoma.common.models.academic.ScoreStanding;

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
	@JoinColumn(name="student_academic_level_id")
	@JsonBackReference
	private StudentAcademicLevel studentAcademicLevel;
	
	@ManyToOne
	@JoinColumn(name="class_id")
	private Class mClass;
	
	@Column(name="completion_status_id")
	private Integer completionStatusId;
	
	@ManyToOne
	@JoinColumn(name="score_standing_id")
	private ScoreStanding scoreStanding;
	
	@Column
	private Double score;
	
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

	public StudentAcademicLevel getStudentAcademicLevel() {
		return studentAcademicLevel;
	}

	public void setStudentAcademicLevel(StudentAcademicLevel studentAcademicLevel) {
		this.studentAcademicLevel = studentAcademicLevel;
	}
	
	public Class getmClass() {
		return mClass;
	}

	public void setmClass(Class mClass) {
		this.mClass = mClass;
	}
		
	public Integer getCompletionStatusId() {
		return completionStatusId;
	}

	public void setCompletionStatusId(Integer completionStatusId) {
		this.completionStatusId = completionStatusId;
	}
	
	public void setScore(Double score) {
		this.score = score;		
	}

	public Double getScore() {
		return score;
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
	
	public ScoreStanding getScoreStanding() {
		return scoreStanding;
	}

	public void setScoreStanding(ScoreStanding scoreStanding) {
		this.scoreStanding = scoreStanding;
	}

	public static class Fields {
		Integer Id;
		Integer completionStatusId;
		List<Integer> studentIds;
		List<Integer> classIds;		
		
		public Fields(Integer id, List<Integer> studentIds, List<Integer> classIds, Integer completionStatusId) {
			super();
			Id = id;
			this.studentIds = studentIds;
			this.classIds = classIds;			
			this.completionStatusId = completionStatusId;
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
	}
	
	public static class StudentClassParam {
		private Integer classId;	
		
		private List<StudentClassProgressParam> studentClassProgressParams;
		
		public StudentClassParam(Integer classId, List<StudentClassProgressParam> studentClassProgressParams) {			
			this.classId = classId;
			this.studentClassProgressParams = studentClassProgressParams;
		}

		public Integer getClassId() {
			return classId;
		}

		public void setClassId(Integer classId) {
			this.classId = classId;
		}

		public List<StudentClassProgressParam> getStudentClassProgressParams() {
			return studentClassProgressParams;
		}

		public void setStudentClassProgressParams(List<StudentClassProgressParam> studentClassProgressParams) {
			this.studentClassProgressParams = studentClassProgressParams;
		}				
	}
	
	public static class StudentClassProgressParam {
		Double score;
		Integer studentId;
		Integer statusId;
		Integer studentAcademicLevelId;
		Integer scoreStandingId;
		
		
		public StudentClassProgressParam(Integer studentAcademicLevelId, Integer studentId, Integer statusId, Double score, Integer scoreStandingId) {			
			this.score = score;
			this.studentId = studentId;
			this.studentAcademicLevelId = studentAcademicLevelId;
			this.statusId = statusId;
			this.scoreStandingId = scoreStandingId;
		}
		
		public Double getScore() {
			return score;
		}
		
		public void setScore(Double score) {
			this.score = score;
		}
		
		public Integer getStudentId() {
			return studentId;
		}
		
		public void setStudentId(Integer studentId) {
			this.studentId = studentId;
		}
		
		public Integer getStatusId() {
			return statusId;
		}
		
		public void setStatusId(Integer statusId) {
			this.statusId = statusId;
		}

		public Integer getStudentAcademicLevelId() {
			return studentAcademicLevelId;
		}

		public void setStudentAcademicLevelId(Integer studentAcademicLevelId) {
			this.studentAcademicLevelId = studentAcademicLevelId;
		}

		public Integer getScoreStandingId() {
			return scoreStandingId;
		}

		public void setScoreStandingId(Integer scoreStandingId) {
			this.scoreStandingId = scoreStandingId;
		}
	}
}
