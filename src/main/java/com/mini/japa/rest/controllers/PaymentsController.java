package com.mini.japa.rest.controllers;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mini.japa.db.operators.PaymentOperator;

@RestController
public class PaymentsController {

	@RequestMapping(value = "/transfer", method = RequestMethod.POST)
	public int performTransfer(@RequestBody String requestBody) {
		
		JSONObject jsonObject = new JSONObject(requestBody);
		
		String fromPhone = jsonObject.getString("fromphone");
		String toPhone = jsonObject.getString("tophone");
		double amount = Double.parseDouble(jsonObject.getString("amount"));
		
		PaymentOperator paymentOperator = new PaymentOperator();
		
		int responseCode = paymentOperator.transfer(fromPhone, toPhone, amount);
		
		return responseCode;
	}
	
	@RequestMapping(value = "/addmoney", method = RequestMethod.POST)
	public int addMoney(@RequestBody String requestBody) {
		
		JSONObject jsonObject = new JSONObject(requestBody);
		
		String toPhone = jsonObject.getString("tophone");
		double amount = Double.parseDouble(jsonObject.getString("amount"));
		
		PaymentOperator paymentOperator = new PaymentOperator();
		
		int responseCode = paymentOperator.addMoney(toPhone, amount);
		
		return responseCode;
	}
	
}
