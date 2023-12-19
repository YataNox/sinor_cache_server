package com.sinor.cache.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class ApiKeyRotation {

	// 현재 사용 중인 API 키
	private String currentApiKey;

	public ApiKeyRotation(){
		currentApiKey = generateApiKey();
	}

	// API 키 생성 메서드
	private String generateApiKey() {
		byte[] randomBytes = new byte[32];
		new SecureRandom().nextBytes(randomBytes);
		return Base64.getEncoder().encodeToString(randomBytes);
	}

	// API 키 회전 메서드
	public String rotateApiKey() {
		// 새로운 API 키 생성
		String newApiKey = generateApiKey();

		// 현재 사용 중인 API 키를 새로운 키로 교체
		currentApiKey = newApiKey;

		// 새로 생성된 API 키 반환
		return newApiKey;
	}

	// 현재 사용 중인 API 키 조회 메서드
	public String getCurrentApiKey() {
		return currentApiKey;
	}
}
/*
*
OAuth 2.0은 소셜 로그인에 사용되지만, 오로지 소셜 로그인에만 국한된 것은 아닙니다. OAuth 2.0은 다양한 사용 사례에 적용할 수 있는 개방형 표준 인증 프로토콜입니다. 주요 목적은 안전하게 리소스에 접근하기 위한 표준을 제공하는 것입니다.

OAuth 2.0은 다음과 같은 주요 사용 사례를 지원합니다:

소셜 로그인:

사용자는 자체 자격 증명을 제공하는 대신, 소셜 프로바이더(예: Google, Facebook, Twitter 등)를 통해 로그인할 수 있습니다. 이를 통해 사용자는 다양한 서비스에서 동일한 로그인 정보를 사용할 수 있습니다.
서비스 간 인증 (API 인증):

클라이언트 애플리케이션은 OAuth 2.0을 사용하여 사용자의 리소스에 접근하기 위한 권한을 얻습니다. 예를 들어, 클라이언트 애플리케이션이 사용자의 Google Drive에 접근하려면 OAuth 2.0을 사용하여 사용자로부터 권한을 얻게 됩니다.
클라이언트 자격 증명:

OAuth 2.0을 사용하여 클라이언트 애플리케이션이 서비스에 인증하고 권한을 얻을 수 있습니다. 이는 주로 서비스 간 통신 및 API 호출에서 사용됩니다.
리소스 소유자 비밀번호 자격 증명:

리소스 소유자 비밀번호 자격 증명 그랜트 유형을 사용하여 사용자의 아이디와 비밀번호를 사용하여 직접 액세스 토큰을 얻는 방식입니다. 이 방식은 일반적으로 안전하지 않다고 간주되며, 일반적으로 권장되지 않습니다.
암시적 그랜트 유형:

암시적 그랜트 유형은 클라이언트 애플리케이션이 사용자의 브라우저를 통해 직접 토큰을 수신하는 방식입니다. 주로 웹 기반 애플리케이션에서 사용됩니다.
클라이언트 자격 증명 그랜트 유형:

클라이언트 자격 증명 그랜트 유형은 클라이언트가 자격 증명으로 토큰을 직접 교환하는 방식입니다.
* */