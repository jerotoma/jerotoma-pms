package com.jerotoma.common.schedules;

import com.jerotoma.common.viewobjects.AcademicLevelVO;
import com.jerotoma.common.viewobjects.CourseVO;
import com.jerotoma.common.viewobjects.DepartmentVO;
import com.jerotoma.common.viewobjects.MeetingTimeVO;
import com.jerotoma.common.viewobjects.RoomVO;
import com.jerotoma.common.viewobjects.TeacherVO;

public class ScheduledClass {
	DepartmentVO department;
	TeacherVO teacher;
	CourseVO course;
	RoomVO room;
	AcademicLevelVO academicLevel;
	MeetingTimeVO meetingTime;
		
	public ScheduledClass(DepartmentVO department, TeacherVO teacher, CourseVO course, RoomVO room,
			AcademicLevelVO academicLevel, MeetingTimeVO meetingTime) {
		super();
		this.department = department;
		this.teacher = teacher;
		this.course = course;
		this.room = room;
		this.academicLevel = academicLevel;
		this.meetingTime = meetingTime;
	}
	public DepartmentVO getDepartment() {
		return department;
	}
	public void setDepartment(DepartmentVO department) {
		this.department = department;
	}
	public TeacherVO getTeacher() {
		return teacher;
	}
	public void setTeacher(TeacherVO teacher) {
		this.teacher = teacher;
	}
	public CourseVO getCourse() {
		return course;
	}
	public void setCourse(CourseVO course) {
		this.course = course;
	}
	public RoomVO getRoom() {
		return room;
	}
	public void setRoom(RoomVO room) {
		this.room = room;
	}
	public AcademicLevelVO getAcademicLevel() {
		return academicLevel;
	}
	public void setAcademicYear(AcademicLevelVO academicLevel) {
		this.academicLevel = academicLevel;
	}
	public MeetingTimeVO getMeetingTime() {
		return meetingTime;
	}
	public void setMeetingTime(MeetingTimeVO meetingTime) {
		this.meetingTime = meetingTime;
	}
	
	
	

}
