package com.jerotoma.services.users;

import java.sql.SQLException;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.academic.Department;
import com.jerotoma.common.models.positions.Position;
import com.jerotoma.common.models.users.Parent;
import com.jerotoma.common.models.users.Person;
import com.jerotoma.common.models.users.Staff;
import com.jerotoma.common.models.users.Teacher;
import com.jerotoma.common.models.users.UserContext;
import com.jerotoma.common.models.users.students.Student;
import com.jerotoma.common.viewobjects.UserVO;

public interface UserService extends UserDetailsService {	
	public UserVO loadCurrentUser();
	public UserContext getUserContext(Authentication authentication);
	public UserVO getUserByUsername(String username);
	public UserVO getUserVOByUserId(Integer userId);
	public List<UserVO> searchUser(QueryParam param) ;		
	public Person createUser(Person person) throws SQLException;
	public Student updateStudent(Student student) throws SQLException;
	public Parent updateParent(Parent parent) throws SQLException;
	public Teacher updateTeacher(Teacher teacher, Department department, Position position) throws SQLException;
	public Staff updateStaff(Staff staff, Position position) throws SQLException;
}
