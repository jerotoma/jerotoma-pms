package com.jerotoma.database.dao.academic;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jerotoma.common.models.users.CompletedAcademicLevel;

@Repository
public interface CompletedAcademicLevelDao  extends JpaRepository<CompletedAcademicLevel, Integer> {

	@Query("SELECT CASE WHEN (count(coal) > 0) THEN true ELSE false END FROM CompletedAcademicLevel coal WHERE coal.student.id = ?1 AND  coal.academicLevel.id = ?2")
	public boolean exists(Integer studentId, Integer academicLevelId);
	
	@Query("SELECT coal FROM CompletedAcademicLevel coal WHERE coal.student.id = ?1 AND  coal.academicLevel.id = ?2")
	public CompletedAcademicLevel findCompletedAcademicLevel(Integer studentId, Integer academicLevelId);

	@Query("SELECT coal FROM CompletedAcademicLevel coal WHERE coal.student.id = ?1")
	public List<CompletedAcademicLevel> getCompletedAcademicLevels(Integer studentId);

}
