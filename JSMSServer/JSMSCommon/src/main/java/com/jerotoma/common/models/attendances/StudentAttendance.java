package com.jerotoma.common.models.attendances;

import java.util.Date;
import java.util.List;

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
import com.jerotoma.common.models.users.students.Student;


	
@Entity
@Table(name = DatabaseConstant.TABLES.STUDENT_ATTENDANCES)
public class StudentAttendance {
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE, 
			generator = DatabaseConstant.TABLES.STUDENT_ATTENDANCES + "_generator")
	@SequenceGenerator(
			name = DatabaseConstant.TABLES.STUDENT_ATTENDANCES + "_generator", 
			sequenceName = DatabaseConstant.TABLES.STUDENT_ATTENDANCES + "_id_seq", 
			allocationSize=1)
	@Column
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="student_id")
	private Student student;
	
	@ManyToOne
	@JoinColumn(name="class_attendance_id")
	private ClassAttendance classAttendance;
	
	@OneToOne
	@JoinColumn(name="attendance_status_id")
	private AttendanceStatus attendanceStatus;
	
	@Column(name="added_by")
	private Integer addedBy;
	
	@Column(name="created_on")
	private Date createdOn;
	
	@Column(name="updated_on")
	private Date updatedOn;
	
	public StudentAttendance() {}

	public StudentAttendance(Integer id, Student student, ClassAttendance classAttendance, AttendanceStatus attendanceStatus,
			Integer addedBy, Date createdOn, Date updatedOn) {
		super();
		this.id = id;
		this.student = student;
		this.classAttendance = classAttendance;
		this.attendanceStatus = attendanceStatus;
		this.addedBy = addedBy;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
	}
	
	public StudentAttendance(Student student, ClassAttendance classAttendance, AttendanceStatus attendanceStatus,
			Integer addedBy, Date createdOn, Date updatedOn) {
		this.student = student;
		this.classAttendance = classAttendance;
		this.attendanceStatus = attendanceStatus;
		this.addedBy = addedBy;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public ClassAttendance getClassAttendance() {
		return classAttendance;
	}

	public void setClassAttendance(ClassAttendance classAttendance) {
		this.classAttendance = classAttendance;
	}

	public AttendanceStatus getAttendanceStatus() {
		return attendanceStatus;
	}

	public void setAttendanceStatus(AttendanceStatus attendanceStatus) {
		this.attendanceStatus = attendanceStatus;
	}

	public Integer getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(Integer addedBy) {
		this.addedBy = addedBy;
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
	
	public static class StudentAttendanceStatusParam {
		Integer studentId; 
		Integer attendanceStatusId;		
				
		public Integer getStudentId() {
			return studentId;
		}
		
		public void setStudentId(Integer studentId) {
			this.studentId = studentId;
		}	

		public Integer getAttendanceStatusId() {
			return attendanceStatusId;
		}

		public void setAttendanceStatusId(Integer attendanceStatusId) {
			this.attendanceStatusId = attendanceStatusId;
		}
	}
	
	public static class StudentAttendanceParam {
		Integer Id; 
		Integer classAttendanceId; 
		List<StudentAttendanceStatusParam> studentAttendanceStatuses;
		
		public Integer getId() {
			return Id;
		}
		public void setId(Integer id) {
			Id = id;
		}
		public Integer getClassAttendanceId() {
			return classAttendanceId;
		}
		public void setClassAttendanceId(Integer classAttendanceId) {
			this.classAttendanceId = classAttendanceId;
		}
		public List<StudentAttendanceStatusParam> getStudentAttendanceStatuses() {
			return studentAttendanceStatuses;
		}
		public void setStudentAttendanceStatuses(List<StudentAttendanceStatusParam> studentAttendanceStatuses) {
			this.studentAttendanceStatuses = studentAttendanceStatuses;
		}		
		
	}
}
