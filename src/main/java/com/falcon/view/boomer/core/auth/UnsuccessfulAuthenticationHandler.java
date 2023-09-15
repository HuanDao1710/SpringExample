package com.falcon.view.boomer.core.auth;

import com.falcon.view.boomer.core.exception.ExceptionResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class UnsuccessfulAuthenticationHandler {
	public void handle(HttpServletRequest request, HttpServletResponse response, Exception exception) throws
			IOException {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		new ObjectMapper().writeValue(response.getOutputStream(), ExceptionResponseDTO
				.of(HttpStatus.UNAUTHORIZED, exception.getMessage(), new Date()));
		response.flushBuffer();
	}
}
