package com.jerotoma.services.students.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.ClassConstant;
import com.jerotoma.common.constants.CompletionStatus;
import com.jerotoma.common.constants.ErrorMessageConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.models.academic.AcademicLevel;
import com.jerotoma.common.models.academic.AcademicYear;
import com.jerotoma.common.models.academic.Class;
import com.jerotoma.common.models.academic.Stream;
import com.jerotoma.common.models.users.students.Student;
import com.jerotoma.common.models.users.students.StudentAcademicLevel;
import com.jerotoma.common.models.users.students.StudentAcademicLevel.Fields;
import com.jerotoma.common.models.users.students.StudentClass;
import com.jerotoma.common.utils.CalendarUtil;
import com.jerotoma.common.viewobjects.UserVO;
import com.jerotoma.database.dao.academic.StudentAcademicLevelDao;
import com.jerotoma.services.academic.AcademicLevelService;
import com.jerotoma.services.academic.AcademicYearService;
import com.jerotoma.services.academic.ClassService;
import com.jerotoma.services.academic.CourseService;
import com.jerotoma.services.academic.StreamService;
import com.jerotoma.services.academic.StudentClassService;
import com.jerotoma.services.assemblers.academic.AssemblerStudentAcademicLevelService;
import com.jerotoma.services.assemblers.academic.AssemblerStudentClassService;
import com.jerotoma.services.securities.EnrollementPrerequisiteClearance;
import com.jerotoma.services.students.StudentAcademicLevelService;
import com.jerotoma.services.users.StudentService;
import com.jerotoma.services.utils.ServiceUtil;

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
	@Autowired StreamService streamService;
	@Autowired ClassService jClassService;
	@Autowired StudentService studentService;
	@Autowired EnrollementPrerequisiteClearance prerequisiteClearance;
	
	Date today = CalendarUtil.getTodaysDate();

	@Override
	public StudentAcademicLevel findObject(Integer primaryKey) throws SQLException {
		return ServiceUtil.getEntity(studentAcademicLevelDao.findById(primaryKey));
	}

	@Override
	public StudentAcademicLevel findObjectUniqueKey(String uniqueKey) throws SQLException {
		throw new RuntimeException(ErrorMessageConstant.METHOD_NOT_IMPLEMENTED);
	}

	@Override
	public StudentAcademicLevel createObject(StudentAcademicLevel object) throws SQLException {
		
		if (!prerequisiteClearance.hasMetPrerequisite(object.getAcademicLevel(), object.getStudent())) {
			throw new RuntimeException(ErrorMessageConstant.PREREQUISITE_NOT_MET);
		}		
		return studentAcademicLevelDao.save(object);
	}

	@Override
	public StudentAcademicLevel updateObject(StudentAcademicLevel object) throws SQLException {
		if (!prerequisiteClearance.hasMetPrerequisite(object.getAcademicLevel(), object.getStudent())) {
			throw new RuntimeException(ErrorMessageConstant.PREREQUISITE_NOT_MET);
		}
		return studentAcademicLevelDao.save(object);
	}

	@Override
	public Boolean deleteObject(StudentAcademicLevel object) throws SQLException {
		studentAcademicLevelDao.delete(object);
		return true;
	}

	@Override
	public List<StudentAcademicLevel> loadList(QueryParam queryParam) throws SQLException {
		if (queryParam == null) {
			return studentAcademicLevelDao.findAll();
		}		
		return studentAcademicLevelDao.findAll(ServiceUtil.getPageable(queryParam)).toList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		
		Map<String, Object> map = new HashMap<>();		
		Page<StudentAcademicLevel> pagestudentAcademicLevel = studentAcademicLevelDao.findAll(ServiceUtil.getPageable(queryParam));
		map.put(ClassConstant.CLASSES, pagestudentAcademicLevel.toList());
		map.put(SystemConstant.COUNT, pagestudentAcademicLevel.getTotalElements());
		map.put(SystemConstant.PAGE_COUNT, pagestudentAcademicLevel.getTotalPages());		
		return map;
	}

	@Override
	public List<StudentAcademicLevel> createBatchObject(List<StudentAcademicLevel> studentAcademicLevels) throws SQLException {
		
		List<StudentAcademicLevel> studentAcademicLevelList = new ArrayList<>();		
		for (StudentAcademicLevel studentAcademicLevel : studentAcademicLevels) {
			StudentAcademicLevel sal = createObject(studentAcademicLevel);
			studentAcademicLevelList.add(sal);			
		}
		return studentAcademicLevelList;
	}

	@Override
	public StudentAcademicLevel findStudentAcademicLevel(Integer studentId, Integer academicLevelId, Integer academicYearId) throws SQLException {
		return studentAcademicLevelDao.findStudentAcademicLevel(studentId, academicLevelId, academicYearId);
	}

	@Override
	public StudentAcademicLevel updateStudentAcademicLevelClasses(Fields studentAcademicLevelField, UserVO authUser) throws SQLException {
		List<StudentClass> studentClasses = new ArrayList<>();		
		
		Student student = studentService.findObject(studentAcademicLevelField.getStudentId());
		AcademicLevel academicLevel  = academicLevelService.findObject(studentAcademicLevelField.getAcademicLevelId());
		AcademicYear academicYear = academicYearService.findObject(studentAcademicLevelField.getAcademicYearId());
		
		CompletionStatus completionStatus = CompletionStatus.getCompletionStatusfromID(studentAcademicLevelField.getAcademicLevelId());
		if (completionStatus.equals(CompletionStatus.NOT_STARTED)) {
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
	public StudentAcademicLevel createStudentAcademicLevelClasses(Fields studentAcademicLevelField, UserVO authUser)
			throws SQLException {
		StudentClass studentClass;
		List<StudentClass> studentClasses = new ArrayList<>();		
		
		Student student = studentService.findObject(studentAcademicLevelField.getStudentId());
		AcademicLevel academicLevel  = academicLevelService.findObject(studentAcademicLevelField.getAcademicLevelId());
		AcademicYear academicYear = academicYearService.findObject(studentAcademicLevelField.getAcademicYearId());
		CompletionStatus completionStatus = CompletionStatus.IN_PROGRESS;
		
		StudentAcademicLevel studentAcademicLevel = studentAcademicLevelDao.findStudentAcademicLevel(student.getId(), academicLevel.getId(), academicYear.getId());
		studentAcademicLevel = studentAcademicLevel != null ? studentAcademicLevel : new StudentAcademicLevel();
		studentAcademicLevel.setAcademicLevel(academicLevel);
		studentAcademicLevel.setIsCurrentAcademicLevel(studentAcademicLevel == null ? true : studentAcademicLevelField.getIsCurrentStudentAcademicLevel());
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

	@Override
	public StudentAcademicLevel createStudentAcademicLevel(Fields studentAcademicLevelField, UserVO authUser) throws SQLException {
		
		Student student = studentService.findObject(studentAcademicLevelField.getStudentId());
		AcademicLevel academicLevel  = academicLevelService.findObject(studentAcademicLevelField.getAcademicLevelId());
		AcademicYear academicYear = academicYearService.findObject(studentAcademicLevelField.getAcademicYearId());

		CompletionStatus completionStatus = studentAcademicLevelField.getCommpletionStatusId() != null 
				?  CompletionStatus.getCompletionStatusfromID(studentAcademicLevelField.getCommpletionStatusId()) : CompletionStatus.IN_PROGRESS;
		
		StudentAcademicLevel studentAcademicLevel = new StudentAcademicLevel();
		
		if (studentAcademicLevelField.getIsCurrentStudentAcademicLevel()) {
			studentAcademicLevel.setIsCurrentAcademicLevel(studentAcademicLevelField.getIsCurrentStudentAcademicLevel());
			studentAcademicLevelDao.updateAllCurrentAcademicLevel(student.getId());
		}
		
		if (studentAcademicLevelField.getStreamId() != null) {	
			Stream stream = streamService.findObject(studentAcademicLevelField.getStreamId());
			studentAcademicLevel.setStream(stream);
		} 
		
		studentAcademicLevel.setAcademicLevel(academicLevel);
		studentAcademicLevel.setCompletionStatusId(completionStatus.getID());
		studentAcademicLevel.setAcademicYear(academicYear);
		studentAcademicLevel.setStudent(student);
		studentAcademicLevel.setUpdatedBy(authUser.getUserId());
		studentAcademicLevel.setCreatedOn(CalendarUtil.getTodaysDate());
		studentAcademicLevel.setUpdatedOn(CalendarUtil.getTodaysDate());
		studentAcademicLevel = createObject(studentAcademicLevel);
		
		if (studentAcademicLevelField.getIsCurrentStudentAcademicLevel()) {
			student.setAcademicLevelId(academicLevel.getId());
			student = studentService.updateObject(student);
			studentAcademicLevel.setStudent(student);
		}
		
		return studentAcademicLevel;	
	}

	@Override
	public StudentAcademicLevel updateStudentAcademicLevel(Fields studentAcademicLevelField, UserVO authUser)
			throws SQLException {
		
		Student student = studentService.findObject(studentAcademicLevelField.getStudentId());
		AcademicLevel academicLevel  = academicLevelService.findObject(studentAcademicLevelField.getAcademicLevelId());
		AcademicYear academicYear = academicYearService.findObject(studentAcademicLevelField.getAcademicYearId());
		
		CompletionStatus completionStatus = studentAcademicLevelField.getCommpletionStatusId() != null 
				?  CompletionStatus.getCompletionStatusfromID(studentAcademicLevelField.getCommpletionStatusId()) : CompletionStatus.IN_PROGRESS;
		StudentAcademicLevel studentAcademicLevel;
		
		if (studentAcademicLevelField.getStreamId() != null) {	
			Stream stream = streamService.findObject(studentAcademicLevelField.getStreamId());
			studentAcademicLevel = studentAcademicLevelDao.findStudentAcademicLevel(student.getId(), academicYear.getId(), academicLevel.getId(), stream.getId());
			studentAcademicLevel.setStream(stream);
		} else {
			studentAcademicLevel = studentAcademicLevelDao.findStudentAcademicLevel(student.getId(), academicYear.getId(), academicLevel.getId());
		}
		
		studentAcademicLevel.setAcademicLevel(academicLevel);
		studentAcademicLevel.setCompletionStatusId(completionStatus.getID());
		studentAcademicLevel.setAcademicYear(academicYear);
		studentAcademicLevel.setStudent(student);
		studentAcademicLevel.setUpdatedBy(authUser.getUserId());
		studentAcademicLevel.setUpdatedOn(CalendarUtil.getTodaysDate());
		
		if (studentAcademicLevelField.getIsCurrentStudentAcademicLevel()) {
			studentAcademicLevel.setIsCurrentAcademicLevel(studentAcademicLevelField.getIsCurrentStudentAcademicLevel());
			studentAcademicLevelDao.updateAllCurrentAcademicLevel(student.getId());
		}		
		studentAcademicLevel = updateObject(studentAcademicLevel);
		
		
		
		return studentAcademicLevel;	
	}
}
