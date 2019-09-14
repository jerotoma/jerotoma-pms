package com.jerotoma;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SchoolManagerApplication extends SpringBootServletInitializer {
	
	private static Class<SchoolManagerApplication> applicationClass = SchoolManagerApplication.class;
		
	public static void main(String[] args) {
		SpringApplication.run(SchoolManagerApplication.class, args);
	}	
	
	@Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
           /* for (String beanName : beanNames) {
               System.out.println(beanName);
            } */
        };
    }
	 
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }
}