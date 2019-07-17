package com.jerotoma.config.env;

import java.io.IOException;
import java.util.Properties;

import com.jerotoma.common.utils.StringUtility;

public class EnvironmentConfig {
	
	public static final String DEV = "dev";
	public static final String PROD = "prod";
	
	private static Properties loadConfigFile(String fileName) {
		Properties properties = new Properties();
		try{
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName));
		}catch(IOException e) {			
			e.printStackTrace();
		}
		return properties;
	}
	
	public static Properties loadAppEnv() {
		Properties properties = loadConfigFile("application.properties");
		String activeProfile = properties.getProperty("spring.profiles.active");
		
		if(!StringUtility.isEmpty(activeProfile)) {
			if(activeProfile.equals(DEV)) {
				properties = loadConfigFile("application-dev.properties");
			} 
			if(activeProfile.equals(PROD)) {
				properties = loadConfigFile("application-prod.properties");
			}
		}		
		return properties;		
	}
	
}
