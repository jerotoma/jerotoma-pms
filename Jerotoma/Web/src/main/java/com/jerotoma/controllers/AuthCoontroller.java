package com.jerotoma.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jerotoma.http.endpoints.EndPointConstants;

@Controller
@RequestMapping(EndPointConstants.AUTH_CONTROLLER.BASE)
public class AuthCoontroller {
	
	@GetMapping(value = {"","/{path:[^\\.]*}"})
	public String redirect() {
	   return "forward:/";
	}
	

}
