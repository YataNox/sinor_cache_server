package com.sinor.cache.admin.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	@GetMapping("/admin/test")
	public String test() {
		return "admin hi";
	}

	@GetMapping("/user/test")
	public String test2() {
		return "user hi";
	}
}
