package com.jerotoma.services.assemblers.academic.impls;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.CompletionStatus;
import com.jerotoma.common.models.users.students.StudentAcademicLevelProgress;
import com.jerotoma.common.models.users.students.StudentProgress;
import com.jerotoma.common.viewobjects.AcademicLevelVO;
import com.jerotoma.common.viewobjects.ClassVO;
import com.jerotoma.common.viewobjects.StudentAcademicLevelVO;
import com.jerotoma.common.viewobjects.StudentVO;
import com.jerotoma.database.assemblers.dao.academic.AssemblerStudentAcademicLevelDao;
import com.jerotoma.services.assemblers.AssemblerStudentService;
import com.jerotoma.services.assemblers.academic.AssemblerAcademicLevelService;
import com.jerotoma.services.assemblers.academic.AssemblerStudentAcademicLevelService;

@Service
public class AssemblerStudentAcademicLevelServiceImpl implements AssemblerStudentAcademicLevelService {
	
	@Autowired AssemblerStudentAcademicLevelDao assemblerStudentAcademicLevelDao;
	@Autowired AssemblerStudentService assemblerStudentService;
	@Autowired AssemblerAcademicLevelService assemblerAcademicLevelService;

	@Override
	public StudentAcademicLevelVO findObject(Integer primaryKey) throws SQLException {
		return assemblerStudentAcademicLevelDao.findObject(primaryKey);
	}

	@Override
	public StudentAcademicLevelVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		return assemblerStudentAcademicLevelDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public List<StudentAcademicLevelVO> loadList() throws SQLException {
		return assemblerStudentAcademicLevelDao.loadList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return assemblerStudentAcademicLevelDao.loadMapList(queryParam);
	}

	@Override
	public Long countObject() throws SQLException {
		return assemblerStudentAcademicLevelDao.countObject();
	}

	@Override
	public List<StudentAcademicLevelVO> findStudentClassIdByParams(Integer studentId, Integer classId) throws SQLException {
		return assemblerStudentAcademicLevelDao.findStudentClassByParams(studentId, classId);
	}

	@Override
	public List<StudentAcademicLevelVO> findStudentAcademicLevelsByStudentId(Integer studentId) throws SQLException {
		return assemblerStudentAcademicLevelDao.findStudentAcademicLevelsByStudentId(studentId);
	}

	@Override
	public List<ClassVO> findStudentClassesByStudentIdAndAndAcademicLevelID(Integer studentId, Integer academicLevelId) throws SQLException {
		return assemblerStudentAcademicLevelDao.findStudentClassesByStudentIdAndAndAcademicLevelID(studentId, academicLevelId);
	}

	@Override
	public List<ClassVO> findTeacherClassesByTeacherId(Integer teacherID) throws SQLException {
		return assemblerStudentAcademicLevelDao.findTeacherClassesByTeacherId(teacherID);
	}
	
	@Override
	public StudentProgress findStudentProgressByStudentId(Integer studentId) throws SQLException {
		StudentProgress studentProgress = new StudentProgress();
		List<StudentAcademicLevelProgress> studentAcademicLevelProgresses = new ArrayList<>();
		
		StudentVO student = assemblerStudentService.findObject(studentId);
		studentProgress.setStudentID(student.getId());
		studentProgress.setStudentName(student.getFullName());
		List<AcademicLevelVO> academicLevels = assemblerAcademicLevelService.loadAcademicLevelByProgram(student.getProgramId());
		studentProgress.setRequiredLevels(academicLevels.size());
		List<StudentAcademicLevelVO>  studentAcademicLevels = assemblerStudentAcademicLevelDao.findStudentAcademicLevelsByStudentId(studentId);
		
		int countCompleted = 0;
		int countNotCompleted = 0;
		
		for (AcademicLevelVO academicLevel : academicLevels) {
			StudentAcademicLevelProgress studentAcademicLevelProgress = new StudentAcademicLevelProgress();
			boolean hasStatus = false;
			for (StudentAcademicLevelVO studentAcademicLevel : studentAcademicLevels) {
				if (academicLevel.getId().equals(studentAcademicLevel.getAcademicLevel().getId())) {
					studentAcademicLevelProgress.setAcademicLevel(academicLevel);
					studentAcademicLevelProgress.setCompletionStatus(studentAcademicLevel.getCompletionStatus());
					studentAcademicLevelProgress.setCompletionStatusName(studentAcademicLevel.getCompletionStatusName());
					hasStatus = true;
					
					if (CompletionStatus.COMPLETED.equals(studentAcademicLevelProgress.getCompletionStatus())) {
						countCompleted++;
					} else {
						countNotCompleted++;
					}
				}
			}			
			if (!hasStatus) {
				studentAcademicLevelProgress.setAcademicLevel(academicLevel);
				studentAcademicLevelProgress.setCompletionStatus(CompletionStatus.NOT_STARTED);
				studentAcademicLevelProgress.setCompletionStatusName(CompletionStatus.NOT_STARTED.getDescription());
				countNotCompleted++;
			}			
			studentAcademicLevelProgresses.add(studentAcademicLevelProgress);
		}
		studentProgress.setCompletedLevels(countCompleted);
		studentProgress.setUnCompletedLevels(countNotCompleted);
		studentProgress.setStudentAcademicLevelProgresses(studentAcademicLevelProgresses);
		
		return studentProgress;
		
	}

}
