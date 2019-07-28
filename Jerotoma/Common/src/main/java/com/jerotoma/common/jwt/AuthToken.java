package com.jerotoma.common.jwt;

import java.io.Serializable;

import io.jsonwebtoken.Claims;

public class AuthToken implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Claims claims;
	String token;
	String refreshToken;
	String issuer;
	Long refreshTokenExpTime;
	Long tokenExpirationTime;
	Long tokenIssuedAt;
	Long refreshTokenIssuedAt;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getTokenIssuer() {
		return issuer;
	}
	public void setTokenIssuer(String tokenIssuer) {
		this.issuer = tokenIssuer;
	}
	public Long getRefreshTokenExpTime() {
		return refreshTokenExpTime;
	}
	public void setRefreshTokenExpTime(Long refreshTokenExpTime) {
		this.refreshTokenExpTime = refreshTokenExpTime;
	}
	public Long getTokenExpirationTime() {
		return tokenExpirationTime;
	}
	public void setTokenExpirationTime(Long tokenExpirationTime) {
		this.tokenExpirationTime = tokenExpirationTime;
	}
	public Claims getClaims() {
		return claims;
	}
	public void setClaims(Claims claims) {
		this.claims = claims;
	}
	public Long getTokenIssuedAt() {
		return tokenIssuedAt;
	}
	public void setTokenIssuedAt(Long tokenIssuedAt) {
		this.tokenIssuedAt = tokenIssuedAt;
	}
	public Long getRefreshTokenIssuedAt() {
		return refreshTokenIssuedAt;
	}
	public void setRefreshTokenIssuedAt(Long refreshTokenIssuedAt) {
		this.refreshTokenIssuedAt = refreshTokenIssuedAt;
	}
}
