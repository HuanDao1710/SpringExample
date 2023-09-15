package com.falcon.view.boomer.core.service;

import org.springframework.lang.Nullable;

import java.util.Date;

public interface LoggingService {
	long countView(String campaignId, @Nullable Date fromDate, String accessToken);
}
