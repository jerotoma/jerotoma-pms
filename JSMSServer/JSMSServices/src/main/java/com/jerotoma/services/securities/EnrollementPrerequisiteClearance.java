package com.jerotoma.services.securities;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.models.academic.AcademicLevel;
import com.jerotoma.common.models.academic.ProgramAcademicLevelPrerequisite;
import com.jerotoma.common.models.users.CompletedAcademicLevel;
import com.jerotoma.services.academic.CompletedAcademicLevelService;

@Service
public class EnrollementPrerequisiteClearance {
	
	@Autowired CompletedAcademicLevelService  completedAcademicLevelService;
	
	public boolean hasMetPrerequisite(AcademicLevel academicLevel, Integer studentId) {
		Set<ProgramAcademicLevelPrerequisite> prerequisites = academicLevel.getPrerequisites();
		Set<CompletedAcademicLevel>  completedAcademicLevels = completedAcademicLevelService.getCompletedAcademicLevels(studentId);
		
		if (prerequisites == null || prerequisites.isEmpty()) {
			return true;
		}
		
		if (completedAcademicLevels == null || completedAcademicLevels.isEmpty()) { 
			return false; 
		}
		
		for (ProgramAcademicLevelPrerequisite prerequisite : prerequisites) {			
			AcademicLevel academicLevelPrerequisite = prerequisite.getPrerequisiteAcademicLevel();			
			boolean found = false;
			for (CompletedAcademicLevel completedAcademicLevel : completedAcademicLevels) {
				if (completedAcademicLevel.getAcademicLevel().equals(academicLevelPrerequisite)) {
					found = true;
				}
			}
			
			if (!found) {
				return false;
			}
		}		
		return true;
	}
}
