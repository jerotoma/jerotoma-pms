package com.jerotoma.config.auth.filters;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

public class CustomCorsConfig implements CorsConfigurationSource{
	
	private CorsConfiguration corsConfiguration;
	
	public CustomCorsConfig(){
		corsConfiguration = new CorsConfiguration();
	}

	@Override
	public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
		//http://localhost:8080/pdf/F000703770_PA.pdf
		 corsConfiguration.addAllowedOrigin("/**");	
		 return corsConfiguration;
	}

}
