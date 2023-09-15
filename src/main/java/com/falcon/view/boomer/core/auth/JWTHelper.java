package com.falcon.view.boomer.core.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

@Component
public class JWTHelper {
	private final Algorithm algorithmAccessToken;
	public static final String ROLE = "role";
	public static final String PERMISSION = "permission";
	public static final String USERNAME = "username";
	public static final String USER_ID = "user_id";

	public JWTHelper(AuthProperties properties) {
		this.algorithmAccessToken = Algorithm.HMAC256(properties.getAccessToken().getSecret());
	}

	public UserJWT accessToken(String accessToken) {
		JWTVerifier verifier = JWT.require(algorithmAccessToken).build();
		DecodedJWT decodedJWT = verifier.verify(accessToken);
		return UserJWT.builder()
				.username(decodedJWT.getClaim(USERNAME).asString())
				.roles(decodedJWT.getClaim(ROLE).asList(String.class))
				.permissions(decodedJWT.getClaim(PERMISSION).asList(String.class))
				.userId(decodedJWT.getClaim(USER_ID).asString())
				.build();
	}
}
