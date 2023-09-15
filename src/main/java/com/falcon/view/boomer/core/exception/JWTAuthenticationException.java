package com.falcon.view.boomer.core.exception;

import org.springframework.security.core.AuthenticationException;

public class JWTAuthenticationException extends AuthenticationException {
	public JWTAuthenticationException(String explanation) {
		super(explanation);
	}
}
