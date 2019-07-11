package com.jerotoma.http.endpoints;

import java.util.Arrays;
import java.util.List;

public class EndPointConstants {
	
	public static final String ROOT_URL = "/";
	public static final String API_ROOT = "/api/secured";
	public static final String APP_DASHBOARD_URL = "/dashboard";
	public static final String SLASH_DOUBLE_ASTERIK = "/**";
	public static final String APP_AUTH_LOGIN_URL = "/login";
	public static final String APP_LOGOUT_URL = "/logout";
	public static final String APP_AUTH_SIGNUP_URL = "/myaccount/create";
	public static final String APP_AUTH_FORGOT_PASSWORD_URL = "/myaccount/forgotPassword";
	public static final String APP_RESOURCES_URL = "/resources";
	public static final String APP_ASSETS_URL = "/assets";
	
	public static interface LOGIN_CONTROLLER {
		public static final String BASE = API_ROOT + "/login";
		public static final String INDEX = "/";
		
		
	}
	
	public static interface HOME_CONTROLLER {
		public static final String BASE = ROOT_URL;
		public static final String INDEX = "/**";
		
		
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
			 ROOT_URL,
			 APP_AUTH_LOGIN_URL + SLASH_DOUBLE_ASTERIK,
			 APP_AUTH_SIGNUP_URL + SLASH_DOUBLE_ASTERIK,
			 APP_AUTH_FORGOT_PASSWORD_URL + SLASH_DOUBLE_ASTERIK,
			 APP_RESOURCES_URL + SLASH_DOUBLE_ASTERIK,
			 APP_ASSETS_URL + SLASH_DOUBLE_ASTERIK					
	        );

}