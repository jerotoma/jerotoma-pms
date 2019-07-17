package com.jerotoma.config.auth.common;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jerotoma.common.exceptions.InvalidJwtTokenException;
import com.jerotoma.common.utils.TextUtility;
import com.jerotoma.config.auth.tokens.AccessJwtToken;
import com.jerotoma.config.auth.tokens.AuthToken;
import com.jerotoma.config.auth.tokens.JwtTokenFactory;
import com.jerotoma.config.auth.tokens.RawAccessJwtToken;
import com.jerotoma.config.auth.tokens.RefreshToken;
import com.jerotoma.config.constants.SecurityConstant;
import com.jerotoma.exceptions.JwtExpiredTokenException;
import com.jerotoma.services.cookies.CookieService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public class AuthProcessor {
	
	public AuthProcessor(){}
	
	public AuthToken extractAuthToken(UserContext userContext, JwtTokenFactory tokenFactory) 
					throws IOException, JsonGenerationException, JsonMappingException {
		
		
		AccessJwtToken accessToken = tokenFactory.createAccessJwtToken(userContext);
		AccessJwtToken refreshAccessToken = tokenFactory.createRefreshToken(userContext);
		RawAccessJwtToken rawAccessToken = new RawAccessJwtToken(refreshAccessToken.getToken());
		
		RefreshToken refreshToken = RefreshToken.create(rawAccessToken, tokenFactory.getJwtSettings().getTokenSigningKey()).orElseThrow(() -> new InvalidJwtTokenException());
		
		return getAuthToken(accessToken,refreshToken);
        
	}

	public AuthToken getAuthToken(AccessJwtToken accessToken, RefreshToken refreshToken) {
		AuthToken authToken = new AuthToken();
		
		Claims accessTokenClaims = accessToken.getClaims();
		Claims accessRefreshTokenClaims = refreshToken.getClaims().getBody();
        authToken.setToken(accessToken.getToken());
        authToken.setTokenIssuer(accessTokenClaims.getIssuer());
        authToken.setTokenExpirationTime(accessTokenClaims.getExpiration().getTime() - SecurityConstant.TIME_BEFORE_EXPIRY);
        authToken.setTokenIssuedAt(accessTokenClaims.getIssuedAt().getTime());
        
        authToken.setRefreshToken(refreshToken.getToken());
        authToken.setRefreshTokenExpTime(accessRefreshTokenClaims.getExpiration().getTime());
        authToken.setRefreshTokenIssuedAt(accessRefreshTokenClaims.getIssuedAt().getTime());
       
        authToken.setClaims(accessTokenClaims);
		return authToken;
	}
	
	public void createCookie(HttpServletRequest request, HttpServletResponse response, AuthToken authToken, CookieService cookieService) {
				
        cookieService.createCookie(response, SecurityConstant.JWT_COOKIE_AUTH_TOKEN, authToken.getToken(), -1);
        cookieService.createCookie(response, SecurityConstant.JWT_COOKIE_AUTH_REFRESH_TOKEN, authToken.getRefreshToken(), -1);
        cookieService.createCookie(response, SecurityConstant.JWT_COOKIE_AUTH_TOKEN_EXPIRATION_TIME, TextUtility.longToString(authToken.getTokenExpirationTime()), -1);
	}
	
	
	public void deleteAllTokenCookie(HttpServletRequest request, HttpServletResponse response, CookieService cookieService) {
		
		if(cookieService.isCookiePresent(request,  SecurityConstant.JWT_COOKIE_AUTH_TOKEN)) {
			cookieService.deleteCookie(request, response, SecurityConstant.JWT_COOKIE_AUTH_TOKEN);
			cookieService.deleteCookie(request, response, SecurityConstant.JWT_COOKIE_AUTH_REFRESH_TOKEN);
			cookieService.deleteCookie(request, response, SecurityConstant.JWT_COOKIE_AUTH_TOKEN_EXPIRATION_TIME);
        }
	}
	
	public Claims claimsProcessor(RawAccessJwtToken token, String signingKey) throws BadCredentialsException, JwtExpiredTokenException {
		Jws<Claims> jwsClaims = token.parseClaims(signingKey);
		return jwsClaims.getBody();
	}
}
