package com.jerotoma.database.dao.academic;

import java.sql.SQLException;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jerotoma.common.models.users.students.StudentAcademicLevel;

public interface StudentAcademicLevelDao extends JpaRepository<StudentAcademicLevel, Integer> {

	@Query("SELECT sal FROM StudentAcademicLevel sal WHERE sal.student.id = ?1 AND sal.academicLevel.id = ?2 AND sal.academicYear.id = ?3")
	StudentAcademicLevel findStudentAcademicLevel(Integer studentId, Integer academicLevelId, Integer academicYearId) throws SQLException;
}
