package com.jerotoma.common.schedules;

import java.util.ArrayList;
import java.util.List;

import com.jerotoma.common.viewobjects.ClassVO;
import com.jerotoma.common.viewobjects.DepartmentVO;

public class Schedule {
	private List<ClassVO> classes;
	private ScheduleData data;
	private boolean isFitnessChanged = true;
	private double fitness = -1;
	private int numberOfConflicts = 0;
	private int classNumber = 0;
	
	public Schedule(ScheduleData data) {
		this.data = data;
		this.classes = new ArrayList<>(data.getNumberOfClasses());
	}
	
	public Schedule initialize() {
		new ArrayList<DepartmentVO>(data.getDepartments()).forEach(department -> {
			department.getCourses().forEach(course -> {
				ClassVO classVO = new ClassVO(classNumber++, department, course);
				classVO.setMeetingTime(data.getMeetingTimes().get((int)(data.getMeetingTimes().size() * Math.random())));
				classVO.setRoom(data.getRooms().get((int)(data.getRooms().size() * Math.random())));
				classVO.setTeacher(data.getTeachers().get((int)(data.getTeachers().size() * Math.random())));
				classes.add(classVO);
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
		 numberOfConflicts = 0;
		 
		 this.classes.forEach(mClass -> {
			 if (mClass.getRoom().getCapacity() < mClass.getCapacity()) {
				 numberOfConflicts++;
			 }
			 this.classes.stream().filter(yClass -> this.classes.indexOf(yClass) >= this.classes.indexOf(mClass)).forEach(yClass -> {
				 if (mClass.getMeetingTime().getId().equals(yClass.getMeetingTime().getId()) && mClass.getId() != yClass.getId()) {
					 if(mClass.getRoom().getId().equals(yClass.getRoom().getId())) {
						 numberOfConflicts++;
					 }
					 if(mClass.getTeacher().getId().equals(yClass.getTeacher().getId())) {
						 numberOfConflicts++;
					 }
				 }				 
			 });		 
		 });
		return 1/(double)(classNumber + 1);
	}

}
