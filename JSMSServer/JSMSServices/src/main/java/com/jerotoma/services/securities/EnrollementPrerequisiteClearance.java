package com.jerotoma.services.securities;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.jerotoma.common.models.academic.AcademicLevel;
import com.jerotoma.common.models.academic.ProgramAcademicLevelPrerequisite;
import com.jerotoma.common.models.users.CompletedAcademicLevel;
import com.jerotoma.common.models.users.students.Student;

@Service
public class EnrollementPrerequisiteClearance {
	
	public boolean hasMetPrerequisite(AcademicLevel academicLevel, Student student) {
		Set<ProgramAcademicLevelPrerequisite> prerequisites = academicLevel.getPrerequisites();
		Set<CompletedAcademicLevel>  completedAcademicLevels = student.getCompletedAcademicLevels();
		
		if (prerequisites == null || prerequisites.isEmpty()) {
			return true;
		}
		
		if (completedAcademicLevels == null || completedAcademicLevels.isEmpty()) { 
			return false; 
		}
		
		for (ProgramAcademicLevelPrerequisite prerequisite : prerequisites) {
			boolean found = false;
			for (CompletedAcademicLevel completedAcademicLevel : completedAcademicLevels) {
				if (completedAcademicLevel.getAcademicLevelId().equals(prerequisite.getAcademicLevel().getId())) {
					found = true;
				}
			}
			
			if (!found) {
				return false;
			}
		}		
		return false;
	}
}
