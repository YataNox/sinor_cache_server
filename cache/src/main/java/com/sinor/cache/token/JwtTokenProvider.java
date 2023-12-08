/*
package com.sinor.cache.token;

import com.example.AutomateX.domain.user.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
  private String secretKey =
      "c2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQtc2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQK";

  private long accessTokenValidTime = 1440 * 60 * 7 * 1000L; // 토큰 유효시간 168 시간(7일)
  private final long refreshTokenValidTime = 60 * 60 * 24 * 12 * 1000L; // refresh 토큰 유효시간 한 달
  private final UserDetailsService userDetailsService;

  // 객체 초기화, secretKey 를 Base64로 인코딩합니다.
  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  // 회원가입 요청 시, accessToken 생성
  public String createAccessToken(UUID userPk, Role roles) {
    Claims claims = Jwts.claims().setSubject(String.valueOf(userPk)); // JWT payload 에 저장되는 정보단위
    claims.put("roles", roles); // 정보는 key/value 쌍으로 저장됩니다.
    Date now = new Date();
    return Jwts.builder()
        .setClaims(claims) // 정보 저장
        .setIssuedAt(now) // 토큰 발행 시간 정보
        .setExpiration(new Date(now.getTime() + accessTokenValidTime)) // set Expire Time
        .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘
        // signature 에 들어갈 secret 값 세팅
        .compact();
  }

  // 로그인 요청 시, refreshToken 생성
  public String createRefreshToken(String value) {
    Claims claims = Jwts.claims(); // Jwt payload 정보단위, user 식별값
    claims.put("value", value); // key : value
    Date now = new Date();
    // Jwt header, payload, signature
    return Jwts.builder().setClaims(claims) // 정보 저장
        .setIssuedAt(now) // 토큰 발행 시간 저장
        .setExpiration(new Date(now.getTime() + refreshTokenValidTime)) // 만료 시간 저장
        .signWith(SignatureAlgorithm.HS256, secretKey) // signature에 key 저장
        .compact();
  }

  // JWT 토큰에서 인증 정보 조회
  public Authentication getAuthentication(String token) {
    UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  // 토큰에서 회원 정보 추출
  public String getUserPk(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
  }

  // Request의 Header에서 token 값을 가져옵니다. "X-AUTH-TOKEN" : "TOKEN값'
  public String resolveToken(HttpServletRequest request) {
    return request.getHeader("X-AUTH-TOKEN");
  }

  // 토큰의 유효성 + 만료일자 확인
  public boolean validateToken(String jwtToken) {
    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
      return !claims.getBody().getExpiration().before(new Date());
    } catch (Exception e) {
      return false;
    }
  }
}*/
