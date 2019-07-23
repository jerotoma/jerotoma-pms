package com.jerotoma.config.auth.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.jerotoma.config.auth.jwt.JwtAuthenticationToken;
import com.jerotoma.config.auth.jwt.extractor.TokenExtractor;
import com.jerotoma.config.auth.tokens.RawAccessJwtToken;
import com.jerotoma.config.constants.SecurityConstant;


public class JwtTokenAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
    
	private final AuthenticationFailureHandler failureHandler;
    private final TokenExtractor tokenExtractor;
    
    @Autowired
    public JwtTokenAuthenticationProcessingFilter(
    		AuthenticationFailureHandler failureHandler, 
    		TokenExtractor tokenExtractor, 
            RequestMatcher matcher) {
    	
        super(matcher);
        this.failureHandler = failureHandler;
        this.tokenExtractor = tokenExtractor;       
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
    	
    	String tokenPayload = request.getHeader(SecurityConstant.AUTHENTICATION_HEADER_NAME);    	    	
        RawAccessJwtToken token = new RawAccessJwtToken(tokenExtractor.extract(tokenPayload));
        JwtAuthenticationToken  jwtAuthToken = new JwtAuthenticationToken(token);
        return getAuthenticationManager().authenticate(jwtAuthToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        failureHandler.onAuthenticationFailure(request, response, failed);
    }
}