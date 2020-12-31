package com.jerotoma.database.dao.academic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jerotoma.common.models.academic.Department;

public interface DepartmentDao extends JpaRepository<Department, Integer> {

}
