package com.sinor.auth.OAuth2.authorizationCode;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Date;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtTokenService {
	@Value("${jwt.private-key}")
	private String jwtPrivateKey;
	@Value("${jwt.public-key}")
	private String jwtPublicKey;
	int accessExpirationMs = 9600000;
	int refreshExpirationMs = 86400000;

	public String createAccessToken(UserToken userToken) throws NoSuchAlgorithmException, InvalidKeySpecException {
		String[] authorities = Arrays.stream(userToken.getRoles()).distinct()
			.map(role -> role.replaceFirst("^ROLE_", ""))
			.toArray(String[]::new);
		return Jwts.builder()
			.setSubject("AccessToken")
			.claim("username", userToken.getUsername())
			.claim("exp", userToken.getExpiration())
			.claim("roles", authorities)
			.setIssuedAt(new Date())
			.setExpiration(new Date((new Date()).getTime() + accessExpirationMs))
			.signWith(SignatureAlgorithm.RS256, generateJwtKeyEncryption(jwtPrivateKey))
			.compact();
	}

	public String createRefreshToken(RefreshTokenDto.Req build) throws
		NoSuchAlgorithmException,
		InvalidKeySpecException {
		return Jwts.builder()
			.setSubject("RefreshToken")
			.setIssuedAt(new Date())
			.setExpiration(new Date((new Date()).getTime() + refreshExpirationMs)) // Refresh token expiration time
			.signWith(SignatureAlgorithm.RS256, generateJwtKeyEncryption(jwtPrivateKey))
			.compact();
	}

	public UserToken parse(String token, String jwtPublicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
		Claims claims = Jwts.parser()
			.setSigningKey(generateJwtKeyDecryption(jwtPublicKey))
			.parseClaimsJws(token)
			.getBody();

		return UserToken.builder()
			.subject(claims.getSubject())
			.username(claims.get("username", String.class))
			.roles(claims.get("roles", String[].class))
			.refreshToken(claims.get("refreshToken", String.class))
			.expiration(claims.getExpiration())
			.build();
	}

	public UserToken parseRefreshToken(String refreshToken) throws NoSuchAlgorithmException, InvalidKeySpecException {
		UserToken userToken = parse(refreshToken, jwtPublicKey);

		// 토큰의 subject를 확인하여 리프레시 토큰인지 확인
		if (!userToken.getSubject().equals("refresh")) {
			throw new InvalidTokenException("Invalid refresh token.");
		}

		// 토큰의 만료 시간을 확인하여 아직 유효한 토큰인지 확인
		if (userToken.getExpiration().before(new Date())) {
			throw new InvalidTokenException("Expired refresh token.");
		}

		return userToken;
	}

	public PrivateKey generateJwtKeyEncryption(String jwtPrivateKey) throws
		NoSuchAlgorithmException, InvalidKeySpecException {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		byte[] keyBytes = Base64.decodeBase64(jwtPrivateKey);
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
		return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
	}

	public PublicKey generateJwtKeyDecryption(String jwtPublicKey) throws
		NoSuchAlgorithmException,
		InvalidKeySpecException {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		byte[] keyBytes = Base64.decodeBase64(jwtPublicKey);
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
		return keyFactory.generatePublic(x509EncodedKeySpec);
	}

	/**
	 * 토큰이 유효한지 확인
	 * 따로 exception 하지 못했습니다.
	 * 참고 부탁드립니다.
	 */
	public class InvalidTokenException extends RuntimeException {
		public InvalidTokenException(String message) {
			super(message);
		}
	}
}
