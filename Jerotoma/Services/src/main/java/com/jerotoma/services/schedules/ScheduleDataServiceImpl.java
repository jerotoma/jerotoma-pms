package com.jerotoma.services.schedules;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.viewobjects.AcademicYearVO;
import com.jerotoma.common.viewobjects.CourseVO;
import com.jerotoma.common.viewobjects.DepartmentVO;
import com.jerotoma.common.viewobjects.MeetingTimeVO;
import com.jerotoma.common.viewobjects.RoomVO;
import com.jerotoma.common.viewobjects.TeacherVO;
import com.jerotoma.services.assemblers.AssemblerTeacherService;
import com.jerotoma.services.assemblers.academic.AssemblerAcademicYearService;
import com.jerotoma.services.assemblers.academic.AssemblerCourseService;
import com.jerotoma.services.assemblers.academic.AssemblerDepartmentService;
import com.jerotoma.services.assemblers.academic.AssemblerMeetingTimeService;
import com.jerotoma.services.assemblers.academic.AssemblerRoomService;

@Service
public class ScheduleDataServiceImpl implements ScheduleDataService {
	
	@Autowired AssemblerAcademicYearService assemblerAcademicYearService;
	@Autowired AssemblerDepartmentService assemblerDepartmentService;
	@Autowired AssemblerRoomService assemblerRoomService;
	@Autowired AssemblerCourseService assemblerCourseService;
	@Autowired AssemblerTeacherService assemblerTeacherService;
	@Autowired AssemblerMeetingTimeService assemblerMeetingTimeService;

	@Override
	public List<RoomVO> findRooms() {		
		try {
			return assemblerRoomService.findList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<TeacherVO> findTeachers() {
		try {
			return assemblerTeacherService.findAllTeachers();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<CourseVO> findCoursesByAcademicYear(Integer academicYearId) {
		try {
			return assemblerCourseService.findCoursesByAcademicYearId(academicYearId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public List<DepartmentVO> findDepartments() {
		try {
			return assemblerDepartmentService.getAllDepartment();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<MeetingTimeVO> findMeetingTimes() {
		try {
			return assemblerMeetingTimeService.findAllMeetingTimes();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<CourseVO> findCourses() {
		try {
			return assemblerCourseService.findAllCourses();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public AcademicYearVO getAcademicYear(Integer academicYearId) {
		try {
			return assemblerAcademicYearService.findObject(academicYearId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
