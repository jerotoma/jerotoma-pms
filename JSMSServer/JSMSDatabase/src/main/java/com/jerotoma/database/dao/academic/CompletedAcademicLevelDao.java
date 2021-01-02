package com.jerotoma.database.dao.academic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jerotoma.common.models.users.CompletedAcademicLevel;

@Repository
public interface CompletedAcademicLevelDao  extends JpaRepository<CompletedAcademicLevel, Integer> {

	CompletedAcademicLevel findObjectUniqueKey(String uniqueKey);

}
