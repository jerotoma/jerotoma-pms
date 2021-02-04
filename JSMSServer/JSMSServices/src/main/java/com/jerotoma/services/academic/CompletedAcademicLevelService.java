package com.jerotoma.services.academic;

import java.util.Set;

import com.jerotoma.common.models.users.CompletedAcademicLevel;
import com.jerotoma.services.BaseService;

public interface CompletedAcademicLevelService  extends BaseService<CompletedAcademicLevel> {

	public boolean exists(Integer studentId, Integer academicLevelId);
	
	public CompletedAcademicLevel findCompletedAcademicLevel(Integer studentId, Integer academicLevelId);

	public Set<CompletedAcademicLevel> getCompletedAcademicLevels(Integer studentId);

}
