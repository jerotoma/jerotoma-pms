package com.jerotoma.database.dao.academic;

import java.sql.SQLException;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.jerotoma.common.models.users.students.StudentAcademicLevel;

public interface StudentAcademicLevelDao extends JpaRepository<StudentAcademicLevel, Integer> {

	@Query("SELECT sal FROM StudentAcademicLevel sal WHERE sal.student.id = ?1 AND sal.academicYear.id = ?2 AND sal.academicLevel.id = ?3")
	StudentAcademicLevel findStudentAcademicLevel(Integer studentId, Integer academicYearId, Integer academicLevelId) throws SQLException;
	
	@Query("SELECT sal FROM StudentAcademicLevel sal WHERE sal.student.id = ?1 AND sal.academicYear.id = ?2 AND sal.academicLevel.id = ?3 AND sal.stream.id = ?4")
	StudentAcademicLevel findStudentAcademicLevel(Integer studentId, Integer academicYearId, Integer academicLevelId, Integer streamlId) throws SQLException;

	@Modifying
	@Query("UPDATE StudentAcademicLevel sal SET sal.isCurrentAcademicLevel = false WHERE sal.student.id = ?1")
	void updateAllCurrentAcademicLevel(Integer studentId) throws SQLException;
}
