package com.jerotoma.config.auth.jwt.extractor;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

import com.jerotoma.common.constants.SecurityConstant;
import com.jerotoma.common.utils.StringUtility;

@Component
public class JwtHeaderTokenExtractor implements TokenExtractor {
    @Override
    public String extract(String header) {
        if (StringUtility.isEmpty(header)) {
            throw new AuthenticationServiceException("Authorization header cannot be blank!");
        }
        
        if (!header.contains(SecurityConstant.HEADER_PREFIX)) {
            throw new AuthenticationServiceException("Invalid authorization header size.");
        }

        if (header.length() < SecurityConstant.HEADER_PREFIX.length()) {
            throw new AuthenticationServiceException("Invalid authorization header size.");
        }

        return header.substring(SecurityConstant.HEADER_PREFIX.length(), header.length());
    }

	
}