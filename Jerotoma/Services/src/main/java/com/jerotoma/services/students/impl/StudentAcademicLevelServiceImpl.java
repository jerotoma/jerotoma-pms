package com.jerotoma.services.students.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.CompletionStatus;
import com.jerotoma.common.models.academic.AcademicLevel;
import com.jerotoma.common.models.academic.AcademicYear;
import com.jerotoma.common.models.academic.Class;
import com.jerotoma.common.models.academic.StudentAcademicLevel;
import com.jerotoma.common.models.academic.StudentAcademicLevel.Fields;
import com.jerotoma.common.models.academic.StudentClass;
import com.jerotoma.common.models.students.Student;
import com.jerotoma.common.utils.CalendarUtil;
import com.jerotoma.common.viewobjects.UserVO;
import com.jerotoma.database.dao.academic.StudentAcademicLevelDao;
import com.jerotoma.services.assemblers.academic.AssemblerStudentAcademicLevelService;
import com.jerotoma.services.assemblers.academic.AssemblerStudentClassService;
import com.jerotoma.services.courses.AcademicLevelService;
import com.jerotoma.services.courses.AcademicYearService;
import com.jerotoma.services.courses.ClassService;
import com.jerotoma.services.courses.CourseService;
import com.jerotoma.services.courses.StudentClassService;
import com.jerotoma.services.students.StudentAcademicLevelService;
import com.jerotoma.services.users.StudentService;

@Service
@Transactional
public class StudentAcademicLevelServiceImpl implements StudentAcademicLevelService {
	
	@Autowired StudentAcademicLevelDao studentAcademicLevelDao;
	@Autowired StudentClassService studentClassService;
	@Autowired AssemblerStudentClassService assemblerstudentClassService;
	@Autowired AssemblerStudentAcademicLevelService assemblerStudentClassService;
	@Autowired AcademicYearService academicYearService;	
	@Autowired AcademicLevelService academicLevelService;
	@Autowired CourseService courseService;
	@Autowired ClassService jClassService;
	@Autowired StudentService studentService;

	@Override
	public StudentAcademicLevel findObject(Integer primaryKey) throws SQLException {
		return studentAcademicLevelDao.findObject(primaryKey);
	}

	@Override
	public StudentAcademicLevel findObjectUniqueKey(String uniqueKey) throws SQLException {
		return studentAcademicLevelDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public StudentAcademicLevel createObject(StudentAcademicLevel object) throws SQLException {
		return studentAcademicLevelDao.createObject(object);
	}

	@Override
	public StudentAcademicLevel updateObject(StudentAcademicLevel object) throws SQLException {
		return studentAcademicLevelDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(StudentAcademicLevel object) throws SQLException {
		return studentAcademicLevelDao.deleteObject(object);
	}

	@Override
	public List<StudentAcademicLevel> loadList(QueryParam queryParam) throws SQLException {
		return studentAcademicLevelDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return studentAcademicLevelDao.loadMapList(queryParam);
	}

	@Override
	public List<StudentAcademicLevel> createBatchObject(List<StudentAcademicLevel> studentClasses) throws SQLException {
		return studentAcademicLevelDao.createBatchObject(studentClasses);
	}

	@Override
	public StudentAcademicLevel findStudentAcademicLevel(Integer studentId, Integer academicLevelId, Integer academicYearId) throws SQLException {
		return studentAcademicLevelDao.findStudentAcademicLevel(studentId, academicLevelId, academicYearId);
	}

	@Override
	public StudentAcademicLevel updateStudentAcademicLevel(Fields studentAcademicLevelField, UserVO authUser) throws SQLException {
		
		
		List<StudentClass> studentClasses = new ArrayList<>();		
		
		Student student = studentService.findObject(studentAcademicLevelField.getStudentId());
		AcademicLevel academicLevel  = academicLevelService.findObject(studentAcademicLevelField.getAcademicLevelId());
		AcademicYear academicYear = academicYearService.findObject(studentAcademicLevelField.getAcademicYearId());
		
		CompletionStatus completionStatus = CompletionStatus.getCompletionStatusfromID(studentAcademicLevelField.getAcademicLevelId());
		if (completionStatus.equals(CompletionStatus.NO_STATUS)) {
			completionStatus = CompletionStatus.IN_PROGRESS;
		}
		
		StudentAcademicLevel studentAcademicLevel = findObject(studentAcademicLevelField.getId());
		studentAcademicLevel.setAcademicLevel(academicLevel);
		studentAcademicLevel.setCompletionStatus(completionStatus);
		studentAcademicLevel.setAcademicYear(academicYear);
		studentAcademicLevel.setStudent(student);	
		studentAcademicLevel.setUpdatedBy(authUser.getUserId());
		studentAcademicLevel.setUpdatedOn(CalendarUtil.getTodaysDate());
		studentAcademicLevel = updateObject(studentAcademicLevel);			
		
		for (Integer classId : studentAcademicLevelField.getClassIds()) {		
			Class jClass = jClassService.findObject(classId);
			
			if (!academicYear.getId().equals(jClass.getAcademicYear().getId())) {
				throw new RuntimeException("Course : " + jClass.getCourse().getName() + " is not available for Academic Year : " + academicYear.getYearOfStudy() + " - " + academicYear.getName());
			}
			StudentClass studentClass = null;
			if (assemblerstudentClassService.doesStudentClassRecordExist(classId, studentAcademicLevel.getId())) {
				studentClass  = studentClassService.findStudentClass(classId, studentAcademicLevel.getId());
			}
						
			if (studentClass == null) {
				studentClass = new StudentClass();
			}
			
			studentClass.setmClass(jClass);	
			studentClass.setCompletionStatusId(completionStatus.getID());				
			studentClass.setStudentAcademicLevel(studentAcademicLevel);
			studentClass.setUpdatedBy(authUser.getUserId());
			studentClass.setCreatedOn(CalendarUtil.getTodaysDate());
			studentClass.setUpdatedOn(CalendarUtil.getTodaysDate());
			studentClasses.add(studentClass);				
		}
		studentClassService.updateBatchObject(studentClasses);
		return studentAcademicLevel;
	}

	@Override
	public StudentAcademicLevel createStudentAcademicLevel(Fields studentAcademicLevelField, UserVO authUser)
			throws SQLException {
		StudentClass studentClass;
		List<StudentClass> studentClasses = new ArrayList<>();		
		
		Student student = studentService.findObject(studentAcademicLevelField.getStudentId());
		AcademicLevel academicLevel  = academicLevelService.findObject(studentAcademicLevelField.getAcademicLevelId());
		AcademicYear academicYear = academicYearService.findObject(studentAcademicLevelField.getAcademicYearId());
		CompletionStatus completionStatus = CompletionStatus.IN_PROGRESS;
		
		StudentAcademicLevel studentAcademicLevel = new StudentAcademicLevel();
		studentAcademicLevel.setAcademicLevel(academicLevel);
		studentAcademicLevel.setCompletionStatusId(completionStatus.getID());
		studentAcademicLevel.setAcademicYear(academicYear);
		studentAcademicLevel.setStudent(student);
		studentAcademicLevel.setUpdatedBy(authUser.getUserId());
		studentAcademicLevel.setCreatedOn(CalendarUtil.getTodaysDate());
		studentAcademicLevel.setUpdatedOn(CalendarUtil.getTodaysDate());
		studentAcademicLevel = createObject(studentAcademicLevel);			
		
		for (Integer classId : studentAcademicLevelField.getClassIds()) {		
			Class jClass = jClassService.findObject(classId);				
			studentClass = new StudentClass();
			studentClass.setmClass(jClass);	
			studentClass.setCompletionStatusId(completionStatus.getID());				
			studentClass.setStudentAcademicLevel(studentAcademicLevel);
			studentClass.setUpdatedBy(authUser.getUserId());
			studentClass.setCreatedOn(CalendarUtil.getTodaysDate());
			studentClass.setUpdatedOn(CalendarUtil.getTodaysDate());
			studentClasses.add(studentClass);				
		}
		studentClassService.createBatchObject(studentClasses);
		return studentAcademicLevel;
	}

	@Override
	public boolean deleteStudentClass(Fields studentAcademicLevelField) throws SQLException {
		Integer studentId = studentAcademicLevelField.getStudentId(); 
		Integer academicLevelId = studentAcademicLevelField.getAcademicLevelId(); 
		Integer academicYearId = studentAcademicLevelField.getAcademicYearId(); 
		Integer jClassId = studentAcademicLevelField.getClassIds().get(0);
		
		StudentAcademicLevel studentAcademicLevel = studentAcademicLevelDao.findStudentAcademicLevel(studentId, academicLevelId, academicYearId);
		return studentClassService.deleteStudentClass(studentAcademicLevel.getId(), jClassId);
	}

}
