package com.jerotoma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SchoolManagerApplication extends SpringBootServletInitializer {
	
	private static Class<SchoolManagerApplication> applicationClass = SchoolManagerApplication.class;	
		
	public static void main(String[] args) {
		SpringApplication.run(applicationClass, args);
	}	
		 
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }
}