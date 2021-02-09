package com.jerotoma.services.users.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.CompletionStatus;
import com.jerotoma.common.exceptions.FieldRequiredException;
import com.jerotoma.common.models.academic.AcademicLevel;
import com.jerotoma.common.models.academic.Department;
import com.jerotoma.common.models.addresses.Address;
import com.jerotoma.common.models.addresses.ParentAddress;
import com.jerotoma.common.models.addresses.StaffAddress;
import com.jerotoma.common.models.addresses.StudentAddress;
import com.jerotoma.common.models.addresses.TeacherAddress;
import com.jerotoma.common.models.positions.Position;
import com.jerotoma.common.models.users.Parent;
import com.jerotoma.common.models.users.Person;
import com.jerotoma.common.models.users.Staff;
import com.jerotoma.common.models.users.Teacher;
import com.jerotoma.common.models.users.User;
import com.jerotoma.common.models.users.UserContext;
import com.jerotoma.common.models.users.students.Student;
import com.jerotoma.common.models.users.students.StudentAcademicLevel;
import com.jerotoma.common.models.users.students.StudentAcademicLevel.Fields;
import com.jerotoma.common.utils.CalendarUtil;
import com.jerotoma.common.viewobjects.AcademicYearVO;
import com.jerotoma.common.viewobjects.PersonVO;
import com.jerotoma.common.viewobjects.UserVO;
import com.jerotoma.services.AddressService;
import com.jerotoma.services.academic.AcademicLevelService;
import com.jerotoma.services.academic.AcademicYearService;
import com.jerotoma.services.academic.DepartmentService;
import com.jerotoma.services.academic.ProgramService;
import com.jerotoma.services.academicdisciplines.AcademicDisciplineService;
import com.jerotoma.services.assemblers.AssemblerParentService;
import com.jerotoma.services.assemblers.AssemblerSequenceGeneratorService;
import com.jerotoma.services.assemblers.AssemblerStaffService;
import com.jerotoma.services.assemblers.AssemblerStudentService;
import com.jerotoma.services.assemblers.AssemblerTeacherService;
import com.jerotoma.services.assemblers.academic.AssemblerAcademicLevelService;
import com.jerotoma.services.assemblers.academic.AssemblerAcademicYearService;
import com.jerotoma.services.assemblers.academic.AssemblerProgramService;
import com.jerotoma.services.positions.PositionService;
import com.jerotoma.services.securities.EnrollementPrerequisiteClearance;
import com.jerotoma.services.students.StudentAcademicLevelService;
import com.jerotoma.services.users.AuthUserService;
import com.jerotoma.services.users.ParentAddressService;
import com.jerotoma.services.users.ParentService;
import com.jerotoma.services.users.StaffAddressService;
import com.jerotoma.services.users.StaffService;
import com.jerotoma.services.users.StudentAddressService;
import com.jerotoma.services.users.StudentService;
import com.jerotoma.services.users.TeacherAddressService;
import com.jerotoma.services.users.TeacherService;
import com.jerotoma.services.users.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired AuthUserService authUserService;
	@Autowired AssemblerTeacherService assemblerTeacherService;
	@Autowired AssemblerAcademicYearService assemblerAcademicYearService;
	@Autowired AcademicYearService academicYearService;
	@Autowired AssemblerStudentService assemblerStudentService;
	@Autowired AssemblerStaffService assemblerStaffService;
	@Autowired AssemblerParentService assemblerParentService;
	@Autowired AssemblerSequenceGeneratorService sequenceGeneratorService;
	@Autowired TeacherService teacherService;	
	@Autowired StudentService studentService;
	@Autowired StaffService staffService;
	@Autowired ParentService parentService;
	@Autowired TeacherAddressService teacherAddressService;
	@Autowired AddressService addressService;
	@Autowired StudentAddressService studentAddressService;
	@Autowired StaffAddressService staffAddressService;
	@Autowired ParentAddressService parentAddressService;
	@Autowired PositionService positionService;
	@Autowired AcademicDisciplineService academicDisciplineService;
	@Autowired DepartmentService departmentService;
	@Autowired ProgramService programService;	
	@Autowired AssemblerAcademicLevelService assemblerAcademicLevelService;
	@Autowired AcademicLevelService academicLevelService;
	@Autowired StudentAcademicLevelService studentAcademicLevelService;
	@Autowired AssemblerProgramService assemblerProgramService;
	@Autowired EnrollementPrerequisiteClearance prerequisiteClearance;
	
	
	Date today = CalendarUtil.getTodaysDate();

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return authUserService.loadUserByUsername(username);
	}

	@Transactional
	@Override
	public UserVO loadCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserContext userContext = getUserContext(authentication);
		User authUser = (User) loadUserByUsername(userContext.getUsername());		
		return getUserDetails(authUser);
	}

	@Override
	public UserContext getUserContext(Authentication authentication) {

		if (authentication == null) {
			throw new IllegalStateException();
		}
		Object principal = authentication.getPrincipal();

		if (authentication.getPrincipal() instanceof UserContext) {
			return (UserContext) principal;
		} else if (principal instanceof User) {
			User user = (User) principal;
			return new UserContext(user.getUsername(), user.getAuthorities());
		} else {
			throw new BadCredentialsException("Invalid credential was provided");
		}
	}

	@Override
	public UserVO getUserByUsername(String username) {		
		User authUser = (User)loadUserByUsername(username);		
		return getUserDetails(authUser);
	}

	private UserVO getUserDetails(User user) {
		PersonVO person = null;
		
		if (user == null) {
			return null;
		}
		
		try {
			switch (user.getUserType()) {
			case TEACHER:
				person = assemblerTeacherService.findObjectUniqueKey(user.getUsername());
				break;
			case STUDENT:
				person = assemblerStudentService.findObjectUniqueKey(user.getUsername());
				break;
			case STAFF:
				person = assemblerStaffService.findObjectUniqueKey(user.getUsername());
				break;
			case PARENT:
				person = assemblerParentService.findObjectUniqueKey(user.getUsername());
				break;
			default:
				throw new UsernameNotFoundException("User type not found");
			}
		} catch (SQLException e) {
			person = null;
			e.printStackTrace();
		}
		
		if (person == null) {
			return null;
		}		
		return new UserVO(user, person);
	}

	@Override
	public UserVO getUserVOByUserId(Integer userId) {
		User user = getUserById(userId);
		return getUserDetails(user);
	}
	
	
	private User getUserById(Integer userId) {
		try {
			return authUserService.findObject(userId);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<UserVO> searchUser(QueryParam param) {	
		List<UserVO> userVOs = new ArrayList<>();
		try {
			List<User>  users = authUserService.search(param);
			if (users != null) {
				return users.stream().map(user -> getUserDetails(user)).collect(Collectors.toList());
			}			
			return userVOs;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return new ArrayList<UserVO>();		
	}

	@Transactional
	@Override
	public Person createUser(Person person) throws SQLException {
		
		Parent parent = null;
		
		switch (person.getUser().getUserType()) {
		case STUDENT:
			Student student = (Student)person;			
			student = createStudent(student.getUser(), student);			
			return student;
		case TEACHER:
			Teacher teacher = (Teacher)person;
			teacher = createTeacher(person.getUser(), teacher);				
			return teacher;
		case PARENT:
			parent = (Parent)person;			
			parent = createParent(parent.getUser(), parent);				
			return parent;
		case STAFF:
			Staff staff = (Staff)person;
			staff = createStaff(person.getUser(), staff);				
			return staff;
		default:
			break;
		}		
		return null;
	}
	
	@Transactional
	private Staff createStaff(User newUser, Staff staff) throws SQLException {
		
		newUser = authUserService.createUserLoginAccount(newUser);
		staff.setUserId(newUser.getId());
		staff = staffService.createObject(staff);
		UserVO loggedInUser = loadCurrentUser();
		
		Address address = staff.getAddress();
		address.setUpdatedBy(loggedInUser.getId());
		address = addressService.createObject(address);
		
		StaffAddress staffAddress = new StaffAddress();
		staffAddress.setAddress(address);
		staffAddress.setStaff(staff);
		staffAddress.setCreatedOn(today);
		staffAddress.setUpdatedOn(today);
		staffAddressService.createObject(staffAddress);
		return staff;
	}

	@Transactional
	private Parent createParent(User newUser, Parent parent) throws SQLException {
		Student student = null;
		Set<Parent> parents = new HashSet<>();
		Integer studentId = parent.getStudentId();	
				
		if (studentId != null) {
			student = studentService.findObject(studentId);
		}
				
		newUser = authUserService.createUserLoginAccount(newUser);		
		UserVO loggedInUser = loadCurrentUser();
		parent.setUserId(newUser.getId());
		parent.setUpdatedBy(loggedInUser.getId());
		Address address = parent.getAddress();
		parent = parentService.createObject(parent);
		
		if (student != null && student.getParents() != null ) {
			parents.addAll(student.getParents());
			parents.add(parent);
			student.setParents(parents);
			student = studentService.updateObject(student);
		}	
						
		address.setUpdatedBy(loggedInUser.getId());
		address = addressService.createObject(address);
		
		ParentAddress parentAddress = new ParentAddress();
		parentAddress.setAddress(address);
		parentAddress.setParent(parent);
		parentAddress.setCreatedOn(today);
		parentAddress.setUpdatedOn(today);
		parentAddressService.createObject(parentAddress);		
		
		return parent;
	}

	@Transactional
	private Teacher createTeacher(User newUser, Teacher teacher) throws SQLException {
		newUser = authUserService.createUserLoginAccount(newUser);		
		teacher.setUserId(newUser.getId());
		Address address = teacher.getAddress();	
		teacher = teacherService.createObject(teacher);
		
		UserVO loggedInUser = loadCurrentUser();		
		address.setUpdatedBy(loggedInUser.getId());
		address = addressService.createObject(address);
		
		TeacherAddress teacherAddress = new TeacherAddress();
		teacherAddress.setAddress(address);
		teacherAddress.setTeacher(teacher);
		teacherAddress.setCreatedOn(today);
		teacherAddress.setUpdatedOn(today);
		teacherAddressService.createObject(teacherAddress);
		return teacher;
	}

	@Transactional
	protected Student createStudent(User newUser, Student student) throws SQLException {
		
		CompletionStatus completionStatus = CompletionStatus.IN_PROGRESS;
		final Integer commpletionStatusId = completionStatus.getID();
		final Integer academicLevelId = student.getAcademicLevelId(); 
		final Integer streamId = student.getStreamId();
		final Set<Parent> parents = new HashSet<>();
		
		AcademicYearVO academicYearVO = assemblerAcademicYearService.getCurrentAcademicYear();		
		if (academicYearVO == null) {
			throw new FieldRequiredException("Current Academic Year is required to continue.");
		}
		
		if (!assemblerProgramService.doesProgramAcademicLevelExist(student.getProgramId(), student.getAcademicLevelId())) {
			throw new FieldRequiredException("Program or Academic Level is required to continue.");
		}
		
		final Integer academicYearId = academicYearVO.getId();		
		student.setProgram(programService.findObject(student.getProgramId()));
		AcademicLevel academicLevel = academicLevelService.findObject(student.getAcademicLevelId());
		
		Parent primaryParent = student.getPrimaryParent();
		primaryParent = createParent(primaryParent.getUser(), primaryParent);
		student.setPrimaryParent(primaryParent);
		parents.add(primaryParent);
				
		if (student.getParentIds() != null) {			
			for (Integer parentId: student.getParentIds()) {
				final Parent parent = parentService.findObject(parentId);
				parents.add(parent);
			}		
		}
		UserVO user = loadCurrentUser();
		newUser = authUserService.createUserLoginAccount(newUser);
		
		student.setParents(parents);
		student.setUserId(newUser.getId());	
		student.setUpdatedBy(user.getId());		
		student.setStudentNumber(sequenceGeneratorService.getNextNumber().intValue());	
		student.setAcademicLevelId(academicLevel.getId());
		Address address = student.getAddress();
		
		student = studentService.createObject(student);	
								
		address.setUpdatedBy(user.getId());
		address = addressService.createObject(address);
		StudentAddress studentAddress = new StudentAddress();
		studentAddress.setStudent(student);
		studentAddress.setAddress(address);
		studentAddress.setCreatedOn(today);
		studentAddress.setUpdatedOn(today);
		studentAddressService.createObject(studentAddress);	
		
		Fields studentAcademicLevelField = new Fields(null, student.getId(), commpletionStatusId, academicLevelId, streamId, academicYearId, null, true);
		studentAcademicLevelService.createStudentAcademicLevel(studentAcademicLevelField, user);
		return student;
	}
	
	@Transactional
	public Student updateStudent(Student student) throws SQLException {
		
		CompletionStatus completionStatus = CompletionStatus.IN_PROGRESS;
		final Integer commpletionStatusId = completionStatus.getID();
		final Integer academicLevelId = student.getAcademicLevelId(); 
		final Integer streamId = student.getStreamId();
		final Set<Parent> parents = new HashSet<>();
		Parent parent;
		Address address;
		
		AcademicYearVO academicYearVO = assemblerAcademicYearService.getCurrentAcademicYear();		
		if (academicYearVO == null) {
			throw new FieldRequiredException("Current Academic Year is required to continue.");
		}
		
		if (student.getId() == null) {
			throw new FieldRequiredException("Student ID is required");
		}
		
		if (!assemblerProgramService.doesProgramAcademicLevelExist(student.getProgramId(), academicLevelId)) {
			throw new FieldRequiredException("Program or Academic Level can not be empty or null.");
		}
		
		Fields studentAcademicLevelField = new Fields(null, student.getId(), commpletionStatusId, academicLevelId, streamId, academicYearVO.getId(), null, true);
		UserVO user = loadCurrentUser();
		
		Student mStudent = studentService.findObject(student.getId());	
		mStudent.setFirstName(student.getFirstName());
		mStudent.setLastName(student.getLastName());
		mStudent.setMiddleNames(student.getMiddleNames());
		mStudent.setFullName(student.getFullName());
		mStudent.setAge(student.getAge());
		mStudent.setBirthDate(student.getBirthDate());
		mStudent.setGender(student.getGender());
		mStudent.setOccupation(student.getOccupation());
		mStudent.setPhoneNumber(student.getPhoneNumber());
		mStudent.setParentIds(student.getParentIds());
		
		if (student.getPrimaryParent() != null) {
			parent = updateParent(student.getPrimaryParent());
			mStudent.setPrimaryParent(parent);
			parents.add(parent);
		}
		
		mStudent.setUpdatedOn(new Date());
		mStudent.setUpdatedBy(user.getId());
		mStudent.setAddress(student.getAddress());
		mStudent.setProgram(programService.findObject(student.getProgramId()));	
		
		AcademicLevel academicLevel = academicLevelService.findObject(student.getAcademicLevelId());
		mStudent.setAcademicLevelId(academicLevel.getId());
		
		address = mStudent.getAddress();
		
		Set<StudentAcademicLevel>  studentAcademicLevels = mStudent.getStudentAcademicLevels();
		student.setStudentAcademicLevels(studentAcademicLevels);
		
		if (mStudent.getParentIds() != null) {			
			for (Integer parentId: mStudent.getParentIds()) {
				parent = parentService.findObject(parentId);
				parents.add(parent);
			}										
			mStudent.setParents(parents);
		}
		
		student = studentService.updateObject(mStudent);
		studentAcademicLevelService.updateStudentAcademicLevel(studentAcademicLevelField, user);
		
		address.setUpdatedBy(user.getId());
		address = addressService.updateObject(address);
		return student;
	}
	
	@Transactional
	public Parent updateParent(Parent parent) throws SQLException {
		Student student;
		Address address;
		
		if (parent.getId() == null) {
			throw new FieldRequiredException("Parent ID is required");
		}
		
		UserVO loggedInUser = loadCurrentUser();
		Parent mParent = parentService.findObject(parent.getId());	
		mParent.setFirstName(parent.getFirstName());
		mParent.setLastName(parent.getLastName());
		mParent.setMiddleNames(parent.getMiddleNames());
		mParent.setFullName(parent.getFullName());
		mParent.setAge(parent.getAge());
		mParent.setBirthDate(parent.getBirthDate());
		mParent.setGender(parent.getGender());
		mParent.setOccupation(parent.getOccupation());
		mParent.setPhoneNumber(parent.getPhoneNumber());	
		mParent.setStudentIds(parent.getStudentIds());
		mParent.setUpdatedOn(new Date());
		mParent.setUpdatedBy(loggedInUser.getId());
		mParent.setAddress(parent.getAddress());
		address = mParent.getAddress();
						
		if (parent.getStudentIds() != null) {
			Set<Student> students = new HashSet<>();
			for (Integer studentId: parent.getStudentIds()) {
				student = studentService.findObject(studentId);
				students.add(student);
			}										
			mParent.setStudents(students);
		}				
		mParent = parentService.updateObject(mParent);
		
		address.setUpdatedBy(loggedInUser.getId());
		address = addressService.updateObject(address);
		return mParent;
	}
	
	@Transactional
	public Teacher updateTeacher(Teacher teacher, Department department, Position position) throws SQLException {
		
		Address address;
		Teacher mTeacher = teacherService.findObject(teacher.getId());
		UserVO user = loadCurrentUser();
					
		mTeacher.setFirstName(teacher.getFirstName());
		mTeacher.setLastName(teacher.getLastName());
		mTeacher.setMiddleNames(teacher.getMiddleNames());
		mTeacher.setFullName(teacher.getFullName());
		mTeacher.setPosition(position);
		mTeacher.setDepartment(department);
		mTeacher.setAge(teacher.getAge());
		mTeacher.setBirthDate(teacher.getBirthDate());
		mTeacher.setGender(teacher.getGender());
		mTeacher.setOccupation(teacher.getOccupation());
		mTeacher.setPhoneNumber(teacher.getPhoneNumber());
		mTeacher.setUserCode(teacher.getUserCode());
		mTeacher.setPicture(teacher.getPicture());
		mTeacher.setUpdatedOn(new Date());
		mTeacher.setUpdatedBy(user.getId());
		mTeacher.setAddress(teacher.getAddress());
		
		address = teacher.getAddress();
		teacher = teacherService.updateObject(mTeacher);
		
		address.setUpdatedBy(user.getId());
		address = addressService.updateObject(address);
		return teacher;
	}
	
	@Transactional
	public Staff updateStaff(Staff staff, Position position) throws SQLException {
		
		Address address;
		UserVO user = loadCurrentUser();
		
		Staff mStaff = staffService.findObject(staff.getId());
		mStaff.setFirstName(staff.getFirstName());
		mStaff.setLastName(staff.getLastName());
		mStaff.setMiddleNames(staff.getMiddleNames());
		mStaff.setFullName(staff.getFullName());
		mStaff.setAge(staff.getAge());
		mStaff.setBirthDate(staff.getBirthDate());
		mStaff.setGender(staff.getGender());
		mStaff.setOccupation(staff.getOccupation());
		mStaff.setPhoneNumber(staff.getPhoneNumber());				
		mStaff.setUpdatedOn(new Date());				
		mStaff.setAddress(staff.getAddress());
		address = staff.getAddress();				
		mStaff.setPosition(position);
		mStaff.setUpdatedBy(user.getId());
		
		staff = staffService.updateObject(mStaff);				
		address.setUpdatedBy(user.getId());
		address = addressService.updateObject(address); 
						
		StaffAddress staffAddress = new StaffAddress();
		staffAddress.setAddress(address);
		staffAddress.setStaff(staff);
		staffAddress.setCreatedOn(today);
		staffAddress.setUpdatedOn(today);
		staffAddressService.updateObject(staffAddress);
		return staff;
	}

}
