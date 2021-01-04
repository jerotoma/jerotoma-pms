package com.jerotoma.database.dao.academic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jerotoma.common.models.academic.Program;

public interface ProgramDao extends JpaRepository<Program, Integer>{

	@Query("select p from Program p where p.code = ?1")
	Program findObjectUniqueKey(String uniqueKey);
}
