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
import com.jerotoma.common.exceptions.CustomAccessDiniedException;
import com.jerotoma.config.auth.AuthenticationFailureHandler;
import com.jerotoma.config.auth.CustomLogoutSuccessHandler;
import com.jerotoma.config.auth.filters.AjaxLoginProcessingFilter;
import com.jerotoma.config.auth.filters.ApiCorsFilter;
import com.jerotoma.config.auth.filters.JwtTokenAuthenticationProcessingFilter;
import com.jerotoma.config.auth.jwt.SkipPathRequestMatcher;
import com.jerotoma.config.auth.jwt.extractor.TokenExtractor;
import com.jerotoma.config.auth.providers.CustomAuthenticationProvider;
import com.jerotoma.config.auth.providers.JwtAuthenticationProvider;
import com.jerotoma.services.cookies.CookieService;
import com.jerotoma.services.users.AuthUserService;

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
	@Autowired CustomLogoutSuccessHandler logoutSuccessHandler;
	@Autowired CookieService cookieService;
	@Autowired TokenExtractor tokenExtractor;
	@Autowired ObjectMapper objectMapper;
	@Autowired BCryptPasswordEncoder encoder;
	@Autowired AuthUserService userService;
	@Autowired CustomAccessDiniedException accessDeniedHandler;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
    	
		http 
		.csrf()
     		.disable()
     		.antMatcher(EndPointConstants.API_ROOT + EndPointConstants.SLASH_DOUBLE_ASTERIK)		    		
	    .authorizeRequests()
    		.antMatchers(EndPointConstants.loadPermittedAppEndpoints().split(",")).permitAll() 
    		.antMatchers(EndPointConstants.loadAPIPermittedAppEndpoints().split(",")).permitAll() 
    		.anyRequest()
    		.authenticated()   		        		
    		.and()    	
    	.addFilterBefore(new ApiCorsFilter(), UsernamePasswordAuthenticationFilter.class)
   	     	.addFilterBefore(buildAjaxLoginProcessingFilter(EndPointConstants.API_AUTH_LOGIN_URL), UsernamePasswordAuthenticationFilter.class)
   	     	.addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
   	     	.httpBasic()
   	     	.and() 
    	.exceptionHandling()
	 	 	.authenticationEntryPoint(authenticationEntryPoint)
	 	 	.accessDeniedHandler(accessDeniedHandler)
	 	 	.accessDeniedPage(EndPointConstants.ROOT_URL)
	 	 	.and()
	 	.cors()
	 		.configurationSource(corsConfigurationSource())
	 		.and()   
	 	.sessionManagement()
           	.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
           	.and()
         .logout()
         	.invalidateHttpSession(true)
         	.clearAuthentication(true)
         	.logoutSuccessHandler(logoutSuccessHandler)
         	.logoutUrl(EndPointConstants.API_LOGOUT_URL);
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
        configuration.setAllowedMethods(Arrays.asList("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS"));
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
	
}
