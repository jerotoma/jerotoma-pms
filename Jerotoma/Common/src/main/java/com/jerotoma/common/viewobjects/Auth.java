package com.jerotoma.common.viewobjects;

import java.util.List;

import com.jerotoma.common.constants.RoleConstant.USER_ROLES;
import com.jerotoma.common.constants.UserConstant.USER_TYPE;

public class Auth {
	private Integer userId;
	private String fullName;
	private String avatar;
	private Integer avatarId;
	private List<USER_ROLES>  roles;
	private USER_TYPE userType;
	
	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getAvatar() {
		return avatar;
	}
	
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	public Integer getAvatarId() {
		return avatarId;
	}
	
	public void setAvatarId(Integer avatarId) {
		this.avatarId = avatarId;
	}
	
	public List<USER_ROLES> getRoles() {
		return roles;
	}
	
	public void setRoles(List<USER_ROLES> roles) {
		this.roles = roles;
	}
	
	public USER_TYPE getUserType() {
		return userType;
	}
	
	public void setUserType(USER_TYPE userType) {
		this.userType = userType;
	}
}
