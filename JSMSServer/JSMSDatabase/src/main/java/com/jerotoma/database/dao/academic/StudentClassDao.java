package com.jerotoma.database.dao.academic;

import java.sql.SQLException;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.jerotoma.common.models.users.students.StudentClass;

public interface StudentClassDao extends JpaRepository<StudentClass, Integer> {

	@Query("SELECT sc FROM StudentClass sc WHERE sc.mClass.id = ?1 AND sc.studentAcademicLevel.id = ?2")
	StudentClass findStudentClass(Integer classId, Integer studentAcademicLevelId) throws SQLException;

	@Transactional
	@Modifying
	@Query("DELETE FROM StudentClass sc WHERE sc.mClass.id = ?1 AND sc.studentAcademicLevel.id = ?2")
	void deleteStudentClass(Integer jClassId, Integer studentAcademicLevelId) throws SQLException;

}
