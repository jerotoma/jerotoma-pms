package com.jerotoma.common.schedules;

import java.util.List;

import com.jerotoma.common.viewobjects.ClassVO;

public class Schedule {
	private List<ClassVO> classes;
	private boolean isFitnessChanged = true;
	private double fitness = -1;
	private int numberOfConflicts = 0;
	private int numberOfClasses = 0;
	
	public Schedule(List<ClassVO> classes) {
		this.classes = classes;
	}
	
	public Schedule initialize() {
		return this;
	}

	public int getNumberOfConflicts() {
		return numberOfConflicts;
	}

	public int getNumberOfClasses() {
		return numberOfClasses;
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
		return 1/(double)(numberOfClasses + 1);
	}

}
