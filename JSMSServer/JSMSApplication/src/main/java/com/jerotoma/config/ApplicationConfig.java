package com.jerotoma.config;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import com.jerotoma.common.config.env.EnvironmentConfig;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.config.auth.common.AuthProcessor;

@Configuration
@ComponentScan
@EnableJpaRepositories(basePackages = {"com.jerotoma.database"})
public class ApplicationConfig {
	@Autowired DataSource dataSource;
	Properties properties = EnvironmentConfig.loadAppEnv();
	
	@Bean 
    public AuthProcessor authProcessor() {
    	return new AuthProcessor();
    }
	
	@Bean
    public LocaleResolver localeResolver() {
        final CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(Locale.ENGLISH);
        return cookieLocaleResolver;
    }
	
	@Bean(name="entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
		Map<String, String> mapProperties = new HashMap<>();
		mapProperties.put(SystemConstant.JPA_JDBC_DRIVER, properties.getProperty("spring.datasource.driver-class-name"));
		mapProperties.put(SystemConstant.JPA_JDBC_URL, properties.getProperty("spring.datasource.url"));
		mapProperties.put(SystemConstant.JPA_JDBC_USERNAME, properties.getProperty("spring.datasource.username"));
		mapProperties.put(SystemConstant.JPA_JDBC_PASSWORD, properties.getProperty("spring.datasource.password"));
		mapProperties.put(SystemConstant.JPA_JDBC_DIALECT, properties.getProperty("spring.datasource.dialect"));
				
        return builder
        		.dataSource(dataSource)
        		//.properties(mapProperties)
        		.jta(false)
        		.packages(SystemConstant.PACKAGES_TO_SCAN)
        		.build();
	}
}
