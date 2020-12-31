package com.jerotoma.database.dao.academic;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jerotoma.common.models.academic.AcademicLevel;

public interface AcademicLevelDao extends JpaRepository<AcademicLevel, Integer> {
	
	@Query("SELECT al FROM AcademicLevel al")
	List<AcademicLevel> getAllAcademicLevels();

	@Query("SELECT al FROM AcademicLevel al WHERE al.code = ?1")
	AcademicLevel findObjectUniqueKey(String uniqueKey);

}
