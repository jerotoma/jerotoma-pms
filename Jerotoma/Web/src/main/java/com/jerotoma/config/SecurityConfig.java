package com.jerotoma.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.config.auth.AuthenticationFailureHandler;
import com.jerotoma.config.auth.filters.AjaxLoginProcessingFilter;
import com.jerotoma.config.auth.filters.ApiCorsFilter;
import com.jerotoma.config.auth.filters.JwtTokenAuthenticationProcessingFilter;
import com.jerotoma.config.auth.jwt.SkipPathRequestMatcher;
import com.jerotoma.config.auth.jwt.extractor.TokenExtractor;
import com.jerotoma.config.auth.providers.CustomAuthenticationProvider;
import com.jerotoma.config.auth.providers.JwtAuthenticationProvider;
import com.jerotoma.services.cookies.CookieService;
import com.jerotoma.services.users.AuthUserService;
import com.stripe.net.APIResource.RequestMethod;

@Configuration
@ComponentScan
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired Environment env;
	@Autowired RestAuthenticationEntryPoint authenticationEntryPoint;  
	@Autowired AuthenticationSuccessHandler successHandler;
	@Autowired AuthenticationFailureHandler authFailureHandler;
	@Autowired AuthenticationManager authenticationManager;	
	@Autowired JwtAuthenticationProvider jwtAuthenticationProvider;
	@Autowired CustomAuthenticationProvider ajaxAuthenticationProvider;
	@Autowired CookieService cookieService;
	@Autowired TokenExtractor tokenExtractor;
	@Autowired ObjectMapper objectMapper;
	@Autowired BCryptPasswordEncoder encoder;
	@Autowired AuthUserService userService;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
    	
		http 
		.csrf()
     		.disable()
     		.antMatcher(EndPointConstants.API_SECURED_ROOT + EndPointConstants.SLASH_DOUBLE_ASTERIK)		    		
	    .authorizeRequests()
    		.antMatchers(EndPointConstants.loadPermittedAppEndpoints().split(",")).permitAll() 
    		.anyRequest().authenticated()    		        		
    		.and()        	  	 		
    	.exceptionHandling()
	 	 	.authenticationEntryPoint(authenticationEntryPoint)
	 	 	.and()
	 	.cors()
	 		.configurationSource(corsConfigurationSource())
	 		.and()   
	 	.sessionManagement()
           	.sessionCreationPolicy(SessionCreationPolicy.STATELESS)           	
           	.and()     	 	
	     .addFilterBefore(new ApiCorsFilter(), UsernamePasswordAuthenticationFilter.class)
	     .addFilterBefore(buildAjaxLoginProcessingFilter(EndPointConstants.API_AUTH_LOGIN_URL), UsernamePasswordAuthenticationFilter.class)
	     .addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
	     .httpBasic();	    			
    }
   	
    protected AjaxLoginProcessingFilter buildAjaxLoginProcessingFilter(String loginUrl) throws Exception {
        AjaxLoginProcessingFilter filter = new AjaxLoginProcessingFilter(loginUrl, successHandler, authFailureHandler, objectMapper);
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }
    
    protected JwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter() throws Exception {
        List<String> pathsToSkip = Arrays.asList(
        		EndPointConstants.API_AUTH_LOGIN_URL, 
        		EndPointConstants.API_REFRESH_TOKEN_URL);
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, EndPointConstants.API_SECURED_ROOT + EndPointConstants.SLASH_DOUBLE_ASTERIK);
        
        JwtTokenAuthenticationProcessingFilter filter
            = new JwtTokenAuthenticationProcessingFilter(authFailureHandler, tokenExtractor, matcher);
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }
    	
    @Override
    protected void configure(AuthenticationManagerBuilder authBuilder) throws Exception {
    	authBuilder.authenticationProvider(jwtAuthenticationProvider);
    	authBuilder.authenticationProvider(ajaxAuthenticationProvider);	    	 
    }  
	
	@Bean
    CorsConfigurationSource corsConfigurationSource() {
        CustomCorsConfiguration configuration = new CustomCorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(env.getProperty("app.frontend.url")));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", RequestMethod.DELETE.toString()));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
	
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Override
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
	@Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
    	return new CustomAuthenticationProvider(userService, encoder);
    }
}
