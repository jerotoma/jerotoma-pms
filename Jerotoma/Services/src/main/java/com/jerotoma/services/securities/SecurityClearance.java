package com.jerotoma.services.securities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.constants.ExceptionMessageConstant;
import com.jerotoma.common.constants.RoleConstant;
import com.jerotoma.common.exceptions.UnAuthorizedAccessException;
import com.jerotoma.common.viewobjects.UserVO;
import com.jerotoma.services.users.UserService;

@Service
public class SecurityClearance {

	@Autowired UserService userService;
	
	public SecurityClearance() {}
	
	public boolean canAccessSystem() {		
		return false;		
	}
	
	public void checkChangeUserProfileImageClearance(Integer userId) {	
		UserVO currentUser = userService.loadCurrentUser();
		UserVO userVO = userService.getUserByUserId(userId);
		UnAuthorizedAccessException unAuthorizedAccess = new UnAuthorizedAccessException(String.format(ExceptionMessageConstant.UN_AUTHORIZED_ACCESS_MESSAGE, "change profile image"));
		if (userVO == null) {
			throw unAuthorizedAccess;
		}
		
		if (currentUser.getUserId() != userId) {			
			checkAdminsAndExecutiveAuthorization(currentUser, unAuthorizedAccess);	
		}		
	}

	public void checkProgramCreationPermission() {
		UserVO currentUser = userService.loadCurrentUser();
		UnAuthorizedAccessException unAuthorizedAccess = new UnAuthorizedAccessException(String.format(ExceptionMessageConstant.UN_AUTHORIZED_ACCESS_MESSAGE, "create new program"));
		if (currentUser == null) {
			throw unAuthorizedAccess;
		}
		checkAdminsAndExecutiveAuthorization(currentUser, unAuthorizedAccess);	
	}
	
	public void checkGeneralEntityCreationPermission() {
		UserVO currentUser = userService.loadCurrentUser();
		UnAuthorizedAccessException unAuthorizedAccess = new UnAuthorizedAccessException(String.format(ExceptionMessageConstant.UN_AUTHORIZED_ACCESS_MESSAGE, "create new program"));
		if (currentUser == null) {
			throw unAuthorizedAccess;
		}
		checkAdminsAndExecutiveAuthorization(currentUser, unAuthorizedAccess);		
	}

	public void checkGeneralEntityModificationPermission() {
		UserVO currentUser = userService.loadCurrentUser();
		UnAuthorizedAccessException unAuthorizedAccess = new UnAuthorizedAccessException(String.format(ExceptionMessageConstant.UN_AUTHORIZED_ACCESS_MESSAGE, "create new program"));
		if (currentUser == null) {
			throw unAuthorizedAccess;
		}
		checkAdminsAndExecutiveAuthorization(currentUser, unAuthorizedAccess);			
	}

	public void checkGeneralEntityDeletionPermission() {
		UserVO currentUser = userService.loadCurrentUser();
		UnAuthorizedAccessException unAuthorizedAccess = new UnAuthorizedAccessException(String.format(ExceptionMessageConstant.UN_AUTHORIZED_ACCESS_MESSAGE, "create new program"));
		if (currentUser == null) {
			throw unAuthorizedAccess;
		}		
		checkAdminsAndExecutiveAuthorization(currentUser, unAuthorizedAccess);		
	}

	public void checkStudentCreationPermission() {
		UserVO currentUser = userService.loadCurrentUser();
		UnAuthorizedAccessException unAuthorizedAccess = new UnAuthorizedAccessException(String.format(ExceptionMessageConstant.UN_AUTHORIZED_ACCESS_MESSAGE, "create new program"));
		if (currentUser == null) {
			throw unAuthorizedAccess;
		}
		checkAdminsAndExecutiveAuthorization(currentUser, unAuthorizedAccess);			
	}

	public void checkRoleAssignationPermission() {
		
		UserVO currentUser = userService.loadCurrentUser();
		UnAuthorizedAccessException unAuthorizedAccess = new UnAuthorizedAccessException(String.format(ExceptionMessageConstant.UN_AUTHORIZED_ACCESS_MESSAGE, "create new program"));
		if (currentUser == null) {
			throw unAuthorizedAccess;
		}
		
		checkAdminsAndExecutiveAuthorization(currentUser, unAuthorizedAccess);		
	}

	protected void checkAdminsAndExecutiveAuthorization(UserVO currentUser, UnAuthorizedAccessException unAuthorizedAccess) {
		boolean allowedAccess = 
				currentUser.getRoles().contains(RoleConstant.USER_ROLES.ROLE_ADMIN) 
				|| currentUser.getRoles().contains(RoleConstant.USER_ROLES.ROLE_DIRECTOR) 
				|| currentUser.getRoles().contains(RoleConstant.USER_ROLES.ROLE_SUPER_ADMIN) 
				|| currentUser.getRoles().contains(RoleConstant.USER_ROLES.ROLE_PRINCIPAL);
		
		if (!allowedAccess) {
			throw unAuthorizedAccess;
		}
	}
}
