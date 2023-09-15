package com.falcon.view.boomer.core.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Collection;


@RequiredArgsConstructor
public class RequestHelper<R, B> {
	private final RestTemplate restTemplate;
	private Object body;
	private final HttpHeaders headers = new HttpHeaders();
	private Object[] variables = new Object[0];
	private UriComponentsBuilder uriComponentsBuilder = null;




	public RequestHelper<R, B> withBody(Object body) {
		this.body = body;
		return this;
	}

	public RequestHelper<R, B> withHeader(String headerName, String headerValue) {
		headers.set(headerName, headerValue);
		return this;
	}

	public RequestHelper<R, B> withVariables(Object... variables) {
		this.variables = variables;
		return this;
	}

	public RequestHelper<R, B> withUri(String uri) {
		this.uriComponentsBuilder = UriComponentsBuilder.fromUriString(uri);
		return this;
	}

	public RequestHelper<R, B> withParam(String key, Object... value) {
		if (uriComponentsBuilder == null)
			throw new IllegalArgumentException("You must setting uri before param");
		uriComponentsBuilder.queryParam(key, value);
		return this;
	}

	public RequestHelper<R, B> withParam(String key, Collection<Object> value) {
		if (uriComponentsBuilder == null)
			throw new IllegalArgumentException("You must setting uri before param");
		uriComponentsBuilder.queryParam(key, value);
		return this;
	}

	public R get(ParameterizedTypeReference<R> parameterizedTypeReference) {
		if (uriComponentsBuilder == null)
			throw new IllegalArgumentException("Uri must not null");
		ResponseEntity<R> response =
				restTemplate.exchange(uriComponentsBuilder.build(variables), HttpMethod.GET, new HttpEntity<>(body, headers), parameterizedTypeReference);
		return response.getBody();
	}

	public R post(ParameterizedTypeReference<R> parameterizedTypeReference) {
		if (uriComponentsBuilder == null)
			throw new IllegalArgumentException("Uri must not null");
		ResponseEntity<R> response =
				restTemplate.exchange(uriComponentsBuilder.encode(StandardCharsets.UTF_8).build(variables), HttpMethod.POST, new HttpEntity<>(body, headers), parameterizedTypeReference);
		return response.getBody();
	}
}
