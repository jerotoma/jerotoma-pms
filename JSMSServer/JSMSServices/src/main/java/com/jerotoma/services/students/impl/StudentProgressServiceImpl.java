package com.jerotoma.services.students.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.constants.CompletionStatus;
import com.jerotoma.common.viewobjects.AcademicLevelProgress;
import com.jerotoma.common.viewobjects.AcademicLevelVO;
import com.jerotoma.common.viewobjects.AcademicYearVO;
import com.jerotoma.common.viewobjects.ClassProgressVO;
import com.jerotoma.common.viewobjects.StudentAcademicLevelVO;
import com.jerotoma.common.viewobjects.StudentAcademicLevelsProgress;
import com.jerotoma.common.viewobjects.StudentClassesProgressVO;
import com.jerotoma.common.viewobjects.StudentClassVO;
import com.jerotoma.common.viewobjects.StudentVO;
import com.jerotoma.database.assemblers.dao.academic.AssemblerStudentAcademicLevelDao;
import com.jerotoma.services.assemblers.AssemblerStudentService;
import com.jerotoma.services.assemblers.academic.AssemblerAcademicLevelService;
import com.jerotoma.services.assemblers.academic.AssemblerStudentClassService;
import com.jerotoma.services.students.StudentProgressService;

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
				
				List<StudentClassVO> studentClasses = assemblerStudentClassService.findStudentClasses(studentId, academicLevel.getId(), academicYear.getId());
				classesProgress.add(new ClassProgressVO(academicYear, academicLevel, studentClasses));
			}
			studentClassProgress = new StudentClassesProgressVO(student.getId(), student.getFullName(), classesProgress);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return studentClassProgress;
	}
	
	@Override
	public StudentAcademicLevelsProgress findStudentAcademicLevelsProgressByStudentId(Integer studentId) throws SQLException {
		StudentAcademicLevelsProgress studentProgress = new StudentAcademicLevelsProgress();
		List<AcademicLevelProgress> academicLevelProgresses = new ArrayList<>();
		
		StudentVO student = assemblerStudentService.findObject(studentId);
		studentProgress.setStudentID(student.getId());
		studentProgress.setStudentName(student.getFullName());
		List<AcademicLevelVO> academicLevels = assemblerAcademicLevelService.loadAcademicLevelByProgram(student.getProgramId());
		studentProgress.setRequiredLevels(academicLevels.size());
		List<StudentAcademicLevelVO>  studentAcademicLevels = assemblerStudentAcademicLevelDao.findStudentAcademicLevelsByStudentId(studentId);
		
		int countCompleted = 0;
		int countNotCompleted = 0;
		
		for (AcademicLevelVO academicLevel : academicLevels) {
			AcademicLevelProgress academicLevelProgress = new AcademicLevelProgress();
			boolean hasStatus = false;
			for (StudentAcademicLevelVO studentAcademicLevel : studentAcademicLevels) {
				if (academicLevel.getId().equals(studentAcademicLevel.getAcademicLevel().getId())) {
					academicLevelProgress.setAcademicLevel(academicLevel);
					academicLevelProgress.setCompletionStatus(studentAcademicLevel.getCompletionStatus());
					academicLevelProgress.setCompletionStatusName(studentAcademicLevel.getCompletionStatusName());
					academicLevelProgress.setAcademicYear(studentAcademicLevel.getAcademicYear());
					hasStatus = true;
					
					if (CompletionStatus.COMPLETED.equals(academicLevelProgress.getCompletionStatus())) {
						countCompleted++;
					} else {
						countNotCompleted++;
					}
				}
			}			
			if (!hasStatus) {
				academicLevelProgress.setAcademicLevel(academicLevel);
				academicLevelProgress.setCompletionStatus(CompletionStatus.NOT_STARTED);
				academicLevelProgress.setCompletionStatusName(CompletionStatus.NOT_STARTED.getDescription());
				countNotCompleted++;
			}			
			academicLevelProgresses.add(academicLevelProgress);
		}
		studentProgress.setCompletedLevels(countCompleted);
		studentProgress.setUnCompletedLevels(countNotCompleted);
		studentProgress.setAcademicLevelProgresses(academicLevelProgresses);
		
		return studentProgress;		
	}

}
