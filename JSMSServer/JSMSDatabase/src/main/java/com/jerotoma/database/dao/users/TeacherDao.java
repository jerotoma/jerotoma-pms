package com.jerotoma.database.dao.users;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jerotoma.common.models.users.Teacher;

public interface TeacherDao extends JpaRepository<Teacher, Integer> {
	
	//@Query("SELECT t FROM Teacher t WHERE t.teacherCode = ?1 ")
	//public Teacher findObjectUniqueKey(String uniqueKey);

}
