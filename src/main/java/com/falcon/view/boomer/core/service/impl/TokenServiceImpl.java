package com.falcon.view.boomer.core.service.impl;

import com.falcon.view.boomer.core.auth.AuthenticationSuccessResponse;
import com.falcon.view.boomer.core.service.InternalProperties;
import com.falcon.view.boomer.core.service.TokenService;
import com.falcon.view.boomer.core.utils.RequestHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
	private final RestTemplate restTemplate;
	private final InternalProperties internalProperties;

	@Override
	public AuthenticationSuccessResponse login() {
		return new RequestHelper<AuthenticationSuccessResponse, Object>(restTemplate)
				.withUri(internalProperties.getLoginUri())
				.withParam("username", internalProperties.getUsername())
				.withParam("password", internalProperties.getPassword())
				.post(new ParameterizedTypeReference<AuthenticationSuccessResponse>() {
				});
	}
}
