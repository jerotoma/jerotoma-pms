package com.jerotoma.api.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jerotoma.common.exceptions.InvalidJwtTokenException;
import com.jerotoma.common.users.AuthUser;
import com.jerotoma.config.auth.common.AuthProcessor;
import com.jerotoma.config.auth.common.UserContext;
import com.jerotoma.config.auth.interfaces.IAuthenticationFacade;
import com.jerotoma.config.auth.jwt.extractor.TokenExtractor;
import com.jerotoma.config.auth.jwt.verifier.TokenVerifier;
import com.jerotoma.config.auth.tokens.AccessJwtToken;
import com.jerotoma.config.auth.tokens.AuthToken;
import com.jerotoma.config.auth.tokens.JwtTokenFactory;
import com.jerotoma.config.auth.tokens.RawAccessJwtToken;
import com.jerotoma.config.auth.tokens.RefreshToken;
import com.jerotoma.config.constants.SecurityConstant;
import com.jerotoma.services.cookies.CookieService;
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
    @Autowired private JwtTokenFactory tokenFactory;
    @Autowired private AuthUserService userService;
    @Autowired private TokenVerifier tokenVerifier;
    @Autowired private AuthProcessor authProcessor;
    @Autowired @Qualifier("jwtHeaderTokenExtractor") TokenExtractor tokenExtractor;
    @Autowired IAuthenticationFacade authenticationFacade;   
	
    
    @RequestMapping(value="/refresh-token", method=RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody Map<String, Object> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	

		Map<String, Object> tokenMap = new HashMap<>();
                 
    	String tokenPayload = cookieService.findCookieValue(request, SecurityConstant.JWT_COOKIE_AUTH_TOKEN);
    	
    	String token = tokenExtractor.extract(tokenPayload);
       
    	RawAccessJwtToken rawToken = new RawAccessJwtToken(token);
        RefreshToken refreshToken = RefreshToken.create(rawToken, tokenFactory.getJwtSettings().getTokenSigningKey()).orElseThrow(() -> new InvalidJwtTokenException());

        String jti = refreshToken.getJti();
        if (!tokenVerifier.verify(jti)) {
            throw new InvalidJwtTokenException();
        }

        String subject = refreshToken.getSubject();
        AuthUser user = userService.loadUserByUsername(subject);

        if (user == null) {
        	throw new InsufficientAuthenticationException("User has no roles assigned");
        }
        
        
        UserContext userContext = UserContext.create(user.getUsername(), user.getAuthorities());
        AccessJwtToken accessJwtToken = tokenFactory.createAccessJwtToken(userContext);
        
        AuthToken authToken = authProcessor.getAuthToken(accessJwtToken, refreshToken);
        tokenMap.put("authToken", authToken);
        tokenMap.put("auth", user);
       
        return tokenMap;
    }
}