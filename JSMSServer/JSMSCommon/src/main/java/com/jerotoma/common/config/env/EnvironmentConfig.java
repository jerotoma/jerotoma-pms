package com.jerotoma.common.config.env;

import java.io.IOException;
import java.util.Properties;

import com.jerotoma.common.utils.StringUtility;

public class EnvironmentConfig {
	
	public static final String DEV = "dev";
	public static final String PROD = "prod";
	private static final String ACTIVE_PROFILE = "spring.profiles.active";
	private static final String APP_PROPS_FILE_NAME = "application.properties";
	private static final String PROD_PROPS_FILE_NAME = "application-prod.properties";
	private static final String DEV_PROPS_FILE_NAME = "application-dev.properties";
	
	private static Properties loadConfigFile(String fileName) {
		final Properties properties = new Properties();
		try{
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName));
		}catch(IOException e) {			
			e.printStackTrace();
		}
		return properties;
	}
	
	public static Properties loadAppEnv() {
		Properties properties = null;
		if(!StringUtility.isEmpty(getActiveMode())) {
			if(isDev()) {
				properties = loadConfigFile(DEV_PROPS_FILE_NAME);
			} 
			if(isProd()) {
				properties = loadConfigFile(PROD_PROPS_FILE_NAME);
			}
		}		
		return properties;		
	}
	
	public static boolean isDev() {
		return getActiveMode().equals(DEV);
	}
	
	public static boolean isProd() {
		return getActiveMode().equals(PROD);
	}
	
	private static String getActiveMode() {
		Properties properties = loadConfigFile(APP_PROPS_FILE_NAME);
		return properties.getProperty(ACTIVE_PROFILE);
	}
	
}
