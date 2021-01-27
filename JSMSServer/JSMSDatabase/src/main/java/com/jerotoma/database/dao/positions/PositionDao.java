package com.jerotoma.database.dao.positions;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jerotoma.common.models.positions.Position;

public interface PositionDao extends JpaRepository<Position, Integer>  {	
}
