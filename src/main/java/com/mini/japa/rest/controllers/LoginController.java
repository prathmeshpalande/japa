package com.mini.japa.rest.controllers;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.mini.japa.db.operators.UserOperator;

@RestController
public class LoginController {
	
	@RequestMapping(value = "/performsignup", method = RequestMethod.POST)
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
	
	@RequestMapping(value = "/validatesignup", method = RequestMethod.POST)
	public Object validateSignup(@RequestBody String requestBody) {
		
		JSONObject jsonObject = new JSONObject(requestBody);
		
		String phone = jsonObject.getString("phone");
		
		UserOperator userOperator = new UserOperator();
		
		Object status = userOperator.validateSignup(phone);
		
		return status;
	}
	
	@RequestMapping(value = "/performlogin", method = RequestMethod.POST)
	public int performLogin(@RequestBody String requestBody) {
		
		JSONObject jsonObject = new JSONObject(requestBody);
		
		String phone = jsonObject.getString("phone");
		String password = jsonObject.getString("password");
		
		UserOperator userOperator = new UserOperator();
		
		int status = userOperator.authUser(phone, password);
		
		return status;
	}
	
	@RequestMapping(value = "/getprofile", method = RequestMethod.POST)
	public Object getProfile(@RequestBody String requestBody) {
		
		JSONObject jsonObject = new JSONObject(requestBody);
		
		String phone = jsonObject.getString("phone");
		
		UserOperator userOperator = new UserOperator();
		
		Object response = userOperator.getProfile(phone);
		
		return response;
	}
}
