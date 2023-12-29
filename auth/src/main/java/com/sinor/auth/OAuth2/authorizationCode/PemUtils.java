package com.sinor.auth.OAuth2.authorizationCode;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class PemUtils {
	public static PrivateKey readPrivateKeyFromFile(String filePath) throws Exception {
		byte[] keyBytes = Files.readAllBytes(Paths.get(filePath));
		String privateKeyContent = new String(keyBytes);
		privateKeyContent = privateKeyContent.replaceAll("\\n", "")
			.replace("-----BEGIN PRIVATE KEY-----", "")
			.replace("-----END PRIVATE KEY-----", "");
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent));
		return kf.generatePrivate(keySpecPKCS8);
	}
}
