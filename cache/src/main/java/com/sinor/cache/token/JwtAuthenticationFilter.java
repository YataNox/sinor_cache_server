// package com.sinor.cache.token;
//
// import java.io.IOException;
//
// import org.springframework.web.filter.OncePerRequestFilter;
//
// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
//
// public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
// 	private final com.example.AutomateX.config.auth.JwtTokenProvider jwtAuthenticationProvider;
//
// 	public JwtAuthenticationFilter(com.example.AutomateX.config.auth.JwtTokenProvider provider) {
// 		jwtAuthenticationProvider = provider;
// 	}
//
// 	@Override
// 	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
// 		FilterChain filterChain) throws ServletException, IOException {
// 		String token = jwtAuthenticationProvider.resolveToken(request);
//
// 		if (token != null && jwtAuthenticationProvider.validateToken(token)) {
// 			Authentication authentication = jwtAuthenticationProvider.getAuthentication(token);
//
// 			SecurityContextHolder.getContext().setAuthentication(authentication);
// 		}
//
// 		filterChain.doFilter(request, response);
//
// 	}
// }