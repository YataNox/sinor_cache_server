package com.sinor.auth.security;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sinor.auth.dto.AuthDto;
import com.sinor.auth.service.RedisService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider implements InitializingBean {

	private final UserDetailsServiceImpl userDetailsService;
	private final RedisService redisService;

	private static final String AUTHORITIES_KEY = "roles";
	private static final String EMAIL_KEY = "email";
	private static final String url = "https://authHost:9000";

	private final String secretKey;
	private static KeyPair signingKeyPair;

	private final Long accessTokenValidityInMilliseconds;
	private final Long refreshTokenValidityInMilliseconds;

	public JwtTokenProvider(
		UserDetailsServiceImpl userDetailsService,
		RedisService redisService,
		@Value("${jwt.private-key}") String secretKey,
		@Value("${jwt.access-token-validity-in-seconds}") Long accessTokenValidityInMilliseconds,
		@Value("${jwt.refresh-token-validity-in-seconds}") Long refreshTokenValidityInMilliseconds) {
		this.userDetailsService = userDetailsService;
		this.redisService = redisService;
		this.secretKey = secretKey;
		this.accessTokenValidityInMilliseconds = accessTokenValidityInMilliseconds * 1000;
		this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds * 1000;
	}

	// 시크릿 키 설정
	@Override
	public void afterPropertiesSet() throws Exception {
		signingKeyPair = generateKeyPair(secretKey);
		String publicKey = Base64.getEncoder().encodeToString(signingKeyPair.getPublic().getEncoded());
		System.out.println("publickey : " + publicKey);
	}

	private KeyPair generateKeyPair(String secretKey) throws NoSuchAlgorithmException {
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(2048, new SecureRandom(secretKey.getBytes()));
		return generator.generateKeyPair();
	}

	@Transactional
	public AuthDto.TokenDto createToken(String email, String authorities) {
		Long now = System.currentTimeMillis();

		String accessToken = Jwts.builder()
			.setHeaderParam("typ", "JWT")
			.setHeaderParam("alg", "RS256")
			.setExpiration(new Date(now + accessTokenValidityInMilliseconds))
			.setSubject("access-token")
			.claim(url, true)
			.claim(EMAIL_KEY, email)
			.claim(AUTHORITIES_KEY, authorities)
			.signWith(signingKeyPair.getPrivate(), SignatureAlgorithm.RS256)
			.compact();

		String refreshToken = Jwts.builder()
			.setHeaderParam("typ", "JWT")
			.setHeaderParam("alg", "RS256")
			.setExpiration(new Date(now + refreshTokenValidityInMilliseconds))
			.setSubject("refresh-token")
			.signWith(signingKeyPair.getPrivate(), SignatureAlgorithm.RS256)
			.compact();

		return new AuthDto.TokenDto(accessToken, refreshToken);
	}

	// == 토큰으로부터 정보 추출 == //

	public Claims getClaims(String token) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(signingKeyPair.getPublic())
				.build()
				.parseClaimsJws(token)
				.getBody();
		} catch (ExpiredJwtException e) { // Access Token
			return e.getClaims();
		}
	}

	public Authentication getAuthentication(String token) {
		String email = getClaims(token).get(EMAIL_KEY).toString();
		UserDetailsImpl userDetailsImpl = userDetailsService.loadUserByUsername(email);
		return new UsernamePasswordAuthenticationToken(userDetailsImpl, "", userDetailsImpl.getAuthorities());
	}

	public long getTokenExpirationTime(String token) {
		return getClaims(token).getExpiration().getTime();
	}

	// == 토큰 검증 == //
	public boolean validateRefreshToken(String refreshToken) {
		try {
			if (redisService.getValues(refreshToken).equals("delete")) { // 회원 탈퇴했을 경우
				return false;
			}
			Jwts.parserBuilder()
				.setSigningKey(signingKeyPair.getPublic())
				.build()
				.parseClaimsJws(refreshToken);
			System.out.println("refreshToken: " + refreshToken);
			return true;
		} catch (SignatureException e) {
			log.error("Invalid JWT signature.");
		} catch (MalformedJwtException e) {
			log.error("Invalid JWT token.");
		} catch (ExpiredJwtException e) {
			log.error("Expired JWT token.");
		} catch (UnsupportedJwtException e) {
			log.error("Unsupported JWT token.");
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty.");
		} catch (NullPointerException e) {
			log.error("JWT Token is empty.");
		}
		return false;
	}

	// Filter에서 사용
	public boolean validateAccessToken(String accessToken) {
		try {
			if (redisService.getValues(accessToken) != null // NPE 방지
				&& redisService.getValues(accessToken).equals("logout")) { // 로그아웃 했을 경우
				return false;
			}
			Jwts.parserBuilder()
				.setSigningKey(signingKeyPair.getPublic())
				.build()
				.parseClaimsJws(accessToken);
			return true;
		} catch (ExpiredJwtException e) {
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// 재발급 검증 API에서 사용
	public boolean validateAccessTokenOnlyExpired(String accessToken) {
		try {
			return getClaims(accessToken)
				.getExpiration()
				.before(new Date());
		} catch (ExpiredJwtException e) {
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
