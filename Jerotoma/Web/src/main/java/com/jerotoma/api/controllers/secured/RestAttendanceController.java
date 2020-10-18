package com.jerotoma.api.controllers.secured;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jerotoma.api.controllers.BaseController;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.services.academic.AcademicYearService;
import com.jerotoma.services.academic.ClassService;
import com.jerotoma.services.assemblers.attendances.AssemblerClassAttendanceService;
import com.jerotoma.services.attendances.ClassAttendanceService;

@RestController
@RequestMapping(EndPointConstants.REST_ATTENDANCE_CONTROLLER.BASE)
public class RestAttendanceController extends BaseController {
	
	@Autowired ClassService classService;
	@Autowired AcademicYearService academicYearService;
	@Autowired ClassAttendanceService classAttendanceService;
	@Autowired AssemblerClassAttendanceService assemblerClassAttendanceService;

	
}
