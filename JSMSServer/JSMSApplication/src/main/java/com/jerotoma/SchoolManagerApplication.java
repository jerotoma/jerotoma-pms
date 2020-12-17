package com.jerotoma;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

@SpringBootApplication
public class SchoolManagerApplication extends SpringBootServletInitializer implements CommandLineRunner {
	
	@Value("classpath:db/database-functions.sql")
	Resource databaseFunctionResource;
	
	@Autowired DataSource dataSource;
	
	private static Class<SchoolManagerApplication> applicationClass = SchoolManagerApplication.class;	
		
	public static void main(String[] args) {
		SpringApplication.run(applicationClass, args);
	}	
	
	 
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }


	@Override
	public void run(String... args) throws Exception {
		runDatabaseResources(dataSource, databaseFunctionResource);		
	}
	
	private void runDatabaseResources(DataSource dataSource, Resource resource) {		
		try {        	      
   	     ScriptUtils.executeSqlScript(
   	    		 dataSource.getConnection(),
   	    		 new EncodedResource(resource, "UTF-8"), 
   	    		 false, 
   	    		 false, 
   	    		 ScriptUtils.DEFAULT_COMMENT_PREFIX, 
   	    		 ScriptUtils.DEFAULT_STATEMENT_SEPARATOR + ';',
   	    		 ScriptUtils.DEFAULT_BLOCK_COMMENT_START_DELIMITER, 
   	    		 ScriptUtils.DEFAULT_BLOCK_COMMENT_END_DELIMITER);
   	    } catch (Exception e) {
   	      e.printStackTrace();
   	    }
	}
}