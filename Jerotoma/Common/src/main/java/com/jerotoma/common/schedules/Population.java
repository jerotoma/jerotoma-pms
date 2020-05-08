package com.jerotoma.common.schedules;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import com.jerotoma.common.viewobjects.ClassVO;

public class Population {
	
	private List<Schedule> schedules;
	
	public Population(int size, List<ClassVO> classes) {
		schedules = new ArrayList<>(size);
		IntStream.range(0, size).forEach(x -> schedules.add(new Schedule(classes)));
	}
	

	public List<Schedule> getSchedules() {
		return schedules;
	}
	
	public Population sortByFitness() {
		schedules.sort((schedul1, schedule2) -> {
			int returnValue = 0;
			if (schedul1.getFitness() > schedule2.getFitness()) {
				returnValue = -1;
			} else if (schedul1.getFitness() < schedule2.getFitness()) {
				returnValue = 1;
			}
			return returnValue;
		});
		return this;
	}
}
