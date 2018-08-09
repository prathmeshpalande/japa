package com.mini.japa.rest.controllers;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mini.japa.db.operators.UserOperator;

@RestController
public class ClickStreamController {
	
	@RequestMapping(value = "/clickstream", method = RequestMethod.POST)
	public int performSignup(@RequestBody String requestBody) {
		
		JSONObject jsonObject = new JSONObject(requestBody);
		
		String name = jsonObject.getString("name");
		String aadhaar = jsonObject.getString("aadhaar");
		String email = jsonObject.getString("email");
		String password = jsonObject.getString("password");
		String phone = jsonObject.getString("phone");
		String dob = jsonObject.getString("dob");
		
		UserOperator userOperator = new UserOperator();
		
		int status = userOperator.registerUser(name, aadhaar, email, password, phone, dob);
		
		return status;
	}
	

}
