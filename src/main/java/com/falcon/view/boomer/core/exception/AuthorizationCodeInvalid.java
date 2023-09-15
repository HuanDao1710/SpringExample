package com.falcon.view.boomer.core.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthorizationCodeInvalid extends AuthenticationException {
	public AuthorizationCodeInvalid(String msg) {
		super(msg);
	}
}
