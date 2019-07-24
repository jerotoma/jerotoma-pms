package com.jerotoma.config.auth.tokens;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jerotoma.common.utils.StringUtility;
import com.jerotoma.config.auth.common.Scopes;
import com.jerotoma.config.auth.common.UserContext;
import com.jerotoma.config.auth.jwt.JwtSettings;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenFactory {
	
	private JwtSettings jwtSettings;
	
	@Autowired
    public JwtTokenFactory(JwtSettings settings) {
		this.jwtSettings = settings;
    }

    /**
     * Factory method for issuing new JWT Tokens.
     * 
     * @param userContext
     * @return AccessJwtToken
     */
    public AccessJwtToken createAccessJwtToken(UserContext userContext) {
        if (StringUtility.isEmpty(userContext.getUsername())) 
            throw new IllegalArgumentException("Cannot create JWT Token without username");

        if (userContext.getAuthorities() == null || userContext.getAuthorities().isEmpty()) 
            throw new IllegalArgumentException("User doesn't have any privileges");

        Claims claims = Jwts.claims().setSubject(userContext.getUsername());
        claims.put("scopes", userContext.getAuthorities().stream().map(s -> s.toString()).collect(Collectors.toList()));

        LocalDateTime currentTime = LocalDateTime.now();
        String token = Jwts.builder()
          .setClaims(claims)
          .setIssuer(jwtSettings.getTokenIssuer())
          .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
          .setExpiration(Date.from(currentTime
        	  .plusMinutes(jwtSettings.getTokenExpirationTime())
              .atZone(ZoneId.systemDefault()).toInstant()))
          .signWith(SignatureAlgorithm.HS512, jwtSettings.getTokenSigningKey())
        .compact();

        return new AccessJwtToken(token, claims);
    }

    public JwtToken createRefreshToken(UserContext userContext) {
        if (StringUtility.isEmpty(userContext.getUsername())) {
            throw new IllegalArgumentException("Cannot create JWT Token without username");
        }
        LocalDateTime currentTime = LocalDateTime.now();

        Claims claims = Jwts.claims().setSubject(userContext.getUsername());
        claims.put("scopes", Arrays.asList(Scopes.REFRESH_TOKEN.authority()));
        
        String token = Jwts.builder()
        		  .setClaims(claims)
		          .setIssuer(jwtSettings.getTokenIssuer())
		          .setId(UUID.randomUUID().toString())
		          .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
		          .setExpiration(Date.from(currentTime
		              .plusMinutes(jwtSettings.getRefreshTokenExpTime())
		              .atZone(ZoneId.systemDefault()).toInstant()))
		          .signWith(SignatureAlgorithm.HS512, jwtSettings.getTokenSigningKey())
		        .compact();

        return new AccessJwtToken(token, claims);
    }

	public JwtSettings getJwtSettings() {
		return jwtSettings;
	}

	public void setJwtSettings(JwtSettings jwtSettings) {
		this.jwtSettings = jwtSettings;
	}
    
    
}