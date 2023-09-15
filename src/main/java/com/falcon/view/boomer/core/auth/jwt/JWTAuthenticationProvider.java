package com.falcon.view.boomer.core.auth.jwt;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.falcon.view.boomer.core.auth.JWTHelper;
import com.falcon.view.boomer.core.auth.UserJWT;
import com.falcon.view.boomer.core.exception.JWTAuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JWTAuthenticationProvider implements AuthenticationProvider {
	private final JWTHelper jwtHelper;

	@Override
	public Authentication authenticate(Authentication authentication) throws
			AuthenticationException {
		try {
			JWTAuthenticationToken jwtAuthenticationToken = (JWTAuthenticationToken) authentication;
			UserJWT user = jwtHelper.accessToken(jwtAuthenticationToken.getToken());

			return new JWTAuthenticationToken(
					jwtAuthenticationToken.getToken(),
					user,
					user.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
		} catch (SignatureVerificationException exception) {
			throw new JWTAuthenticationException("AccessToken invalid");
		} catch (Exception ex) {
			throw new JWTAuthenticationException(String.format("Authentication accessToken with error: %s", ex.getMessage()));
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return JWTAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
