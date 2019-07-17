package com.jerotoma.services.cookies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.utils.StringUtility;

@Service
public class CookieServiceImpl  implements CookieService{
	
	private String rootURL = "/";


	@Override
	public Cookie createCookie(HttpServletResponse response, String cookieKey, String cookieValue, Integer maxAge) {
		if (!StringUtility.isEmpty(cookieKey) && !StringUtility.isEmpty(cookieValue)) {
			Cookie cookieToken = new Cookie(cookieKey, cookieValue);
			int cookieAge = SystemConstant.COOKIE_LIFE_TIME;
			if(maxAge != null) {
				cookieAge = maxAge;
			}			
			cookieToken.setMaxAge(cookieAge);
			cookieToken.setPath(rootURL);
			response.addCookie(cookieToken);
			
			return cookieToken;
		}
		return null;
	}

	@Override
	public String findCookieValue(HttpServletRequest request, String cookieKey) {
		List<Cookie> cookies = findCookies(request);
		if(cookies != null && !cookies.isEmpty()) {
			for(Cookie cookie: cookies) {
	        	if(cookie.getName().equals(cookieKey)) {
	        		return cookie.getValue();            	
	        	}
	        } 
		}
		return null;
	}

	@Override
	public boolean deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieKey) {
		boolean isDeleted = false;
		List<Cookie> cookies = findCookies(request);
        if	(cookies != null && !cookies.isEmpty()) {
            for(Cookie cookie: cookies) {
            	if(cookie.getName().equals(cookieKey)) {
            		Cookie cookieToken = new Cookie(cookieKey, "");
            		cookieToken.setMaxAge(0);
            		cookieToken.setPath(rootURL);
            		response.addCookie(cookieToken);            		
            		isDeleted = true;
            	}
            }                  
        }
		return isDeleted;
	}

	@Override
	public boolean deleteAllCookies(HttpServletRequest request, HttpServletResponse response) {
		boolean isDeleted = false;
		List<Cookie> cookies = findCookies(request);
        if	(cookies != null && !cookies.isEmpty()) {
            for(Cookie cookie: cookies) {
            	Cookie cookieToken = new Cookie(cookie.getName(), "");
        		cookieToken.setMaxAge(0);
        		cookieToken.setPath(rootURL);
        		response.addCookie(cookieToken); 
        		isDeleted = true;
            }                  
        }
		return isDeleted;
	}

	@Override
	public Cookie findCookie(HttpServletRequest request, String cookieKey) {
		List<Cookie> cookies = findCookies(request);
		if(cookies != null && !cookies.isEmpty()) {
			for(Cookie cookie: cookies) {
	        	if(cookie.getName().equals(cookieKey)) {
	        		return cookie;            	
	        	}
	        } 
		}
		return null;
	}

	@Override
	public List<Cookie> findCookies(HttpServletRequest request) {
		if(request != null && request.getCookies() != null) {
			return Arrays.asList(request.getCookies());
		}
		return new ArrayList<Cookie>();
	}

	@Override
	public boolean isCookiePresent(HttpServletRequest request, String cookieKey) {		
		return findCookieValue(request, cookieKey) != null;
	}
}
