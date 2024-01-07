package com.sinor.cache.authentication.config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class authController {
	@GetMapping("/admin/test")
	public String test() {
		return "admin test";
	}
}
