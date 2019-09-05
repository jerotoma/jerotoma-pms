package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.jerotoma.common.constants.JClassConstant;

public class JClassVO {
	
	private Integer id;
	
	private Integer capacity;
	
	private CourseVO course;
	
	private AcademicYearVO academicYear;
	
	private ClassRoomVO classRoom;
	
	private TeacherVO teacher;
	
	private Integer updatedBy;
	
	private Date createdOn;
	
	private Date updatedOn;
	
	private List<StudentVO> students;
	
	public JClassVO(ResultSet rs) throws SQLException {
		this.id = rs.getInt(JClassConstant.JCLASS_ID);
		this.capacity = rs.getInt(JClassConstant.JCLASS_CAPACITY);
		this.updatedOn = rs.getDate(JClassConstant.JCLASS_UPDATED_ON);
		this.createdOn = rs.getDate(JClassConstant.JCLASS_CREATED_ON);
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public CourseVO getCourse() {
		return course;
	}

	public void setCourse(CourseVO course) {
		this.course = course;
	}

	public AcademicYearVO getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(AcademicYearVO academicYear) {
		this.academicYear = academicYear;
	}

	public ClassRoomVO getClassRoom() {
		return classRoom;
	}

	public void setClassRoom(ClassRoomVO classRoom) {
		this.classRoom = classRoom;
	}

	public TeacherVO getTeacher() {
		return teacher;
	}

	public void setTeacher(TeacherVO teacher) {
		this.teacher = teacher;
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

	public List<StudentVO> getStudents() {
		return students;
	}

	public void setStudents(List<StudentVO> students) {
		this.students = students;
	}
}
