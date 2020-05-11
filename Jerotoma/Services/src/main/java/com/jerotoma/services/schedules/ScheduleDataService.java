package com.jerotoma.services.schedules;

import java.util.List;

import com.jerotoma.common.viewobjects.CourseVO;
import com.jerotoma.common.viewobjects.DepartmentVO;
import com.jerotoma.common.viewobjects.MeetingTimeVO;
import com.jerotoma.common.viewobjects.RoomVO;
import com.jerotoma.common.viewobjects.TeacherVO;

public interface ScheduleDataService {
	public List<RoomVO> findRooms();
	public List<TeacherVO> findTeachers();
	public List<CourseVO> findCoursesByAcademicYear(Integer academicYearId);
	public List<DepartmentVO> findDepartments();
	public List<MeetingTimeVO> findMeetingTimes();	
}
