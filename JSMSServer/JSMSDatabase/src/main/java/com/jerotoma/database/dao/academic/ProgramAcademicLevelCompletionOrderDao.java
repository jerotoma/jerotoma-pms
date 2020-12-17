package com.jerotoma.database.dao.academic;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jerotoma.common.models.academic.ProgramAcademicLevelCompletionOrder;

@Repository
@Transactional
public interface ProgramAcademicLevelCompletionOrderDao extends JpaRepository<ProgramAcademicLevelCompletionOrder, Integer> {

}
