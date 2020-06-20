package com.jerotoma.common.viewobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.jerotoma.common.constants.RoleConstant.USER_ROLES;
import com.jerotoma.common.models.security.Role;
import com.jerotoma.common.models.users.AuthUser;

public class UserVO extends PersonVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<USER_ROLES> roles;	
	private PositionVO position;	
	private DepartmentVO department;
	private Integer studentNumber;

	public UserVO(AuthUser authUser, PersonVO person) {
		super(person);		
		this.userId = authUser.getId();
		this.username = authUser.getUsername();	    
		this.roles = getUserRoles(authUser.getRoles());
		
		if (person instanceof TeacherVO) {
			TeacherVO teacher = (TeacherVO)person;
			this.position = teacher.getPosition();
			this.department = teacher.getDepartment();
		}
		
		if (person instanceof StudentVO) {
			StudentVO student = (StudentVO)person;
			this.studentNumber = student.getStudentNumber();
			
		}
		
		if (person instanceof ParentVO) {
			ParentVO parent = (ParentVO)person;	
			parent.getPicture();
		}
		
		if (person instanceof StaffVO) {
			StaffVO staff = (StaffVO)person;
			this.position = staff.getPosition();
		}
		
	}

	private List<USER_ROLES> getUserRoles(Collection<Role> roles) {
		
		List<USER_ROLES> userRoles = new ArrayList<>();
		if (roles == null) {
			return userRoles;
		}		
		roles.stream().forEach(role -> {
			userRoles.add(USER_ROLES.getRole(role.getId()));
		});
		
		return userRoles;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<USER_ROLES> getRoles() {
		return roles;
	}

	public void setRoles(List<USER_ROLES> roles) {
		this.roles = roles;
	}

	public PositionVO getPosition() {
		return position;
	}

	public void setPosition(PositionVO position) {
		this.position = position;
	}

	public DepartmentVO getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentVO department) {
		this.department = department;
	}

	public Integer getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(Integer studentNumber) {
		this.studentNumber = studentNumber;
	}
}
