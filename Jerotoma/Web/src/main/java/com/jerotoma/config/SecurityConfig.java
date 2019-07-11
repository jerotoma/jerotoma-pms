package com.jerotoma.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.jerotoma.http.endpoints.EndPointConstants;

@Configuration
@ComponentScan
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	 @Autowired Environment env;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
    	
		http
		.cors()
			.configurationSource(corsConfigurationSource())
			.and()		
    	.authorizeRequests()
	    	.antMatchers(EndPointConstants.loadPermittedAppEndpoints().split(",")).permitAll()
    		.antMatchers(
    				EndPointConstants.APP_DASHBOARD_URL + EndPointConstants.SLASH_DOUBLE_ASTERIK,
    				EndPointConstants.API_ROOT + EndPointConstants.SLASH_DOUBLE_ASTERIK
    				)
    		.authenticated()
    		.and()    
		.formLogin()
    		.loginPage(EndPointConstants.APP_AUTH_LOGIN_URL)
    		.failureForwardUrl(EndPointConstants.APP_AUTH_LOGIN_URL) 
    		.defaultSuccessUrl(EndPointConstants.APP_DASHBOARD_URL)    		
    		.permitAll()
			.and()
		.logout()
	 		.logoutUrl(EndPointConstants.APP_LOGOUT_URL)
	 		.logoutSuccessUrl(EndPointConstants.ROOT_URL)	 		
	 		.permitAll();
    	
    }	
	
	@Bean
    CorsConfigurationSource corsConfigurationSource() {
        CustomCorsConfiguration configuration = new CustomCorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(env.getProperty("app.frontend.url")));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
