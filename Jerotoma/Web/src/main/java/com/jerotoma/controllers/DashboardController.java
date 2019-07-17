package com.jerotoma.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jerotoma.common.constants.EndPointConstants;

@Controller
@RequestMapping(EndPointConstants.DASHBOARD_CONTROLLER.BASE)
public class DashboardController {
	
	@GetMapping(value = {"","/{path:[^\\.]*}"})
	public String redirect() {
	   return "forward:/index.html";
	}
}