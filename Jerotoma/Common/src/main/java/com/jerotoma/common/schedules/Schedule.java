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
		
	public Schedule(ScheduledData data) {
		this.data = data;
		this.classes = new ArrayList<>(data.getNumberOfClasses());
	}
	
	public Schedule initialize() {
		if (this.data.getMeetingTimes().isEmpty()) {
			throw new RuntimeException("Meeting Time can not be empty to continue");
		}
		
		if (this.data.getRooms().isEmpty()) {
			throw new RuntimeException("Room can not be empty to continue");
		}
		
		if (this.data.getTeachers().isEmpty()) {
			throw new RuntimeException("Teacher can not be empty to continue");
		}
		
		if (this.data.getDepartments().isEmpty()) {
			throw new RuntimeException("Department can not be empty to continue");
		}		
		new ArrayList<DepartmentVO>(this.data.getDepartments()).forEach(department -> {
			department.getCourses().forEach(course -> {
				if (course.getAcademicLevel().getId().equals(this.data.getAcademicLevel().getId())) {
					ClassVO classVO = new ClassVO(this.classNumber++, department, course);
					classVO.setMeetingTime(this.data.getMeetingTimes().get((int)(this.data.getMeetingTimes().size() * Math.random())));
					classVO.setRoom(this.data.getRooms().get((int)(data.getRooms().size() * Math.random())));
					classVO.setCapacity(classVO.getRoom().getCapacity());
					classVO.setTeacher(this.data.getTeachers().get((int)(this.data.getTeachers().size() * Math.random())));
					this.classes.add(classVO);
				}				
			});
		});		
		return this;
	}

	public int getNumberOfConflicts() {
		return this.numberOfConflicts;
	}

	public int getNumberOfClasses() {
		return this.classNumber;
	}
		
	public List<ClassVO> getClasses() {
		this.isFitnessChanged = false;
		return this.classes;
	}

	public double getFitness() {
		if(this.isFitnessChanged) {
			this.fitness = calculateFitness();
			this.isFitnessChanged = false;
		}
		return this.fitness;
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
