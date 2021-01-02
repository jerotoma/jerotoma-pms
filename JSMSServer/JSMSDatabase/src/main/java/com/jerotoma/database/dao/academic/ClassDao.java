package com.jerotoma.database.dao.academic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jerotoma.common.models.academic.Class;

@Repository
public interface ClassDao extends JpaRepository<Class, Integer> {

}
