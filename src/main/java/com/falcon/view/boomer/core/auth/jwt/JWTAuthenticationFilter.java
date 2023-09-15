package com.falcon.view.boomer.core.auth.jwt;

import com.falcon.view.boomer.core.auth.UnsuccessfulAuthenticationHandler;
import com.falcon.view.boomer.core.auth.UserContext;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
	public static final String BEARER = "Bearer ";
	private final AuthenticationManager authenticationManager;
	private final UserContext userContext;
	private final UnsuccessfulAuthenticationHandler unsuccessfulAuthenticationHandler;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws
			ServletException, IOException {
		if (!requireCheckAuthentication(request)) {
			filterChain.doFilter(request, response);
			return;
		}

		try {
			Authentication authentication = authenticationManager.authenticate(new JWTAuthenticationToken(getToken(request)));
			if (authentication == null) {
				throw new BadCredentialsException("Authentication is failed");
			}
			JWTAuthenticationToken jwtAuthenticationToken = (JWTAuthenticationToken) authentication;
			successfulAuthentication(request, response, jwtAuthenticationToken);
		} catch (AuthenticationException exception) {
			unSuccessfulAuthentication(request, response, exception);
		} finally {
			filterChain.doFilter(request, response);
		}
	}

	private String getToken(HttpServletRequest request) {
		return request.getHeader(HttpHeaders.AUTHORIZATION).substring(BEARER.length());
	}

	private boolean requireCheckAuthentication(HttpServletRequest request) {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		return StringUtils.isNotBlank(authorizationHeader) && authorizationHeader.startsWith(BEARER);
	}


	private void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, JWTAuthenticationToken authenticationToken) {
		var context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authenticationToken);
		SecurityContextHolder.setContext(context);

		userContext.setUser(authenticationToken.getUserJWT());
		userContext.setAccessToken(authenticationToken.getToken());
	}

	private void unSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Exception e) throws
			IOException {
		unsuccessfulAuthenticationHandler.handle(request, response, e);
	}
}
