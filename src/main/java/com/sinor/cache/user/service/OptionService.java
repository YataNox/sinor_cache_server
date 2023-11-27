package com.example.demo.src.user.service;

import com.example.demo.src.user.entity.Option;
import com.example.demo.src.user.repository.OptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OptionService {
    private final OptionRepository optionRepository;

    @Autowired
    public OptionService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    public Option getOption(String key){
        return optionRepository.findById(key).orElseThrow(() -> new RuntimeException("옵션 미발견"));
    }

    public Option updateExpiredTime(String key, Long expiredTime){
        Option option = getOption(key);
        option.setExpiredTime(expiredTime);
        return optionRepository.save(option);
    }
}
