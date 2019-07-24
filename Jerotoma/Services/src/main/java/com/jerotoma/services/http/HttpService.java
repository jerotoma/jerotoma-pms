package com.jerotoma.services.http;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;



public interface HttpService {
	public void processResponse(HttpServletRequest request,	HttpServletResponse response, String refreshToken, String accessToken) throws IOException, JsonGenerationException, JsonMappingException;
}
