package com.jerotoma.services.securities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.constants.ExceptionMessageConstant;
import com.jerotoma.common.constants.RoleConstant;
import com.jerotoma.common.exceptions.UnAuthorizedAccessException;
import com.jerotoma.common.viewobjects.UserVO;
import com.jerotoma.services.users.UserService;

@Service
public class UserSecurityClearance {
	
	@Autowired UserService userService;
	
	public UserSecurityClearance() {}	
	
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
		
		if (currentUser.getId() != userId) {			
			if (!currentUser.getRoles().contains(RoleConstant.USER_ROLES.ROLE_ADMIN)) {
				throw unAuthorizedAccess;
			}
		}		
	}
}
