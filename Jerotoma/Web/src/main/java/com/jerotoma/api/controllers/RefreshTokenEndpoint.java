package com.jerotoma.api.controllers;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jerotoma.common.constants.SecurityConstant;
import com.jerotoma.common.exceptions.InvalidJwtTokenException;
import com.jerotoma.common.users.AuthUser;
import com.jerotoma.common.utils.StringUtility;
import com.jerotoma.config.auth.common.UserContext;
import com.jerotoma.config.auth.interfaces.IAuthenticationFacade;
import com.jerotoma.config.auth.jwt.extractor.TokenExtractor;
import com.jerotoma.config.auth.jwt.verifier.TokenVerifier;
import com.jerotoma.config.auth.tokens.AccessJwtToken;
import com.jerotoma.config.auth.tokens.JwtTokenFactory;
import com.jerotoma.config.auth.tokens.RawAccessJwtToken;
import com.jerotoma.config.auth.tokens.RefreshToken;
import com.jerotoma.services.cookies.CookieService;
import com.jerotoma.services.http.HttpService;
import com.jerotoma.services.users.AuthUserService;

/**
 * RefreshTokenEndpoint
 * 
 * @author vladimir.stankovic
 *
 * Aug 17, 2016
 */
@RestController
@RequestMapping("/api/auth")
public class RefreshTokenEndpoint {
	
    @Autowired private CookieService cookieService;
    @Autowired private HttpService httpService;
    @Autowired private JwtTokenFactory tokenFactory;
    @Autowired private AuthUserService userService;
    @Autowired private TokenVerifier tokenVerifier;
    @Autowired @Qualifier("jwtHeaderTokenExtractor") TokenExtractor tokenExtractor;
    @Autowired IAuthenticationFacade authenticationFacade;   
	
    
    @RequestMapping(value="/refresh-token", method=RequestMethod.POST, produces={ MediaType.APPLICATION_JSON_VALUE })
    public void refreshToken(@RequestBody Map<String, String> params, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	
    	if(!params.containsKey(SecurityConstant.REFRESH_TOKEN_KEY)) {
    		throw new InvalidJwtTokenException("Invalid Token");
    	}
    	
    	String refreshTokenKey = params.get(SecurityConstant.REFRESH_TOKEN_KEY);
    	
    	if (!refreshTokenKey.equals(SecurityConstant.REFRESH_TOKEN_VALUE)) {
    		throw new InvalidJwtTokenException("Invalid Token");
    	}	
    			
    	String tokenPayload = cookieService.findCookieValue(request, SecurityConstant.JWT_COOKIE_AUTH_REFRESH_TOKEN);
    	if (StringUtility.isEmpty(tokenPayload)) {
        	throw new InsufficientAuthenticationException("Token can not be null");
        }
    	   	
    	String token = tokenExtractor.extract(SecurityConstant.HEADER_PREFIX + tokenPayload);
       
    	RawAccessJwtToken rawToken = new RawAccessJwtToken(token);
        RefreshToken refreshToken = RefreshToken.create(rawToken, tokenFactory.getJwtSettings().getTokenSigningKey()).orElseThrow(() -> new InvalidJwtTokenException("Invalid Token"));

        String jti = refreshToken.getJti();
        if (!tokenVerifier.verify(jti)) {
            throw new InvalidJwtTokenException("Invalid Token");
        }

        String subject = refreshToken.getSubject();
        AuthUser user = userService.loadUserByUsername(subject);

        if (user == null) {
        	throw new UsernameNotFoundException("User not found of the provided token");
        }
        
        
        UserContext userContext = UserContext.create(user.getUsername(), user.getAuthorities());
        AccessJwtToken accessJwtToken = tokenFactory.createAccessJwtToken(userContext);
        httpService.processResponse(request, response, refreshToken.getToken(), accessJwtToken.getToken());
        
      }
}