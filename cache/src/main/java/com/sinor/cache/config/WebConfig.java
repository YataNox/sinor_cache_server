package com.sinor.cache.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.servlet.Filter;

@Configuration
public class WebConfig {

	@Value("${filter.key}")
	private String key;

	@Bean
	public FilterRegistrationBean UrlFilter() {
		FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new UrlFilter(key));
		filterRegistrationBean.setOrder(1);
		filterRegistrationBean.addUrlPatterns("/admin/*");
		return filterRegistrationBean;
	}
}