package com.jerotoma.services.students;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.models.users.students.StudentAcademicLevel;
import com.jerotoma.common.models.users.students.StudentAcademicLevel.Fields;
import com.jerotoma.common.viewobjects.UserVO;
import com.jerotoma.services.BaseService;

public interface StudentAcademicLevelService extends BaseService<StudentAcademicLevel> {

	public StudentAcademicLevel findStudentAcademicLevel(Integer studentId, Integer academicLevelId, Integer academicYearId) throws SQLException;
	
	public StudentAcademicLevel findStudentAcademicLevel(Integer studentId, Integer academicYearId,	Integer academicLevelId, Integer streamId) throws SQLException;
	
	public List<StudentAcademicLevel> createBatchObject(List<StudentAcademicLevel> studentAcademicLevel) throws SQLException;

	public StudentAcademicLevel updateStudentAcademicLevel(Fields studentAcademicLevelField, UserVO authUser) throws SQLException;
	
	public StudentAcademicLevel updateStudentAcademicLevelClasses(Fields studentAcademicLevelField, UserVO authUser) throws SQLException;
	
	public StudentAcademicLevel updateStudentAcademicLevelClasses(StudentAcademicLevel studentAcademicLevel, Fields studentAcademicLevelField, UserVO authUser) throws SQLException;

	public StudentAcademicLevel createStudentAcademicLevelClasses(Fields studentAcademicLevelField, UserVO authUser) throws SQLException;

	public boolean deleteStudentClass(Fields studentAcademicLevelField) throws SQLException;
	
	public StudentAcademicLevel createStudentAcademicLevel(Fields studentAcademicLevelField, UserVO authUser) throws SQLException;;
}
