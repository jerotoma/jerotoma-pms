package com.jerotoma.common.schedules;

import com.jerotoma.common.models.academic.Room;
import com.jerotoma.common.viewobjects.AcademicYearVO;
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
	AcademicYearVO academicYear;
	MeetingTimeVO meetingTime;
		
	public ScheduledClass(DepartmentVO department, TeacherVO teacher, CourseVO course, RoomVO room,
			AcademicYearVO academicYear, MeetingTimeVO meetingTime) {
		super();
		this.department = department;
		this.teacher = teacher;
		this.course = course;
		this.room = room;
		this.academicYear = academicYear;
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
	public AcademicYearVO getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(AcademicYearVO academicYear) {
		this.academicYear = academicYear;
	}
	public MeetingTimeVO getMeetingTime() {
		return meetingTime;
	}
	public void setMeetingTime(MeetingTimeVO meetingTime) {
		this.meetingTime = meetingTime;
	}
	
	
	

}
