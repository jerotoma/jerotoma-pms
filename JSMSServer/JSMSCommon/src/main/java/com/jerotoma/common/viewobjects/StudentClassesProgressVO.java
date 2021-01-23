package com.jerotoma.common.viewobjects;

import java.util.List;

public class StudentClassesProgressVO {
	private Integer studentID;
	private String studentName;		
	private List<ClassProgressVO> classesProgress;
	
	public StudentClassesProgressVO(Integer studentID,  String studentName, List<ClassProgressVO> classesProgress ) {
		this.studentID = studentID;
		this.studentName = studentName;
		this.classesProgress = classesProgress;
	}

	public Integer getStudentID() {
		return studentID;
	}

	public void setStudentID(Integer studentID) {
		this.studentID = studentID;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public List<ClassProgressVO> getClassesProgress() {
		return classesProgress;
	}

	public void setClassesProgress(List<ClassProgressVO> classesProgress) {
		this.classesProgress = classesProgress;
	}	
}
