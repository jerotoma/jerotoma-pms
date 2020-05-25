package com.jerotoma.services.schedules;

import java.util.List;

import com.jerotoma.common.models.academic.Class;
import com.jerotoma.common.models.users.AuthUser;
import com.jerotoma.common.viewobjects.AcademicYearVO;
import com.jerotoma.common.viewobjects.CourseVO;
import com.jerotoma.common.viewobjects.DepartmentVO;
import com.jerotoma.common.viewobjects.MeetingTimeVO;
import com.jerotoma.common.viewobjects.RoomVO;
import com.jerotoma.common.viewobjects.TeacherVO;
import com.jerotoma.common.viewobjects.WorkDayVO;

public interface ScheduleDataService {
	public List<RoomVO> findRooms();
	public List<TeacherVO> findTeachers();
	public List<CourseVO> findCoursesByAcademicYear(Integer academicYearId);
	public List<DepartmentVO> findDepartments();
	public List<MeetingTimeVO> findMeetingTimes();
	public List<WorkDayVO> findAllWorkDays();
	public List<CourseVO> findCourses();
	public AcademicYearVO getAcademicYear(Integer academicYearId);
	public AcademicYearVO getCurrentAcademicYear();
	public List<Class> generateClasses(AcademicYearVO academicYear, AuthUser authUser);	
}
