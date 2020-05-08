package com.jerotoma.common.models.academic;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.jerotoma.common.constants.DatabaseConstant;
import com.jerotoma.common.models.users.Teacher;
import com.jerotoma.common.schedules.MeetingTime;


@Entity
@Table(name = DatabaseConstant.TABLES.CLASSES)
public class Class {
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE, 
			generator = DatabaseConstant.TABLES.CLASSES + "_generator")
	@SequenceGenerator(
			name = DatabaseConstant.TABLES.CLASSES + "_generator", 
			sequenceName = DatabaseConstant.TABLES.CLASSES + "_id_seq", 
			allocationSize=1)
	@Column
	private Integer id;
	
	@Column
	private Integer capacity;
		
	@ManyToOne
	@JoinColumn(name="course_id")
	private Course course;
	
	@ManyToOne
	@JoinColumn(name="academic_year_id")
	private AcademicYear academicYear;
	
	@ManyToOne
	@JoinColumn(name="room_id")
	private Room room;
	
	@ManyToMany(mappedBy="jClasses")
	@JsonBackReference
	private Set<StudentClass> studentClasses = new HashSet<>();
	
	@ManyToOne
	@JoinColumn(name="teacher_id")
	@JsonBackReference
	private Teacher teacher;
	
	@ManyToOne
	@JoinColumn(name="meeting_time_id")
	@JsonBackReference
	private MeetingTime meetingTime;
	
	
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
	
	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public AcademicYear getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(AcademicYear academicYear) {
		this.academicYear = academicYear;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	public Integer getCapacity() {
		return capacity;
	}
	
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}
		
	public Set<StudentClass> getStudentClasses() {
		return studentClasses;
	}

	public void setStudentClasses(Set<StudentClass> studentClasses) {
		this.studentClasses = studentClasses;
	}

	public MeetingTime getMeetingTime() {
		return meetingTime;
	}

	public void setMeetingTime(MeetingTime meetingTime) {
		this.meetingTime = meetingTime;
	}
	
	public static class ClassFields{
		Integer courseId = null;
		Integer academicYearId = null;
		Integer roomId = null;
		Integer teacherId = null;
		Integer meetingTimeId = null;
		Integer capacity = null;	
		Integer id = null;
				
		public ClassFields(Integer courseId, Integer academicYearId, Integer roomId, Integer teacherId, Integer meetingTimeId,
				Integer capacity, Integer id) {
			super();
			this.courseId = courseId;
			this.academicYearId = academicYearId;
			this.roomId = roomId;
			this.teacherId = teacherId;
			this.meetingTimeId = meetingTimeId;
			this.capacity = capacity;
			this.id = id;
		}
		
		public Integer getCourseId() {
			return courseId;
		}
		
		public void setCourseId(Integer courseId) {
			this.courseId = courseId;
		}
		
		public Integer getAcademicYearId() {
			return academicYearId;
		}
		
		public void setAcademicYearId(Integer academicYearId) {
			this.academicYearId = academicYearId;
		}
		
		public Integer getRoomId() {
			return roomId;
		}
		
		public void setRoomId(Integer roomId) {
			this.roomId = roomId;
		}
		
		public Integer getTeacherId() {
			return teacherId;
		}
		
		public void setTeacherId(Integer teacherId) {
			this.teacherId = teacherId;
		}
		
		public Integer getCapacity() {
			return capacity;
		}
		
		public void setCapacity(Integer capacity) {
			this.capacity = capacity;
		}
		
		public Integer getId() {
			return id;
		}
		
		public void setId(Integer id) {
			this.id = id;
		}

		public Integer getMeetingTimeId() {
			return meetingTimeId;
		}

		public void setMeetingTimeId(Integer meetingTimeId) {
			this.meetingTimeId = meetingTimeId;
		}
		
	}
}
