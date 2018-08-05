package com.mini.paytm.rest.controllers;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mini.paytm.db.operators.PassbookOperator;

@RestController
public class PassbookController {

	@RequestMapping(value = "/getpassbook", method = RequestMethod.POST)
	public Map<Integer, List<String>> getPassbook(@RequestBody String requestBody) {
				
		JSONObject jsonObject = new JSONObject(requestBody);
		
		String phone = jsonObject.getString("phone");
		
		PassbookOperator passbookOperator = new PassbookOperator();
		
		Map<Integer, List<String>> passbook = passbookOperator.getPassbook(phone);
		
		return passbook;
		
	}
}
