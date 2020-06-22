package com.jerotoma.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jerotoma.common.constants.EndPointConstants;

@Controller
@RequestMapping(EndPointConstants.DASHBOARD_CONTROLLER.BASE)
public class DashboardController {
	
	@GetMapping(value = {"",
			"/{path:[^\\.]*}",
			"/{path:[^\\.]*}/{path:[^\\.]*}",
			"/{path:[^\\.]*}/{path:[^\\.]*}/{path:[^\\.]*}",
			"/{path:[^\\.]*}/{path:[^\\.]*}/{path:[^\\.]*}/{path:[^\\.]*}",
			"/{path:[^\\.]*}/{path:[^\\.]*}/{path:[^\\.]*}/{path:[^\\.]*}/{path:[^\\.]*}",
			"/{path:[^\\.]*}/{path:[^\\.]*}/{path:[^\\.]*}/{path:[^\\.]*}/{path:[^\\.]*}/{path:[^\\.]*}",
			"/{path:[^\\.]*}/{path:[^\\.]*}/{path:[^\\.]*}/{path:[^\\.]*}/{path:[^\\.]*}/{path:[^\\.]*}/{path:[^\\.]*}"
			})
	public String redirect(Authentication auth) {	
	   return "forward:/";
	}
}