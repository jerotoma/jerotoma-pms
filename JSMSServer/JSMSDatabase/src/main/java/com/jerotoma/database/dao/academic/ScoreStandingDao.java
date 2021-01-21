package com.jerotoma.database.dao.academic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jerotoma.common.models.academic.ScoreStanding;

public interface ScoreStandingDao extends JpaRepository<ScoreStanding, Integer> {

	@Query("SELECT ss FROM ScoreStanding ss WHERE ?1 BETWEEN ss.minScore AND ss.maxScore")
	ScoreStanding findScoreStandingByScore(Double score);
}
