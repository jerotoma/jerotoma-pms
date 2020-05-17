package com.jerotoma.common.schedules;

import java.util.List;
import java.util.stream.IntStream;

import com.jerotoma.common.constants.ScheduleConstant;

public class GeneticAlgorithm {
	
	ScheduledData data;

	public GeneticAlgorithm(ScheduledData data) {
		this.data = data;
	}
	
	public Population evolve(Population population) {
		return mutatePopulation(crossoverPopulation(population));
	}
	
	public Population crossoverPopulation(Population population) {
		Population crossoverPopulation = new Population(population.getSchedules().size(), data);
		
		IntStream.range(0, ScheduleConstant.NUMBER_OF_ELITE_SCHEDULES)
			.forEach(x -> crossoverPopulation.getSchedules().set(x, population.getSchedules().get(x)));
		
		IntStream.range(ScheduleConstant.NUMBER_OF_ELITE_SCHEDULES,  
				population.getSchedules().size()).forEach(x -> {			
			if (ScheduleConstant.CROSSOVER_RATE > Math.random()) {
				Schedule schedule1 = selectTournamentPopulation(population).sortByFitness().getSchedules().get(0); 
				Schedule schedule2 = selectTournamentPopulation(population).sortByFitness().getSchedules().get(0); 
				crossoverPopulation.getSchedules().set(x, crossoverSchedule(schedule1, schedule2));
			} else {
				crossoverPopulation.getSchedules().set(x, population.getSchedules().get(x));
			}
			
		});		
		return crossoverPopulation;
	}
	
	public Population selectTournamentPopulation(Population population) {
		Population tournamentPopulation = new Population(ScheduleConstant.TOURNAMENT_SELECTION_SIZE, data);
		IntStream.range(0, ScheduleConstant.TOURNAMENT_SELECTION_SIZE).forEach(x -> {
			tournamentPopulation.getSchedules().set(x, 
					population.getSchedules().get((int)(Math.random() * population.getSchedules().size())));
		});
		return tournamentPopulation;
	}
	
	public Population mutatePopulation(Population population) {
		Population mutatePopulation = new Population(population.getSchedules().size(), data);
		List<Schedule> schedules = mutatePopulation.getSchedules();
		IntStream.range(0, ScheduleConstant.NUMBER_OF_ELITE_SCHEDULES).forEach(x -> {
			mutatePopulation.getSchedules().set(x, schedules.set(x, population.getSchedules().get(x)));
		});
		
		IntStream.range(ScheduleConstant.NUMBER_OF_ELITE_SCHEDULES, population.getSchedules().size()).forEach(x -> {
			schedules.set(x, mutateSchedule(population.getSchedules().get(x)));
		});
		
		return mutatePopulation;
	}
	
	public Schedule mutateSchedule(Schedule mutateSchedule) {
		Schedule schedule = new Schedule(data);
		int size = mutateSchedule.getClasses().size();
		if (size > 0) {
			IntStream.range(0, size).forEach(x -> {
				if (ScheduleConstant.MUTATION_RATE > Math.random()) {
					int scheduleClassesSize = schedule.getClasses().size();
					if (scheduleClassesSize > 0) {
						 mutateSchedule.getClasses().set(x, schedule.getClasses().get(x));
					}		
				} 
			});	
		}				
		return mutateSchedule;
	}
	
	public Schedule crossoverSchedule(Schedule schedule1, Schedule schedule2) {
		Schedule crossoverSchedule = new Schedule(data);
		IntStream.range(0, crossoverSchedule.getClasses().size()).forEach(x -> {
			if (Math.random() > 0.5) {
				crossoverSchedule.getClasses().set(x, schedule1.getClasses().get(x));
			} else {
				crossoverSchedule.getClasses().set(x, schedule2.getClasses().get(x));
			}
		});		
		return crossoverSchedule;
	}
	
	
	

}
