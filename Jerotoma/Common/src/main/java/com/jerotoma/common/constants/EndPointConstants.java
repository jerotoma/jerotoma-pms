package com.jerotoma.common.constants;

import java.util.Arrays;
import java.util.List;

public class EndPointConstants {
	
	public static final String ROOT_URL = "/";
	public static final String ERROR_URL = "/error";
	public static final String API_ROOT = "/api";
	public static final String API_SECURED_ROOT = API_ROOT + "/secured";
	public static final String API_PUBLIC_ROOT = API_ROOT + "/public";
	
	public static final String API_PUB_URL =  API_ROOT + "/pub";
	public static final String API_REFRESH_TOKEN_URL =  API_ROOT + "/auth/token";
	
	public static final String APP_DASHBOARD_URL = "/dashboard";
	public static final String SLASH_DOUBLE_ASTERIK = "/**";
	public static final String APP_AUTH = "/auth";
	public static final String API_APP_AUTH = "/api/auth";
	public static final String APP_ACCOUNT = "/account";
	public static final String APP_AUTH_LOGIN_URL =  APP_AUTH + "/login";
	public static final String API_LOGOUT_URL =  API_ROOT + "/auth/sign-out";
	
	
	public static final String APP_AUTH_SIGNUP_URL = "/auth/register";
	public static final String APP_AUTH_FORGOT_PASSWORD_URL = "/auth/forgotPassword";
	public static final String APP_RESOURCES_URL = "/resources";
	public static final String APP_ASSETS_URL = "/assets";
	
	public static final String API_AUTH_LOGIN_URL = "/api/auth/login";
	
	public static interface REST_MEDIA_CONTROLLER {
		public static final String BASE =  API_SECURED_ROOT + "/uploads";	
	}
	
	public static interface REST_COURSE_CONTROLLER{
		public static final String BASE =  API_SECURED_ROOT + "/courses";	
	}
	
	public static interface REST_DEPARTMENT_CONTROLLER{
		public static final String BASE =  API_SECURED_ROOT + "/departments";	
	}
	
	public static interface REST_CLASS_CONTROLLER{
		public static final String BASE =  API_SECURED_ROOT + "/classes";	
	}
	
	public static interface REST_DASHBOARD_CONTROLLER{
		public static final String BASE =  API_SECURED_ROOT + "/dashboard";	
	}
	
	public static interface REST_ATTENDANCE_CONTROLLER {
		public static final String BASE =  API_SECURED_ROOT + "/attendances";	
	}
	
	public static interface REST_CLASS_ATTENDANCE_CONTROLLER {
		public static final String BASE =   REST_ATTENDANCE_CONTROLLER.BASE  + "/classes";	
	}
	
	public static interface REST_ATTENDANCE_STATUS_CONTROLLER {
		public static final String BASE =  REST_ATTENDANCE_CONTROLLER.BASE  + "/statuses";	
	}
	
	public static interface REST_STUDENT_ATTENDANCE_CONTROLLER {
		public static final String BASE =  REST_ATTENDANCE_CONTROLLER.BASE + "/students";	
	}
	public static interface REST_STAFF_ATTENDANCE_CONTROLLER {
		public static final String BASE = REST_ATTENDANCE_CONTROLLER.BASE + "/staffs";	
	}
	public static interface REST_TEACHER_ATTENDANCE_CONTROLLER {
		public static final String BASE =  REST_ATTENDANCE_CONTROLLER.BASE + "/teachers";	
	}
	
	public static interface REST_STUDENT_CLASS_CONTROLLER{
		public static final String BASE =  API_SECURED_ROOT + "/student-classes";	
	}
	
	public static interface REST_ROOM_CONTROLLER{
		public static final String BASE =  API_SECURED_ROOT + "/rooms";	
	}
	
	public static interface REST_ACADEMIC_YEAR_CONTROLLER{
		public static final String BASE =  API_SECURED_ROOT + "/academic-years";	
	}
	
	public static interface REST_MEETING_TIME_CONTROLLER {
		public static final String BASE =  API_SECURED_ROOT + "/meeting-times";	
	}
	
	public static interface REST_WORK_DAY_CONTROLLER {
		public static final String BASE =  API_SECURED_ROOT + "/work-days";	
	}
	
	
	
	public static interface REST_LOGIN_CONTROLLER {
		public static final String BASE =  API_SECURED_ROOT + "/auth/login";		
	}
	
	public static interface REST_POSITION_CONTROLLER {
		public static final String BASE =  API_SECURED_ROOT + "/positions";		
	}
	
	public static interface  REST_SYSTEM_CONFIG_CONTROLLER {
		public static final String BASE =  API_SECURED_ROOT + "/system-configs";		
	}
	
	public static interface  REST_PUB_SYSTEM_CONFIG_CONTROLLER {
		public static final String BASE =  API_PUBLIC_ROOT + "/system-configs";		
	}
	
	public static interface  REST_PUB_THEME_CONTROLLER {
		public static final String BASE =  API_PUBLIC_ROOT + "/themes";		
	}
	
	public static interface  REST_THEME_CONTROLLER {
		public static final String BASE =  API_SECURED_ROOT + "/themes";		
	}
	
	public static interface  REST_ACADEMIC_DISCIPLINE_CONTROLLER {
		public static final String BASE =  API_SECURED_ROOT + "/academic-disciplines";		
	}
	
	public static interface REST_USER_CONTROLLER {
		public static final String BASE = API_SECURED_ROOT+ "/users";
		public static final String INDEX = "/";
		public static final String SEARCH = "/search";
		public static final String CURRENT_USER = "/currentUser";
		public static final String CURRENT_USER_ROLES = CURRENT_USER + "/roles";
	}
	
	public static interface REST_PROGRAM_CONTROLLER {
		public static final String BASE =  API_SECURED_ROOT + "/programs";
		public static final String INDEX = "/";
	}
	
	public static interface REST_ROLE_CONTROLLER {
		public static final String BASE =  API_SECURED_ROOT + "/roles";
		public static final String INDEX = "/";
	}
	public static interface REST_PERMISSION_CONTROLLER {
		public static final String BASE =  API_SECURED_ROOT + "/permissions";
		public static final String INDEX = "/";
	}
	
	public static interface REST_ACADEMIC_LEVEL_CONTROLLER {
		public static final String BASE =  API_SECURED_ROOT + "/academic-levels";
		public static final String INDEX = "/";
	}
	
	public static interface REST_USER_PREFERENCE_CONTROLLER {
		public static final String BASE =  API_SECURED_ROOT + "/user-preferences";
	}
	
	public static interface REST_PUB_AUTH_CONTROLLER {
		public static final String BASE = API_APP_AUTH;
		public static final String REGISTER = "/register";	
		public static final String AUTH_USER = "/user";
	}
	
	public static interface REST_AUTH_CONTROLLER {
		public static final String BASE = API_SECURED_ROOT + "/auth";
		public static final String REGISTER = "/register";	
		public static final String AUTH_USER = "/user";
	}
	
	public static interface AUTH_CONTROLLER {
		public static final String BASE = APP_ACCOUNT;
		
		
		
	}
	
	public static interface HOME_CONTROLLER {
		public static final String BASE = ROOT_URL;
		
		
		
	}
	
	public static interface DASHBOARD_CONTROLLER {
		public static final String BASE = APP_DASHBOARD_URL;
		
		
		
	}
	
	public static interface USER_CONTROLLER {
		public static final String BASE = API_SECURED_ROOT + "/users";
		public static final String[] INDEX = {"", "/"};
		
		
	}

	public static String loadPermittedAppEndpoints() {
		StringBuilder builder = new StringBuilder();
    	for(String endPoint: PERMITTED_APP_ENDPOINTS) {
    		builder.append(endPoint);
    		builder.append(",");    		
    	}    	
    	return builder.toString(); 
	}
	
	public static final List<String> PERMITTED_APP_ENDPOINTS = Arrays.asList(
			 APP_AUTH_LOGIN_URL + SLASH_DOUBLE_ASTERIK,
			 APP_AUTH_SIGNUP_URL + SLASH_DOUBLE_ASTERIK,
			 APP_AUTH_FORGOT_PASSWORD_URL + SLASH_DOUBLE_ASTERIK,
			 APP_RESOURCES_URL + SLASH_DOUBLE_ASTERIK,
			 APP_ASSETS_URL + SLASH_DOUBLE_ASTERIK,
			 API_PUBLIC_ROOT + SLASH_DOUBLE_ASTERIK
	        );
	
	public static String loadAPIPermittedAppEndpoints() {
		StringBuilder builder = new StringBuilder();
    	for(String endPoint: PERMITTED_API_APP_ENDPOINTS) {
    		builder.append(endPoint);
    		builder.append(",");    		
    	}    	
    	return builder.toString(); 
	}
	
	public static final List<String> PERMITTED_API_APP_ENDPOINTS = Arrays.asList(
			API_APP_AUTH + SLASH_DOUBLE_ASTERIK,
			API_PUBLIC_ROOT + SLASH_DOUBLE_ASTERIK						
	        );
	
}