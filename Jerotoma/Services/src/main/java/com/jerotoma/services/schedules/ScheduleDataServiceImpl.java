package com.jerotoma.services.schedules;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.jerotoma.common.constants.ScheduleConstant;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.models.academic.AcademicYear;
import com.jerotoma.common.models.academic.Class;
import com.jerotoma.common.models.academic.Course;
import com.jerotoma.common.models.academic.Room;
import com.jerotoma.common.models.users.Teacher;
import com.jerotoma.common.models.users.User;
import com.jerotoma.common.schedules.GeneticAlgorithm;
import com.jerotoma.common.schedules.MeetingTime;
import com.jerotoma.common.schedules.Population;
import com.jerotoma.common.schedules.Schedule;
import com.jerotoma.common.schedules.ScheduledClass;
import com.jerotoma.common.schedules.ScheduledData;
import com.jerotoma.common.utils.CalendarUtil;
import com.jerotoma.common.viewobjects.AcademicLevelVO;
import com.jerotoma.common.viewobjects.AcademicYearVO;
import com.jerotoma.common.viewobjects.ClassVO;
import com.jerotoma.common.viewobjects.CourseVO;
import com.jerotoma.common.viewobjects.DepartmentVO;
import com.jerotoma.common.viewobjects.MeetingTimeVO;
import com.jerotoma.common.viewobjects.RoomVO;
import com.jerotoma.common.viewobjects.TeacherVO;
import com.jerotoma.common.viewobjects.WorkDayVO;
import com.jerotoma.services.assemblers.AssemblerTeacherService;
import com.jerotoma.services.assemblers.academic.AssemblerAcademicYearService;
import com.jerotoma.services.assemblers.academic.AssemblerClassService;
import com.jerotoma.services.assemblers.academic.AssemblerCourseService;
import com.jerotoma.services.assemblers.academic.AssemblerDepartmentService;
import com.jerotoma.services.assemblers.academic.AssemblerMeetingTimeService;
import com.jerotoma.services.assemblers.academic.AssemblerRoomService;
import com.jerotoma.services.assemblers.academic.AssemblerWorkDayService;
import com.jerotoma.services.courses.ClassService;

@Service
public class ScheduleDataServiceImpl implements ScheduleDataService {
	
	@Autowired AssemblerAcademicYearService assemblerAcademicYearService;
	@Autowired AssemblerDepartmentService assemblerDepartmentService;
	@Autowired AssemblerRoomService assemblerRoomService;
	@Autowired AssemblerCourseService assemblerCourseService;
	@Autowired AssemblerTeacherService assemblerTeacherService;
	@Autowired AssemblerMeetingTimeService assemblerMeetingTimeService;
	@Autowired AssemblerWorkDayService assemblerWorkDayService;
	@Autowired AssemblerClassService assemblerClassService;
	@Autowired ClassService classService;
	
	int generationNumber = 0;
	List<Class> mClasses = new ArrayList<>();

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
			return assemblerCourseService.findCoursesByAcademicLevelId(academicYearId);
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

	@Override
	public List<WorkDayVO> findAllWorkDays() {
		try {
			return assemblerWorkDayService.findAllWorkDays();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public AcademicYearVO getCurrentAcademicYear() {
		return assemblerAcademicYearService.getCurrentAcademicYear();
	}
	
	@Override
	public List<Class> generateClasses(AcademicYearVO academicYear, AcademicLevelVO academicLevel, User authUser) {
		
		List<RoomVO> rooms = findRooms();
		List<TeacherVO> teachers  = findTeachers();
		List<CourseVO> courses = findCoursesByAcademicYear(academicYear.getId());
		List<DepartmentVO> departments  = findDepartments();
		List<MeetingTimeVO> meetingTimes = findMeetingTimes();		
		ScheduledData data = new ScheduledData(rooms, teachers, courses, departments, meetingTimes, academicYear, academicLevel);
		GeneticAlgorithm geneticAlgorithm =  new GeneticAlgorithm(data);
		Population population = new Population(ScheduleConstant.POPULATION_SIZE, data).sortByFitness();		
		population = geneticAlgorithm.evolve(population).sortByFitness();
		while (population.getSchedules().get(0).getFitness() != 1.0) {
			population = geneticAlgorithm.evolve(population).sortByFitness();
			population.getSchedules().forEach(schedule -> {
				System.out.println("Fitness :  " + schedule.getFitness());
				generationNumber++;
				if (schedule.getFitness() == 1) {
					mClasses = processGeneratedClasses(schedule, data, authUser.getId());					
					System.out.print("\n\n> Solution found in " + (generationNumber + 1) + " generations");
				}
			});			
		}
		return mClasses;
	}
	
	@Transactional
	private List<Class> processGeneratedClasses(Schedule schedule, ScheduledData scheduleData, Integer userId) {		
		List<ScheduledClass> scheduledClasses = new ArrayList<>();
		List<Class> mClasses = new ArrayList<>();
		if (schedule.getFitness() == 1) {
			List<ClassVO> classes = schedule.getClasses();			
			classes.forEach( x -> {
				int majorIndex = scheduleData.getDepartments().indexOf(x.getDepartment());
				int courseIndex = -1;
				for (CourseVO course:  scheduleData.getCourses()) {
					if (course.getId().equals(x.getCourse().getId())) {
						courseIndex = scheduleData.getCourses().indexOf(course);
						break;
					}
				}
				if (courseIndex != -1) {
					int roomIndex = scheduleData.getRooms().indexOf(x.getRoom());
					int teacherIndex = scheduleData.getTeachers().indexOf(x.getTeacher());
					int meetingTimeIndex = scheduleData.getMeetingTimes().indexOf(x.getMeetingTime());
					DepartmentVO department = scheduleData.getDepartments().get(majorIndex);
					TeacherVO teacher = scheduleData.getTeachers().get(teacherIndex);
					MeetingTimeVO meetingTime = scheduleData.getMeetingTimes().get(meetingTimeIndex);
					CourseVO course = scheduleData.getCourses().get(courseIndex);
					course.setDepartment(department);
					RoomVO room = scheduleData.getRooms().get(roomIndex);
					AcademicYearVO academicLevel = scheduleData.getAcademicYear();
					ScheduledClass scheduledClass = new ScheduledClass(department, teacher, course, room, academicLevel, meetingTime);
					scheduledClasses.add(scheduledClass);
					
				}		
			});
			
			if (!scheduledClasses.isEmpty()) {
				for(ScheduledClass scheduledClass : scheduledClasses) {
					Class mClass = new Class();
					mClass.setCapacity(scheduledClass.getRoom().getCapacity());
					mClass.setRoom(new Room(scheduledClass.getRoom()));
					mClass.setTeacher(new Teacher(scheduledClass.getTeacher()));
					mClass.setCourse(new Course(scheduledClass.getCourse()));
					mClass.setMeetingTime(new MeetingTime(scheduledClass.getMeetingTime()));
					mClass.setAcademicYear(new AcademicYear(scheduledClass.getAcademicYear()));
					mClass.setUpdatedBy(userId);
					mClass.setUpdatedOn(CalendarUtil.getTodaysDate());
					mClass.setCreatedOn(CalendarUtil.getTodaysDate());
					
					System.out.println("Teacher : " + mClass.getTeacher().getFullName() + "( " + mClass.getTeacher().getId() + " ), Course: " + mClass.getCourse().getName() + "( " + mClass.getCourse().getId() + " )");
					ClassVO classVO = null;
					try {
						classVO = assemblerClassService.findClassByUniqueParams(mClass.getTeacher().getId(), mClass.getCourse().getId(), mClass.getAcademicYear().getId());
					} catch (SQLException | EmptyResultDataAccessException e) {						
						if (e instanceof EmptyResultDataAccessException) {
							classVO = null;
						} else {
							throw new JDataAccessException(e.getMessage(), e);
						}		
					}
					try {
						if (classVO == null) {
							mClass = classService.createObject(mClass);	
							mClasses.add(mClass);
						}						
					} catch (SQLException e) {
						throw new JDataAccessException(e.getMessage(), e);			
					}
				}
			}		
		}
		return mClasses;
	}

}
