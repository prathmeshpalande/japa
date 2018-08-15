package com.mini.japa.rest.controllers;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mini.japa.db.operators.SessionOperator;

@RestController
public class SessionController {

	@RequestMapping(value = "/createsession", method = RequestMethod.POST)
	public boolean createSession(@RequestBody String requestBody) {
		
		boolean response = false;
		
		JSONObject jsonObject = new JSONObject(requestBody);
		String phone = jsonObject.getString("phone");
		
		SessionOperator sessionOperator = new SessionOperator();
		response = sessionOperator.createSession(phone);
		
		return response;
		
	}
	
	@RequestMapping(value = "/validatesession", method = RequestMethod.POST)
	public boolean validateSession(@RequestBody String requestBody) {
		
		boolean response = false;
		
		JSONObject jsonObject = new JSONObject(requestBody);
		String sessionKey = jsonObject.getString("sessionkey");
		
		SessionOperator sessionOperator = new SessionOperator();
		response = sessionOperator.validateSession(sessionKey);
		
		return response;
		
	}
	
}
