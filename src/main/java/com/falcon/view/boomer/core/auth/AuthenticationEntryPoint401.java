package com.falcon.view.boomer.core.auth;

import com.falcon.view.boomer.core.exception.ExceptionResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class AuthenticationEntryPoint401 implements AuthenticationEntryPoint {
	private final ObjectMapper objectMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws
			IOException, ServletException {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		objectMapper.writeValue(response.getOutputStream(), ExceptionResponseDTO
				.of(HttpStatus.UNAUTHORIZED, "Authentication failed.", new Date()));
		response.flushBuffer();
	}
}
