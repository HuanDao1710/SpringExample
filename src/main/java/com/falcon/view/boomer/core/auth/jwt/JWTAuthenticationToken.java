package com.falcon.view.boomer.core.auth.jwt;

import com.falcon.view.boomer.core.auth.UserJWT;
import lombok.EqualsAndHashCode;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
public class JWTAuthenticationToken extends AbstractAuthenticationToken {
	private final String token;
	private UserJWT userJWT;

	public JWTAuthenticationToken(String token) {
		super(null);
		this.token = token;
		setAuthenticated(false);
	}

	public JWTAuthenticationToken(String token, UserJWT userJWT, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.token = token;
		this.userJWT = userJWT;
		setAuthenticated(true);
	}

	public String getToken() {
		return token;
	}

	public UserJWT getUserJWT() {
		return userJWT;
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return null;
	}
}
