package com.jerotoma.services.cookies;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CookieService {
	public Cookie createCookie(HttpServletResponse response, String cookieKey, String cookieValue, Integer maxAge);
	public String findCookieValue(HttpServletRequest request, String cookieKey);
	public boolean deleteAllCookies(HttpServletRequest request, HttpServletResponse response);
	public boolean isCookiePresent(HttpServletRequest request, String cookieKey);
	public boolean deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieKey);	
	public List<Cookie> findCookies(HttpServletRequest request);
	public Cookie findCookie(HttpServletRequest request, String cookieKey);
}
