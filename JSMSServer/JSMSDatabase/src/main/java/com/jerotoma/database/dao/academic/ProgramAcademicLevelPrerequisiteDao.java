package com.jerotoma.database.dao.academic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jerotoma.common.models.academic.ProgramAcademicLevelPrerequisite;

public interface ProgramAcademicLevelPrerequisiteDao extends JpaRepository<ProgramAcademicLevelPrerequisite, Integer> {

	@Query("SELECT prerequisite FROM ProgramAcademicLevelPrerequisite prerequisite WHERE prerequisite.program.id = ?1 AND  prerequisite.academicLevel.id = ?2")
	public ProgramAcademicLevelPrerequisite getProgramAcademicLevelPrerequisite(Integer programId, Integer academicLevelID);

}
