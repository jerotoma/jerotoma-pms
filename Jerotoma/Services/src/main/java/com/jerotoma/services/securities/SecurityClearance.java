package com.jerotoma.services.securities;

public interface SecurityClearance {
	public void checkChangeUserProfileImageClearance(int userId);
	
	public boolean canAccessSystem();

}
