package com.jerotoma.services.academic;

import com.jerotoma.common.models.academic.ProgramAcademicLevelPrerequisite;
import com.jerotoma.services.BaseService;

public interface ProgramAcademicLevelPrerequisiteService extends BaseService<ProgramAcademicLevelPrerequisite>{

	ProgramAcademicLevelPrerequisite getProgramAcademicLevelPrerequisite(Integer programId, Integer academicLevelID);

}
