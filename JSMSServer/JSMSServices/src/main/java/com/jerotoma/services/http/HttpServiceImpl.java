package com.jerotoma.services.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jerotoma.common.constants.SecurityConstant;
import com.jerotoma.services.cookies.CookieService;


@Service
public class HttpServiceImpl implements HttpService{
	
	@Autowired private CookieService cookieService;
	@Autowired private ObjectMapper mapper;

	@Override
	public void processResponse(
			HttpServletRequest request, 
			HttpServletResponse response, 
			String refreshToken,
			String accessToken) throws IOException, JsonGenerationException, JsonMappingException {
		
		Map<String, Object> tokenMap = new HashMap<>();
		
		cookieService.createCookie(response, SecurityConstant.JWT_COOKIE_AUTH_REFRESH_TOKEN,  refreshToken, -1);
		response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setHeader(SecurityConstant.AUTHENTICATION_HEADER_NAME, SecurityConstant.HEADER_PREFIX + accessToken);
        tokenMap.put("success", true);
        
        mapper.writeValue(response.getWriter(), tokenMap);
		
	}
	
	
}
