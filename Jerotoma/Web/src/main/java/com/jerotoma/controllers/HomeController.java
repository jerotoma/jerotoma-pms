package com.jerotoma.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jerotoma.http.endpoints.EndPointConstants;

@Controller
@RequestMapping(EndPointConstants.HOME_CONTROLLER.BASE)
public class HomeController {
	
	@GetMapping(value= {""})
	public String getIndex() {
		return "/index";
	}
	
	@GetMapping(value= {"/home"})
	public String getHomeIndex() {
		return "/index";
	}
}