package com.jerotoma.api.controllers.secured;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jerotoma.api.controllers.BaseController;
import com.jerotoma.common.constants.CompletionStatus;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.constants.StudentConstant;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.academic.AcademicLevel;
import com.jerotoma.common.models.academic.StudentAcademicLevel;
import com.jerotoma.common.models.students.Student;
import com.jerotoma.common.utils.validators.StudentAcadmicLevelValidator;
import com.jerotoma.common.viewobjects.StudentVO;
import com.jerotoma.services.assemblers.AssemblerStudentService;
import com.jerotoma.services.assemblers.academic.AssemblerStudentClassService;
import com.jerotoma.services.courses.AcademicLevelService;
import com.jerotoma.services.courses.AcademicYearService;
import com.jerotoma.services.courses.ClassService;
import com.jerotoma.services.courses.CourseService;
import com.jerotoma.services.courses.StudentClassService;
import com.jerotoma.services.students.StudentAcademicLevelService;
import com.jerotoma.services.users.StudentService;

@RestController
@RequestMapping(EndPointConstants.REST_STUDENT_ACADEMIC_LEVEL_CONTROLLER.BASE)
public class RestStudentAcademicLevelController  extends BaseController {
	
	@Autowired StudentClassService studentClassService;
	@Autowired AssemblerStudentClassService assemblerStudentClassService;
	@Autowired AcademicYearService academicYearService;
	@Autowired AcademicLevelService academicLevelService;
	@Autowired CourseService courseService;
	@Autowired ClassService jClassService;
	@Autowired AssemblerStudentService assemblerStudentService;
	@Autowired StudentService studentService;
	@Autowired StudentAcademicLevelService studentAcademicLevelService;
	
	
	@PostMapping(value = {"", "/"})
	@ResponseBody
	public HttpResponseEntity<Object> createStudentClass(
			Authentication auth, 
			@RequestBody Map<String, Object> params) throws JDataAccessException {
		
		List<String> requiredFields;
		this.logRequestDetail("POST : "+ EndPointConstants.REST_STUDENT_CLASS_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		userSecurityClearance.checkStudentCreationPermission();
		
		requiredFields = new ArrayList<>(
				Arrays.asList(
						StudentConstant.Class.ACADEMIC_YEAR_ID,
						StudentConstant.Class.STUDENT_ACADEMIC_LEVEL_ID,
						StudentConstant.Class.STUDENT_IDS,
						StudentConstant.Class.JCLASS_IDS
						));
				
		StudentAcademicLevel.Fields studentAcademicLevelFields = StudentAcadmicLevelValidator.validate(params, requiredFields);
		
		try {
			
			Student student = studentService.findObject(studentAcademicLevelFields.getStudentId());
			AcademicLevel academicLevel  = academicLevelService.findObject(studentAcademicLevelFields.getAcademicLevelId());
			CompletionStatus completionStatus = CompletionStatus.getCompletionStatusfromID(studentAcademicLevelFields.getAcademicLevelId());
			StudentAcademicLevel studentAcademicLevel = new StudentAcademicLevel(student, academicLevel, completionStatus);
			studentAcademicLevelService.createObject(studentAcademicLevel);
		
		} catch (SQLException e) {
		
			e.printStackTrace();
		};
		
		
		
		return response;	
	}
	
	
	@GetMapping(value = "/unenrolled/program/{programId}/academic-level/{academicLevelId}")
	@ResponseBody
	public HttpResponseEntity<Object> loadStudentsWhoAreUnenrolledAndQualifiedForThisProgramAndAcademicLevel(
			Authentication auth, 
			@PathVariable("programId") Integer programId,
			@PathVariable("academicLevelId") Integer academicLevelId) {
		
		try {
			
			
			List<StudentVO> students = assemblerStudentService.findStudentsWhoAreUnenrolledAndQualifiedForThisProgramAndAcademicLevel(programId, academicLevelId);
		
		} catch (SQLException e) {
		
			e.printStackTrace();
		};
		
		return response;	
	}

}
