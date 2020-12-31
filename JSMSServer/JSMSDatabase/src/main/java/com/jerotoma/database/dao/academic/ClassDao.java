package com.jerotoma.database.dao.academic;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jerotoma.common.models.academic.Class;

public interface ClassDao extends JpaRepository<Class, Integer> {

}
