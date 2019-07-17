package com.jerotoma.config.auth.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;



public class DoRequestSecurityAuditFilter extends GenericFilterBean {

	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		final HttpServletResponse httpResponse = (HttpServletResponse) response;
		final SecurityContext securityContext = SecurityContextHolder.getContext();
		validateCurrentUserToken(httpRequest, httpResponse, securityContext);

		chain.doFilter(request, response);
	}

	protected void validateCurrentUserToken(final HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			SecurityContext securityContext) {

		//Authentication authentication = securityContext.getAuthentication();
		
		
		
	}
}
