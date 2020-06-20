package com.jerotoma.common.constants;

public class SecurityConstant {
	
	public static final String JWT_COOKIE_AUTH_REFRESH_TOKEN = "jwtRefreshAuthToken";
	public static final Long EXPIRATION_TIME = 120L;
	public static final Integer COOKIE_LIFE_TIME = 24*60*60;
	public static final String JWT_COOKIE_AUTH_TOKEN_EXPIRATION_TIME = "jwtAuthTokenExpirationTime";
	public static final String JWT_COOKIE_AUTH_TOKEN = "jwtAuthToken";
	public static final String JWT_COOKIE_REFRESH_AUTH_TOKEN = "jwtRefreshAuthToken";
	public static final long TIME_BEFORE_EXPIRY = 123L;
	public static final String HEADER_PREFIX = "Bearer ";
	public static final String AUTHENTICATION_HEADER_NAME = "Authorization";
	public static final String JWT_TOKEN_HEADER_PARAM = "X-Authorization";
	public static final String REFRESH_TOKEN_KEY = "refreshToken";
	public static final String REFRESH_TOKEN_VALUE = "ROLE_REFRESH_TOKEN";
	public static final String CURRENT_LOGGED_IN_USER = "currentLoggegInUser";
	
	

}
