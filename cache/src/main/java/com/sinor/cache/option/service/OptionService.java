package com.sinor.cache.option.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinor.cache.option.Option;
import com.sinor.cache.option.repository.OptionRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OptionService {
	private final OptionRepository optionRepository;

	@Autowired
	public OptionService(OptionRepository optionRepository) {
		this.optionRepository = optionRepository;
	}

	public Option getOption(String key) {
		return optionRepository.findByUrl(key).orElseThrow(() -> new RuntimeException("옵션 미발견"));
	}

	public Option updateExpiredTime(String key, Long expiredTime) {
		Option option = getOption(key);
		option.setExpiredTime(expiredTime);
		return optionRepository.save(option);
	}
}
