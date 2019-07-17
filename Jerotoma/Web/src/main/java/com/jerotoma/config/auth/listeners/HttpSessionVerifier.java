package com.jerotoma.config.auth.listeners;

import java.util.Date;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

public class HttpSessionVerifier implements HttpSessionListener {
	
	private final static Logger LOGGER = Logger.getLogger(HttpSessionVerifier.class.getName());

	public void sessionCreated(HttpSessionEvent event) {
      Date sessionCreationTime = new Date(event.getSession().getCreationTime());
      Date sessionLastAccessedTime = new Date(event.getSession().getLastAccessedTime());
      int sessionMaxInactiveInterval = event.getSession().getMaxInactiveInterval();
      LOGGER.warn("Session: " + event.getSession().getId()
      + " createTime: " + sessionCreationTime
      + " lastAccess: " + sessionLastAccessedTime
      + " with maxInactiveInterval: " + sessionMaxInactiveInterval
      + " created.");
      
      System.out.println("Session: " + event.getSession().getId()
    	      + " createTime: " + sessionCreationTime
    	      + " lastAccess: " + sessionLastAccessedTime
    	      + " with maxInactiveInterval: " + sessionMaxInactiveInterval
    	      + " created.");
	}
	
	public void sessionDestroyed(HttpSessionEvent event) {
	      Date sessionCreationTime = new Date(event.getSession().getCreationTime());
	      Date sessionLastAccessedTime = new Date(event.getSession().getLastAccessedTime());
	      int sessionMaxInactiveInterval = event.getSession().getMaxInactiveInterval();
	      LOGGER.warn("Session: " + event.getSession().getId()
	      + " createTime: " + sessionCreationTime
	      + " lastAccess: " + sessionLastAccessedTime
	      + " with maxInactiveInterval: " + sessionMaxInactiveInterval
	      + " destroyed.");
	      
	      System.out.println("Session: " + event.getSession().getId()
	    	      + " createTime: " + sessionCreationTime
	    	      + " lastAccess: " + sessionLastAccessedTime
	    	      + " with maxInactiveInterval: " + sessionMaxInactiveInterval
	    	      + " destroyed.");
	}

}
