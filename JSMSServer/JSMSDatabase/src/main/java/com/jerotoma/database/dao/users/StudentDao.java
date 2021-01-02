package com.jerotoma.database.dao.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jerotoma.common.models.users.students.Student;

@Repository
public interface StudentDao extends JpaRepository<Student, Integer> {
	
}
