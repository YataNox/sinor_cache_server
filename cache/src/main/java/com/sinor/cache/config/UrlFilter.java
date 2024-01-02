package com.sinor.cache.config;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UrlFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;

		String key = httpRequest.getHeader("Key"); // header에서 키 추출
		String keyFromDB = "1234"; // DB에서 꺼내기

		if (key == null || !key.equals(keyFromDB)) {
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "인증 실패");
			return;
		}

		chain.doFilter(request, response);

		System.out.println("doFilter 종료");
	}
}