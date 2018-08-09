package com.mini.japa.rest.controllers;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mini.japa.db.operators.ClickStreamOperator;
import com.mini.japa.utils.JSONUtil;

@RestController
public class ClickStreamController {
	
	@RequestMapping(value = "/clickstream", method = RequestMethod.POST)
	public int performSignup(@RequestBody String requestBody) {
		
		JSONObject jsonObject = new JSONObject(requestBody);
		
		String phone = jsonObject.getString("phone");
		JSONObject streamJSONObject = jsonObject.getJSONObject("stream");
		
		Map<String, Object> streamMap = JSONUtil.jsonToMap(streamJSONObject);
		
		ClickStreamOperator clickStreamOperator = new ClickStreamOperator();
		int status = clickStreamOperator.registerClickStream(phone, streamMap);
		
		return status;
	}
	

}
