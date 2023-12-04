package com.example.demo.src.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
public class TestController {

	/**
	 * 로그 테스트 API
	 * [GET] /test/log
	 * @return String
	 */
	@ResponseBody
	@GetMapping("/log")
	public String getAll() {
		System.out.println("테스트");
		return "Success Test";
	}
}
