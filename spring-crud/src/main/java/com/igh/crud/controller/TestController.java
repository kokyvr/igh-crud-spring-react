package com.igh.crud.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class TestController {

	
	public String TestingController() {
		return "Testing Controller";
	}
}
