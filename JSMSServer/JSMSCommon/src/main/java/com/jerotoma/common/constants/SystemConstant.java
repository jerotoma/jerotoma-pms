package com.jerotoma.common.constants;

public class SystemConstant {
	
	public static final String UPDATED_ON = "updated_on";
	public static final String CREATED_ON = "created_on";
	public static final String ADDED_BY = "added_by";

	public static final String COUNT = "count";
	public static final String DOC_FILE_PATH = "/resources/";
	public static final String PACKAGES_TO_SCAN = "com.jerotoma";
	
	public static final String CDN_PART = ".sfo2.cdn.";
	public static final String REPLACED_PART = ".sfo2.";
	
	public static final String DIGITAL_OCEAN_BASE_CDN_URL = "https://jerotoma.sfo2.digitaloceanspaces.com";
	public static final String DIGITAL_OCEAN_CDN_URL = "https://jerotoma.sfo2.cdn.digitaloceanspaces.com";
	
	
	public static final String UPLOAD_PATH =   System.getProperty("catalina.base") + "/storage/uploads";
	public static final String UPLOAD_BASE_PATH =  "/uploads";
	public static final String PAGE_COUNT = "pageCount";
		
	public static final String JPA_JDBC_DRIVER = "javax.persistence.jdbc.driver";
	public static final String JPA_JDBC_URL = "javax.persistence.jdbc.url";
	public static final String JPA_JDBC_USERNAME = "javax.persistence.jdbc.user";
	public static final String JPA_JDBC_PASSWORD = "javax.persistence.jdbc.password";
	public static final String JPA_JDBC_DIALECT = "hibernate.dialect";
	public static final Integer COOKIE_LIFE_TIME = 24*60*60;
	public static final String JWT_COOKIE_AUTH_TOKEN = "jwtCookieAuthToken";
	public static final String JWT_COOKIE_AUTH_REFRESH_TOKEN = "jwtCookieAuthRefreshToken";
	
	public static final String SERVER_TOMCAT_BASEDIR = "server.tomcat.basedir";
	public static final long TIME_BEFORE_EXPIRATION = 5000;  //millisecond
	public static final String DEV = "dev";
	public static final String PROD = "prod";
	
	public static final String BASE_DIRECTORY_NAME = "resources/uploads";
	public static final String FORWARD_SLASH = "/";
	public static final String PERIOD = ".";
	
	public static final String SUCCESS = "success";
	public static final String MESSAGE = "message";
	public static final String HTTP_STATUS = "httpStatus";
	public static final String STORED_OBJECTS = "stored_objects";
	public static final String CONSTRAINT_NAME = "constraintName";
	public static final String UPDATED_BY = "updatedBy";
			
	public static enum cronTrigger{
		EXPIRED_TOKENS("*/2 * * * * ?"),
		COMMISSION_AND_CASHBACK("*/5 0-59 0 ? * THU");
		
		private String time;		
		
		cronTrigger(String time){
			this.time = time;
		}
		
		public String getRunTime() {
			return time;
		}		
	}

}
