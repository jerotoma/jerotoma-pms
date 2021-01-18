package com.jerotoma.common.viewobjects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.jerotoma.common.constants.ClassConstant;

public class ClassVO {
	
	private Integer id;
	
	private Integer capacity;
	
	private Integer totalStudents;
	
	private CourseVO course;
	
	private AcademicYearVO academicYear;
	
	private RoomVO room;
	
	private TeacherVO teacher;
	
	private DepartmentVO department;
	
	private MeetingTimeVO meetingTime;
	
	private Integer updatedBy;
	
	private Date createdOn;
	
	private Date updatedOn;
	
	private List<StudentVO> students;
	
	public ClassVO(ResultSet rs) throws SQLException {
		this.id = rs.getInt(ClassConstant.CLASS_ID);
		this.capacity = rs.getInt(ClassConstant.CLASS_CAPACITY);
		this.totalStudents = rs.getInt(ClassConstant.CLASS_TOTAL_STUDENTS);
		this.updatedOn = rs.getDate(ClassConstant.CLASS_UPDATED_ON);
		this.createdOn = rs.getDate(ClassConstant.CLASS_CREATED_ON);
		
	}

	public ClassVO(int id, DepartmentVO department, CourseVO course, int capacity) {		
		this.id = id;
		this.department = department;
		this.course = course;
		this.capacity = capacity;
	}
	
	public ClassVO(int id, DepartmentVO department, CourseVO course) {		
		this.id = id;
		this.department = department;
		this.course = course;
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
	
	public RoomVO getRoom() {
		return room;
	}

	public void setRoom(RoomVO room) {
		this.room = room;
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

	public MeetingTimeVO getMeetingTime() {
		return meetingTime;
	}

	public void setMeetingTime(MeetingTimeVO meetingTime) {
		this.meetingTime = meetingTime;
	}

	public DepartmentVO getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentVO department) {
		this.department = department;
	}

	public void setTotalStudents(Integer totalStudents ) {
		this.totalStudents = totalStudents;
	}

	public Integer getTotalStudents() {
		return totalStudents;
	}
}
