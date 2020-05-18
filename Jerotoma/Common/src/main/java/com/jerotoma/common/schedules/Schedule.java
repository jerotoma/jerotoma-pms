package com.jerotoma.common.schedules;

import java.util.ArrayList;
import java.util.List;

import com.jerotoma.common.viewobjects.ClassVO;
import com.jerotoma.common.viewobjects.DepartmentVO;

public class Schedule {
	private List<ClassVO> classes;
	private ScheduledData data;
	private boolean isFitnessChanged = true;
	private double fitness = -1;
	private int numberOfConflicts = 0;
	private int classNumber = 0;
	
	int classCapacity = 34;
	
	public Schedule(ScheduledData data) {
		this.data = data;
		this.classes = new ArrayList<>(data.getNumberOfClasses());
	}
	
	public Schedule initialize() {
		if (data.getMeetingTimes().isEmpty()) {
			return this;
		}
		
		if (data.getRooms().isEmpty()) {
			return this;
		}
		
		if (data.getTeachers().isEmpty()) {
			return this;
		}
		
		if (data.getDepartments().isEmpty()) {
			return this;
		}		
		new ArrayList<DepartmentVO>(data.getDepartments()).forEach(department -> {
			department.getCourses().forEach(course -> {
				if (course.getAcademicYear().getId().equals(data.getAcademicYear().getId())) {
					ClassVO classVO = new ClassVO(classNumber++, department, course, classCapacity);
					classVO.setMeetingTime(data.getMeetingTimes().get((int)(data.getMeetingTimes().size() * Math.random())));
					classVO.setRoom(data.getRooms().get((int)(data.getRooms().size() * Math.random())));
					classVO.setTeacher(data.getTeachers().get((int)(data.getTeachers().size() * Math.random())));
					classes.add(classVO);
				}				
			});
		});		
		return this;
	}

	public int getNumberOfConflicts() {
		return numberOfConflicts;
	}

	public int getNumberOfClasses() {
		return classNumber;
	}
		
	public List<ClassVO> getClasses() {
		isFitnessChanged = false;
		return classes;
	}

	public double getFitness() {
		if(isFitnessChanged) {
			fitness = calculateFitness();
			isFitnessChanged = false;
		}
		return fitness;
	}
	
	public double calculateFitness() {
		 this.numberOfConflicts = 0;
		 
		 this.classes.forEach(xClass -> {
			 if (xClass.getRoom().getCapacity() < xClass.getCapacity()) {
				 this.numberOfConflicts++;
			 }
			 this.classes.stream().filter(yClass -> this.classes.indexOf(yClass) >= this.classes.indexOf(xClass)).forEach(yClass -> {
				 if (xClass.getMeetingTime().getId().equals(yClass.getMeetingTime().getId()) && xClass.getId() != yClass.getId()) {
					 if(xClass.getRoom().getId().equals(yClass.getRoom().getId())) {
						 this.numberOfConflicts++;
					 }
					 if(xClass.getTeacher().getId().equals(yClass.getTeacher().getId())) {
						 this.numberOfConflicts++;
					 }
				 }				 
			 });		 
		 });
		return 1/(double)(this.numberOfConflicts + 1);
	}

}
