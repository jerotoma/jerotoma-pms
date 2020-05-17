package com.jerotoma.common.schedules;

import java.util.List;

import com.jerotoma.common.viewobjects.AcademicYearVO;
import com.jerotoma.common.viewobjects.CourseVO;
import com.jerotoma.common.viewobjects.DepartmentVO;
import com.jerotoma.common.viewobjects.MeetingTimeVO;
import com.jerotoma.common.viewobjects.RoomVO;
import com.jerotoma.common.viewobjects.TeacherVO;

public class ScheduledData {
	
	private int numberOfClasses;
	private AcademicYearVO academicYear;
	private List<RoomVO> rooms;
	private List<TeacherVO> teachers;
	private List<CourseVO> courses;
	private List<DepartmentVO> departments;
	private List<MeetingTimeVO> meetingTimes;	
	public ScheduledData() {
		
	}
	
	public ScheduledData(List<RoomVO> rooms, List<TeacherVO> teachers, List<CourseVO> courses,
			List<DepartmentVO> departments, List<MeetingTimeVO> meetingTimes, AcademicYearVO academicYear) {		
		this.rooms = rooms;
		this.teachers = teachers;
		this.courses = courses;
		this.departments = departments;
		this.meetingTimes = meetingTimes;
		this.academicYear = academicYear;
		this.departments.forEach(x -> this.numberOfClasses += x.getCourses().size());
	}

	public int getNumberOfClasses() {
		return numberOfClasses;
	}

	public void setNumberOfClasses(int numberOfClasses) {
		this.numberOfClasses = numberOfClasses;
	}

	public List<RoomVO> getRooms() {
		return rooms;
	}

	public void setRooms(List<RoomVO> rooms) {
		this.rooms = rooms;
	}

	public List<TeacherVO> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<TeacherVO> teachers) {
		this.teachers = teachers;
	}

	public List<CourseVO> getCourses() {
		return courses;
	}

	public void setCourses(List<CourseVO> courses) {
		this.courses = courses;
	}

	public List<DepartmentVO> getDepartments() {
		return departments;
	}

	public void setDepartments(List<DepartmentVO> departments) {
		this.departments = departments;
	}

	public List<MeetingTimeVO> getMeetingTimes() {
		return meetingTimes;
	}

	public void setMeetingTimes(List<MeetingTimeVO> meetingTimes) {
		this.meetingTimes = meetingTimes;
	}

	public AcademicYearVO getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(AcademicYearVO academicYear) {
		this.academicYear = academicYear;
	}
	
}
