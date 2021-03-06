package com.jerotoma.common.viewobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.jerotoma.common.constants.RoleConstant.USER_ROLES;
import com.jerotoma.common.models.security.Role;
import com.jerotoma.common.models.users.User;

public class UserVO extends PersonVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<USER_ROLES> roles;	
	private PositionVO position;	
	private DepartmentVO department;
	private Integer studentNumber;
	
	private String relationshipType;
	
	private ParentVO primaryParent;
	
	private Integer programId;
	private String programName;
	
	private Integer academicLevelId;
	private String currentAcademicLevelName;

	public UserVO(User authUser, PersonVO person) {
		super(person);		
		this.userId = authUser.getId();
		this.username = authUser.getUsername();	    
		this.roles = getUserRoles(authUser.getRoles());
		this.enabled = authUser.isEnabled();
		this.accountNonExpired = authUser.isAccountNonExpired(); 
		this.credentialsNonExpired = authUser.isCredentialsNonExpired();
		this.accountNonLocked = authUser.isAccountNonLocked();
		
		if (person instanceof TeacherVO) {
			TeacherVO teacher = (TeacherVO)person;
			this.position = teacher.getPosition();
			this.department = teacher.getDepartment();
		}
		
		if (person instanceof StudentVO) {
			StudentVO student = (StudentVO)person;
			this.studentNumber = student.getStudentNumber();
			this.programId = student.getProgramId();
			this.programName = student.getProgramName();
			this.academicLevelId = student.getAcademicLevelId();
			this.currentAcademicLevelName = student.getCurrentAcademicLevelName();
			this.primaryParent = student.getPrimaryParent();
		}
		
		if (person instanceof ParentVO) {
			ParentVO parent = (ParentVO)person;	
			this.relationshipType = parent.getRelationshipType();
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

	public Integer getProgramId() {
		return programId;
	}

	public String getProgramName() {
		return programName;
	}

	public Integer getAcademicLevelId() {
		return academicLevelId;
	}

	public String getCurrentAcademicLevelName() {
		return currentAcademicLevelName;
	}

	public String getRelationshipType() {
		return relationshipType;
	}

	public ParentVO getPrimaryParent() {
		return primaryParent;
	}
}
