package com.falcon.view.boomer.core.service.impl;

import com.falcon.view.boomer.core.service.InternalProperties;
import com.falcon.view.boomer.core.service.LoggingService;
import com.falcon.view.boomer.core.utils.RequestHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class LoggingServiceImpl implements LoggingService {
	private final RestTemplate restTemplate;
	private final InternalProperties internalProperties;

	@Override
	public long countView(String campaignId, Date fromDate, String accessToken) {
		var request = new RequestHelper<Long, Object>(restTemplate)
				.withUri(internalProperties.getCountViewUri())
				.withParam("campaignId", campaignId)
				.withHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
		if (Objects.nonNull(fromDate)) {
			request.withParam("fromDate", fromDate);
		}
		return request.get(new ParameterizedTypeReference<Long>() {
		});
	}
}
