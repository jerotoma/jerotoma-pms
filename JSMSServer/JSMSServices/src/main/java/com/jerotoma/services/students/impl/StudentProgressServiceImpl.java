package com.jerotoma.services.students.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.constants.CompletionStatus;
import com.jerotoma.common.models.users.CompletedAcademicLevel;
import com.jerotoma.common.models.users.students.StudentAcademicLevel;
import com.jerotoma.common.models.users.students.StudentAcademicLevel.Fields;
import com.jerotoma.common.utils.CalendarUtil;
import com.jerotoma.common.viewobjects.AcademicLevelProgress;
import com.jerotoma.common.viewobjects.AcademicLevelVO;
import com.jerotoma.common.viewobjects.AcademicYearVO;
import com.jerotoma.common.viewobjects.ClassProgressVO;
import com.jerotoma.common.viewobjects.StudentAcademicLevelVO;
import com.jerotoma.common.viewobjects.StudentAcademicLevelsProgress;
import com.jerotoma.common.viewobjects.StudentClassVO;
import com.jerotoma.common.viewobjects.StudentClassesProgressVO;
import com.jerotoma.common.viewobjects.StudentVO;
import com.jerotoma.common.viewobjects.UserVO;
import com.jerotoma.database.assemblers.dao.academic.AssemblerStudentAcademicLevelDao;
import com.jerotoma.services.academic.CompletedAcademicLevelService;
import com.jerotoma.services.assemblers.AssemblerStudentService;
import com.jerotoma.services.assemblers.academic.AssemblerAcademicLevelService;
import com.jerotoma.services.assemblers.academic.AssemblerStudentClassService;
import com.jerotoma.services.students.StudentAcademicLevelService;
import com.jerotoma.services.students.StudentProgressService;
import com.jerotoma.services.users.UserService;

@Service
public class StudentProgressServiceImpl implements StudentProgressService {

	@Autowired
	AssemblerStudentService assemblerStudentService;
	@Autowired
	AssemblerAcademicLevelService assemblerAcademicLevelService;
	@Autowired
	AssemblerStudentAcademicLevelDao assemblerStudentAcademicLevelDao;
	@Autowired
	AssemblerStudentClassService assemblerStudentClassService;

	@Autowired
	StudentAcademicLevelService studentAcademicLevelService;
	
	@Autowired CompletedAcademicLevelService  completedAcademicLevelService;
	
	@Autowired
	UserService userService;

	@Transactional
	@Override
	public StudentClassesProgressVO findStudentClassesProgressByStudentId(Integer studentId) {

		StudentClassesProgressVO studentClassProgress = null;

		try {

			StudentVO student = assemblerStudentService.findObject(studentId);
			List<ClassProgressVO> classesProgress = new ArrayList<>();

			List<StudentAcademicLevelVO> studentAcademicLevels = assemblerStudentAcademicLevelDao
					.findStudentAcademicLevelsByStudentId(studentId);

			for (StudentAcademicLevelVO studentAcademicLevel : studentAcademicLevels) {
				AcademicLevelVO academicLevel = studentAcademicLevel.getAcademicLevel();
				AcademicYearVO academicYear = studentAcademicLevel.getAcademicYear();

				List<StudentClassVO> studentClasses = assemblerStudentClassService.findStudentClasses(studentId,
						academicLevel.getId(), academicYear.getId());
				classesProgress.add(new ClassProgressVO(academicYear, academicLevel, studentClasses));
			}
			studentClassProgress = new StudentClassesProgressVO(student.getId(), student.getFullName(),
					classesProgress);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return studentClassProgress;
	}

	@Transactional
	@Override
	public StudentAcademicLevelsProgress findStudentAcademicLevelsProgressByStudentId(Integer studentId)
			throws SQLException {
		StudentAcademicLevelsProgress studentProgress = new StudentAcademicLevelsProgress();
		List<AcademicLevelProgress> academicLevelProgresses = new ArrayList<>();

		StudentVO student = assemblerStudentService.findObject(studentId);
		studentProgress.setStudentID(student.getId());
		studentProgress.setStudentName(student.getFullName());
		List<AcademicLevelVO> academicLevels = assemblerAcademicLevelService
				.loadAcademicLevelByProgram(student.getProgramId());
		studentProgress.setRequiredLevels(academicLevels.size());
		List<StudentAcademicLevelVO> studentAcademicLevels = assemblerStudentAcademicLevelDao
				.findStudentAcademicLevelsByStudentId(studentId);

		int countCompleted = 0;
		int countNotCompleted = 0;

		for (AcademicLevelVO academicLevel : academicLevels) {
			AcademicLevelProgress academicLevelProgress = null;
			boolean hasStatus = false;
			for (StudentAcademicLevelVO studentAcademicLevel : studentAcademicLevels) {
				academicLevelProgress = new AcademicLevelProgress();
				academicLevelProgress.setAcademicLevel(academicLevel);
				if (academicLevel.getId().equals(studentAcademicLevel.getAcademicLevel().getId())) {
					academicLevelProgress.setCompletionStatus(studentAcademicLevel.getCompletionStatus());
					academicLevelProgress.setCompletionStatusName(studentAcademicLevel.getCompletionStatusName());
					academicLevelProgress.setCompletionStatusId(studentAcademicLevel.getCompletionStatusId());
					academicLevelProgress.setAcademicYear(studentAcademicLevel.getAcademicYear());
					hasStatus = true;

					if (CompletionStatus.COMPLETED.equals(academicLevelProgress.getCompletionStatus())) {
						countCompleted++;
					} else {
						countNotCompleted++;
					}
					academicLevelProgresses.add(academicLevelProgress);
				}
			}

			if (!hasStatus) {
				countNotCompleted++;
				academicLevelProgress = new AcademicLevelProgress();
				academicLevelProgress.setAcademicLevel(academicLevel);
				academicLevelProgress.setCompletionStatus(CompletionStatus.NOT_STARTED);
				academicLevelProgress.setCompletionStatusName(CompletionStatus.NOT_STARTED.getDescription());
				academicLevelProgress.setCompletionStatusId(CompletionStatus.NOT_STARTED.getID());
				academicLevelProgresses.add(academicLevelProgress);
			}
		}
		studentProgress.setCompletedLevels(countCompleted);
		studentProgress.setUnCompletedLevels(countNotCompleted);
		studentProgress.setAcademicLevelProgresses(academicLevelProgresses);

		return studentProgress;
	}

	@Transactional
	@Override
	public StudentAcademicLevelsProgress updateStudentAcademicLevelsProgress(Fields studentAcademicLevelField)
			throws SQLException {
		
		Integer studentId = studentAcademicLevelField.getStudentId();
		Integer academicLevelId = studentAcademicLevelField.getAcademicLevelId();
		Integer academicYearId = studentAcademicLevelField.getAcademicLevelId();
		
		UserVO user = userService.loadCurrentUser();
		
		StudentAcademicLevel studentAcademicLevel = studentAcademicLevelService.findStudentAcademicLevel(studentId, academicLevelId, academicYearId);
		studentAcademicLevelField.setId(studentAcademicLevel.getId());
		studentAcademicLevel = studentAcademicLevelService.updateStudentAcademicLevelClasses(studentAcademicLevel, studentAcademicLevelField, user);
		processCompletedAcademicLevel(studentId, academicLevelId, user, studentAcademicLevel);
		return findStudentAcademicLevelsProgressByStudentId(studentId);
	}

	private void processCompletedAcademicLevel(Integer studentId, Integer academicLevelId, UserVO user, StudentAcademicLevel studentAcademicLevel) throws SQLException {
		
		boolean completedAcademicLevelExists = completedAcademicLevelService.exists(studentId, academicLevelId);		
		
		if (studentAcademicLevel.getCompletionStatus() == CompletionStatus.COMPLETED && !completedAcademicLevelExists) {
			CompletedAcademicLevel completedAcademicLevel = new CompletedAcademicLevel();
			completedAcademicLevel.setAcademicLevel(studentAcademicLevel.getAcademicLevel());
			completedAcademicLevel.setStudent(studentAcademicLevel.getStudent());
			Date today = CalendarUtil.getTodaysDate();		
			completedAcademicLevel.setCreatedOn(today);
			completedAcademicLevel.setUpdatedOn(today);
			completedAcademicLevel.setUpdatedBy(user.getUserId());
			completedAcademicLevelService.createObject(completedAcademicLevel);
		} else if (studentAcademicLevel.getCompletionStatus() != CompletionStatus.COMPLETED && completedAcademicLevelExists) {
			completedAcademicLevelService.deleteObject(completedAcademicLevelService.findCompletedAcademicLevel(studentId, academicLevelId));
		}
	}

}
