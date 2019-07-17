package com.jerotoma.config.auth.tokens;

import java.util.List;
import java.util.Optional;

import com.jerotoma.exceptions.JwtExpiredTokenException;
import com.jerotoma.config.auth.common.Scopes;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public class RefreshToken implements JwtToken {
    private Jws<Claims> claims;
    private String token;

    private RefreshToken(Jws<Claims> claims, String token) {
        this.claims = claims;
        this.token = token;
    }

    /**
     * Creates and validates Refresh token 
     * 
     * @param token
     * @param signingKey
     * 
     * @throws BadCredentialsException
     * @throws JwtExpiredTokenException
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
	public static Optional<RefreshToken> create(RawAccessJwtToken token, String signingKey) {
       
    	
    	
    	
    	Jws<Claims> claims = token.parseClaims(signingKey);

        List<String> scopes = claims.getBody().get("scopes", List.class);
        if (scopes == null || scopes.isEmpty() 
                || !scopes.stream().filter(scope -> Scopes.REFRESH_TOKEN.authority().equals(scope)).findFirst().isPresent()) {
            return Optional.empty();
        }

        return Optional.of(new RefreshToken(claims, token.getToken()));
    }

    @Override
    public String getToken() {
        return this.token;
    }

    public Jws<Claims> getClaims() {
        return claims;
    }
    
    public String getJti() {
        return claims.getBody().getId();
    }
    
    public String getSubject() {
        return claims.getBody().getSubject();
    }
}