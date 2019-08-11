package com.jerotoma.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.http.HttpStatusAndMessageProcessor;

@Controller
@RequestMapping(EndPointConstants.ERROR_URL)
public class BasicErrorController extends AbstractErrorController implements ErrorController  {
 
    public BasicErrorController(ErrorAttributes errorAttributes, List<ErrorViewResolver> errorViewResolvers) {
	super(errorAttributes, errorViewResolvers);
		
	}

    @RequestMapping
	public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
		Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
		HttpStatus status = HttpStatusAndMessageProcessor.getStatus(request);
		return new ResponseEntity<Map<String, Object>>(body, status);
	}
 
    private boolean isIncludeStackTrace(HttpServletRequest request, MediaType all) {
		return false;
	}

	@Override
    public String getErrorPath() {
        return EndPointConstants.ERROR_URL;
    }
	
}
