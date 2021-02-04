package com.jerotoma.services.students;

import java.sql.SQLException;

import com.jerotoma.common.models.users.students.StudentAcademicLevel.Fields;
import com.jerotoma.common.viewobjects.StudentAcademicLevelsProgress;
import com.jerotoma.common.viewobjects.StudentClassesProgressVO;

public interface StudentProgressService {

	public StudentClassesProgressVO findStudentClassesProgressByStudentId(Integer studentId);
	public StudentAcademicLevelsProgress findStudentAcademicLevelsProgressByStudentId(Integer studentId) throws SQLException;
	public StudentAcademicLevelsProgress updateStudentAcademicLevelsProgress(Fields studentAcademicLevelField) throws SQLException;

}
