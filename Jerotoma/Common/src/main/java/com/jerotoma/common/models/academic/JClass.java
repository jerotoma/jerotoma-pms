package com.jerotoma.common.models.academic;

import java.util.Date;
import java.util.List;

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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.jerotoma.common.constants.DatabaseConstant;
import com.jerotoma.common.models.users.Teacher;


@Entity
@Table(name = DatabaseConstant.TABLES.CLASSES)
public class JClass {
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
	@JoinColumn(name="class_room_id")
	private ClassRoom classRoom;
	
	@ManyToOne
	@JoinColumn(name="teacher_id")
	@JsonBackReference
	private Teacher teacher;
	
	@OneToMany(mappedBy = "jClass")
	private List<StudentClass> studentClasses;
	
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

	public ClassRoom getClassRoom() {
		return classRoom;
	}

	public void setClassRoom(ClassRoom classRoom) {
		this.classRoom = classRoom;
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
	
	public static class JClassFields{
		Integer courseId = null;
		Integer academicYearId = null;
		Integer classRoomId = null;
		Integer teacherId = null;
		Integer capacity = null;	
		Integer id = null;
				
		public JClassFields(Integer courseId, Integer academicYearId, Integer classRoomId, Integer teacherId,
				Integer capacity, Integer id) {
			super();
			this.courseId = courseId;
			this.academicYearId = academicYearId;
			this.classRoomId = classRoomId;
			this.teacherId = teacherId;
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
		
		public Integer getClassRoomId() {
			return classRoomId;
		}
		
		public void setClassRoomId(Integer classRoomId) {
			this.classRoomId = classRoomId;
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
		
	}
}
