package com.jerotoma.database.dao.academic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jerotoma.common.models.academic.AcademicYear;

public interface AcademicYearDao extends JpaRepository<AcademicYear, Integer> {

	@Query("SELECT ay from AcademicYear ay where ay.code = ?1")
	AcademicYear findObjectUniqueKey(String uniqueKey);

}
