package com.jerotoma.common.viewobjects;

import java.util.Date;
import java.util.List;

public class DepartmentVO {
	Integer id;
		
	String name;
		
	private Date createdOn;
	
	private Date updatedOn;
	
	private List<CourseVO> courses;
	
	private List<TeacherVO> teachers;
	
	public DepartmentVO(Integer id, String name, Date createdOn, Date updatedOn) {
		super();
		this.id = id;
		this.name = name;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	public void setCourses(List<CourseVO> courses) {
		this.courses = courses;
	}
	
	public List<CourseVO> getCourses() {
		return this.courses;
	}
	
	public void setTeachers(List<TeacherVO> teachers) {
		this.teachers = teachers;
	}
	
	public List<TeacherVO> getTeachers() {
		return this.teachers;
	}
}
