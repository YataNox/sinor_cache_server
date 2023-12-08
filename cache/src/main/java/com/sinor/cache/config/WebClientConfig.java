package com.sinor.cache.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import io.netty.channel.ChannelOption;
import lombok.extern.slf4j.Slf4j;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

@Configuration
@Slf4j
/*

 * WebClient 빈 등록
 * WebClient 빈을 등록하면, WebClient.Builder 를 주입받아 사용할 수 있다.
 * 2023.12.7 ddongbu
 */
public class WebClientConfig {

	DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();

	HttpClient httpClient = HttpClient.create()
		.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000); // 10초

	@Bean
	public WebClient webClient() {
		return WebClient.builder()
			.uriBuilderFactory(factory)
			.baseUrl("http://mainHost:80")
			.codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(2 * 1024 * 1024))
			.clientConnector(new ReactorClientHttpConnector(httpClient))
			.build();
	}

	@Bean
	public ConnectionProvider connectionProvider() {
		return ConnectionProvider.builder("http-pool")
			.maxConnections(100)
			.pendingAcquireTimeout(Duration.ofMillis(0))
			.pendingAcquireMaxCount(-1)
			.maxIdleTime(Duration.ofMillis(1000L))
			.build();
	}
}
