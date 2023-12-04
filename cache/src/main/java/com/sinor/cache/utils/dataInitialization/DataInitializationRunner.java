package com.sinor.cache.utils.dataInitialization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializationRunner implements ApplicationRunner {

	private final DataInitializationService dataInitializationService;

	@Autowired
	public DataInitializationRunner(DataInitializationService dataInitializationService) {
		this.dataInitializationService = dataInitializationService;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// 애플리케이션이 시작될 때 데이터 초기화
		dataInitializationService.initializeData();
	}
}
