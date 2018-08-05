package com.mini.paytm.rest.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeartbeatController {

	@RequestMapping(value = "/heartbeat", method = RequestMethod.GET)
	public boolean heartbeat() {
		return true;
	}
	
}
