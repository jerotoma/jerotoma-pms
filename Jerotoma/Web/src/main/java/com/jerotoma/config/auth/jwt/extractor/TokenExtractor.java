package com.jerotoma.config.auth.jwt.extractor;

public interface TokenExtractor {
    public String extract(String payload);
}