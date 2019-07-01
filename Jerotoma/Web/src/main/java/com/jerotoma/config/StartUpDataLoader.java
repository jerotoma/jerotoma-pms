package com.jerotoma.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class StartUpDataLoader implements ApplicationListener<ContextRefreshedEvent> {
	private boolean alreadySetup = false;
    private Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (alreadySetup) {
            return;
        }
		logger.debug(event.getApplicationContext().getApplicationName());
        addDefaultAccountsIfNotExists();            
        alreadySetup = true;
		if(!logger.isDebugEnabled()) {
        	
        }
	}
	private void addDefaultAccountsIfNotExists() {
		
		
	}
}
